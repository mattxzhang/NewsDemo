package com.kingyon.newslib.greendao.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * created by arvin on 16/8/3 15:16
 * email：1035407623@qq.com
 */
public class ColumnProxy implements InvocationHandler {
    private ColumnService columnService;

    public ColumnProxy(ColumnService columnService) {
        this.columnService = columnService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(columnService.getColumnEntityDao()==null){
            return null;
        }
        return method.invoke(columnService, args);

    }


}
