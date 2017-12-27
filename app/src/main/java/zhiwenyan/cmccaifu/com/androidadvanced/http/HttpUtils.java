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
    private boolean mCache;

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

    public HttpUtils cache(boolean cache) {
        this.mCache = cache;
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
            get(mCache, mUrl, mParams, callback);
        }
        if (mType == POST_TYPE) {
            post(mCache, mUrl, mParams, callback);
        }
    }

    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
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

    private void get(boolean cache, String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.get(cache, url, params, callBack);
    }

    private void post(boolean cache, String url, Map<String, Object> params, EngineCallBack callBack) {
        mIHttpEngine.post(cache, url, params, callBack);
    }

    //将一个泛型转化为实体类
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
