package com.xlfd.commonutilsx;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    private Map<String, Object> params = new HashMap<>();

    public static RequestParams create() {
        return new RequestParams();
    }

    public static RequestParams create(String key, Object value) {
        return new RequestParams().put(key, value);
    }

    public RequestParams put(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public Map<String, Object> get() {
        return params;
    }

}
