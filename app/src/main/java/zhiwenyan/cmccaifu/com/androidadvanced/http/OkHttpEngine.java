package zhiwenyan.cmccaifu.com.androidadvanced.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zhiwenyan.cmccaifu.com.androidadvanced.db.CacheDataUtil;

/**
 * Created by zhiwenyan on 5/24/17.
 * <p>
 * okHttp默认的引擎
 */

public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public void get(final boolean cache, final String url, final Map<String, Object> params, final EngineCallBack callBack) {

        final String finalUrl = HttpUtils.jointParams(url, params);

        final Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.error(e);
                Log.i("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这2个方法回调不在主线程方法中
                //执行成功之后，转化为可操作的对象
                String resultJson = response.body().string();
                Log.i("TAG", "onResponse: " + resultJson);
                if (cache) {
                    //key 是url，json是后台返回的json数据

                    String cacheResultJson = CacheDataUtil.getCacheResultJson(finalUrl);
                    Log.e("cacheResultJson", "onResponse: " + cacheResultJson);
                    if (!TextUtils.isEmpty(cacheResultJson)) {
                        if (resultJson.equals(cacheResultJson)) {
                            Log.e("TAG", "onResponse: 数据缓存与请求到的数据一致");
                            return;
                        }
                        callBack.success(cacheResultJson);
                    }

                }
                callBack.success(resultJson);
                if (cache) {
                    CacheDataUtil.cacheData(finalUrl, resultJson);
                }
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
