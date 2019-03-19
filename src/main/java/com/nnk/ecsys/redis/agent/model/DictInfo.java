package com.nnk.ecsys.redis.agent.model;

/**
 * Created by YHT on 2017/11/2.
 */
public class DictInfo {
    private String dictKey;
    private String dictValue;
    private int sortNo;

    public DictInfo(String dictKey, String dictValue, int sortNo) {
        this.dictKey = dictKey;
        this.dictValue = dictValue;
        this.sortNo = sortNo;
    }

    public DictInfo(String dictKey, String dictValue) {
        this.dictKey = dictKey;
        this.dictValue = dictValue;
    }

    public DictInfo() {
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }
}
