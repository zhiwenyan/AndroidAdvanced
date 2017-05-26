package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhiwenyan on 5/24/17.
 */

public class HttpUtils implements IHttpEngine {
    private String mUrl;
    private int mType = GET_TYPE;
    public static final int GET_TYPE = 0x01;
    public static final int POST_TYPE = 0x02;

    //默认是OkhttpEngine
    private static IHttpEngine mIHttpEngine = new OkhttpEngine();

    private Context mContext;
    private HashMap<String, Object> mParams;

    public HttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
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

    public HttpUtils addParam(String key, String value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    //添加回调
    public void execute(HttpEngineCallBack callback) {
        if (callback == null) {
            callback = HttpEngineCallBack.Default_Call_Back;
        }
        if (mType == POST_TYPE) {
            get(mUrl, mParams, callback);
        }
        if (mType == GET_TYPE) {
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

    @Override
    public void get(String url, Map<String, Object> params, HttpEngineCallBack callBack) {
        mIHttpEngine.get(url, params, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, HttpEngineCallBack callBack) {
        mIHttpEngine.post(url, params, callBack);

    }
}
