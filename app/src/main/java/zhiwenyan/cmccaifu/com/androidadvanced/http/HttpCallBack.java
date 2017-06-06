package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by zhiwenyan on 6/6/17.
 * <p>
 * 与项目逻辑有关的
 */

public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map params) {
        //名称
        params.put("app_name", "joke_essay");
        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("update_version_code", "5701");
        params.put("device_platform", "android");
        onPreExecute();
    }

    public void onPreExecute() {

    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        T objResult = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(objResult);
    }

    //可以直接返回操作额对象
    public abstract void onSuccess(T result);
}
