package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zhiwenyan.cmccaifu.com.androidadvanced.db.DaoSupportFactory;
import zhiwenyan.cmccaifu.com.androidadvanced.db.IDaoSupport;

/**
 * Created by zhiwenyan on 5/24/17.
 * <p>
 * okHttp默认的引擎
 */

public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void get(boolean cache, String url, Map<String, Object> params, final EngineCallBack callBack) {
        if (cache) {
            //key 是url，json是后台返回的json数据
            IDaoSupport<CacheData> daoSupport = DaoSupportFactory.getInstance().getDao(CacheData.class);
            List<CacheData> cacheDatas = daoSupport.querySupport().selection("mUrlKey=?").selection(url).query();
            if (cacheDatas.size() != 0) {
                CacheData cacheData = cacheDatas.get(0);
                String resultJson = cacheData.getResultJson();
                if (!TextUtils.isEmpty(resultJson)) {
                    callBack.success(resultJson);
                }
            }
        }
        final Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.error(e);
                Log.i("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这2个方法回调不在主线程方法中
                //执行成功之后，转化为可操作的对象
                String result = response.body().string();
                Log.i("TAG", "onResponse: " + result);
                callBack.success(result);
            }
        });
    }

    @Override
    public void post(boolean cache, String url, Map<String, Object> params, EngineCallBack callBack) {
        String json = "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + "Steven" + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + "Steven" + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
