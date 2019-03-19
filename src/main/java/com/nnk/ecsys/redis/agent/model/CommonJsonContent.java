package com.nnk.ecsys.redis.agent.model;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class CommonJsonContent extends CommonJsonBase {

    public final static String KEY_FOR_dataContentType = "_contentType";

    public Integer getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(Integer dataContentType) {
        this.dataContentType = dataContentType;
    }

    @JSONField(name = "_contentType")
    protected Integer dataContentType;
}
