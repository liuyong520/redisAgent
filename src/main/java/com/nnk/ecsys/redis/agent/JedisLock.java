package com.nnk.ecsys.redis.agent;

/**
 * Redis distributed lock implementation.
 * 
 * @author Alois Belaska <alois.belaska@gmail.com>
 */
public class JedisLock {

	/**
	 * Lock key path.
	 */
	String lockKey;

	/**
	 * Lock expiration in miliseconds.
	 */
	int expireMsecs = 60 * 1000;

	/**
	 * Acquire timeout in miliseconds.
	 */
    int timeoutMsecs = 10 * 1000;

	boolean locked = false;

	/**
	 * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 */
	public JedisLock(String lockKey) {
		this.lockKey = lockKey;
	}

	/**
	 * Detailed constructor with default lock expiration of 60000 msecs.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutMsecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 */
	public JedisLock(String lockKey, int timeoutMsecs) {
		this(lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * Detailed constructor.
	 * 
	 * @param lockKey
	 *            lock key (ex. account:1, ...)
	 * @param timeoutMsecs
	 *            acquire timeout in miliseconds (default: 10000 msecs)
	 * @param expireMsecs
	 *            lock expiration in miliseconds (default: 60000 msecs)
	 */
	public JedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
		this(lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	/**
	 * @return lock key
	 */
	public String getLockKey() {
		return lockKey;
	}

	/**
	 * Acquire lock.
	 * 
	 * @return true if lock is acquired, false acquire timeouted
	 * @throws InterruptedException
	 *             in case of thread interruption
	 */
	public synchronized boolean acquire() {
		int timeout = timeoutMsecs;
		while (true) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires);

			if (RedisUtil.setnx(lockKey, expiresStr) == 1) {
				// lock acquired
				locked = true;
				return true;
			}

			String currentValueStr = RedisUtil.getString(lockKey);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				// lock is expired

				String oldValueStr = RedisUtil.getSet(lockKey, expiresStr);
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					// lock acquired
					locked = true;
					return true;
				}
			}

			if (timeout <= 0) {
				break;
			}

			timeout -= 100;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}

		return false;
	}

	/**
	 * Acqurired lock release.
	 */
	public synchronized void release() {
		if (locked) {
			RedisUtil.del(lockKey);
			locked = false;
		}
	}

}
