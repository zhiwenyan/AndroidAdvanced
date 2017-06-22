package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhiwenyan on 5/24/17.
 * 自己的一套实现
 */

public class HttpUtils {
    private String mUrl;
    //请求方式
    private int mType = GET_TYPE;
    //get请求
    private static final int GET_TYPE = 0x01;
    //post请求
    private static final int POST_TYPE = 0x02;

    //默认是OkHttpEngine
    private static IHttpEngine mIHttpEngine = new OkHttpEngine();

    private Context mContext;

    //post的参数
    private HashMap<String, Object> mParams;

    private HttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    //链式调用
    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    public HttpUtils addParam(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    /**
     * @param params
     * @return
     */
    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void execute(EngineCallBack callback) {
        callback.onPreExecute(mContext, mParams);
        //每次执行都会进入这个方法
        if (callback == null) {
            callback = EngineCallBack.Default_Call_Back;
        }
        //判断执行方法
        if (mType == GET_TYPE) {
            get(mUrl, mParams, callback);
        }
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callback);
        }
    }

    public void execute() {
        execute(null);
    }


    public static void init(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;
    }

    public void exchangeEngine(IHttpEngine httpEngine) {
        mIHttpEngine = httpEngine;

    }

    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.get(url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.post(url, params, callBack);
    }

    //将一个泛型转化为实体类
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
