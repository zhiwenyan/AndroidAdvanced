package zhiwenyan.cmccaifu.com.androidadvanced.http;

/**
 * Description:
 * Data：12/26/2017-5:30 PM
 *
 * @author: yanzhiwen
 */
public class CacheData {
    private String mUrlKey;
    private String mResultJson;

    public CacheData(String urlKey, String resultJson) {
        mUrlKey = urlKey;
        mResultJson = resultJson;
    }

    public String getUrlKey() {
        return mUrlKey;
    }

    public void setUrlKey(String urlKey) {
        mUrlKey = urlKey;
    }

    public String getResultJson() {
        return mResultJson;
    }

    public void setResultJson(String resultJson) {
        mResultJson = resultJson;
    }
}
