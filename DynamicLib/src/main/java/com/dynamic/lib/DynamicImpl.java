package com.dynamic.lib;

/**
 * Created by ${zhaoyanjun} on 2017/3/2.
 */

public class DynamicImpl implements Dynamic {
    @Override
    public String sayHello() {
        return new StringBuilder(getClass().getName()).append(" is loaded by DexClassLoader").toString();
    }
}
