package zhiwenyan.cmccaifu.com.androidadvanced.http;

/**
 * Created by zhiwenyan on 5/24/17.
 */

public interface HttpEngineCallBack<T> {
    void success(T result);

    void error(Exception e);

    //默认
    public final HttpEngineCallBack Default_Call_Back = new HttpEngineCallBack() {
        @Override
        public void success(Object result) {

        }

        @Override
        public void error(Exception e) {

        }
    };
}
