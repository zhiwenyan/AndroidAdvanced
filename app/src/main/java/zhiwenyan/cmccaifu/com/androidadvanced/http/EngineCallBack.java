package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by zhiwenyan on 5/24/17.
 */

public interface EngineCallBack {
    //执行之前回调的方法
    void onPreExecute(Context context, Map<String, Object> params);

    //成功的方法
    void success(String result);

    void error(Exception e);

    //默认
    public final EngineCallBack Default_Call_Back = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map params) {

        }

        @Override
        public void success(String result) {

        }

        @Override
        public void error(Exception e) {

        }
    };
}
