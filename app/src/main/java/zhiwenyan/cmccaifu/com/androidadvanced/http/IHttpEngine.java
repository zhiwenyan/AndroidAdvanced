package zhiwenyan.cmccaifu.com.androidadvanced.http;

import java.util.Map;

/**
 * Created by zhiwenyan on 5/24/17.
 */

public interface IHttpEngine {
    //get请求
    void get(String url, Map<String, Object> params, EngineCallBack callBack);

    //post请求
    void post(String url, Map<String, Object> params, EngineCallBack callBack);

}
