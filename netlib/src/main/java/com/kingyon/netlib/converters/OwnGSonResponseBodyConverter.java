package com.kingyon.netlib.converters;

import android.util.Log;

import com.google.gson.Gson;
import com.kingyon.netlib.entitys.ResultResponseEntity;
import com.kingyon.netlib.exception.ResultException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Leo on 2016/5/4
 */
public class OwnGSonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type adapter;

    OwnGSonResponseBodyConverter(Gson gson, Type adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            Log.d("Dream", "response>>" + response);
            ResultResponseEntity resultResponse = gson.fromJson(response, ResultResponseEntity.class);
            if (resultResponse.getStatus() == 200) {
                if (resultResponse.getContent() != null) {
                    return gson.fromJson(resultResponse.getContent(), adapter);
                }
                return null;
            } else {
                throw new ResultException(resultResponse.getStatus(), resultResponse.getMessage());
            }
        } finally {
            value.close();
        }

    }
}