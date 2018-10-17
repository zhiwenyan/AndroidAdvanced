package zhiwenyan.cmccaifu.com.androidadvanced.utils;

/**
 * Description:
 * Data：10/17/2018-10:05 AM
 *
 * @author yanzhiwen
 */
public class PatchUtils {

    /**
     *
     * @param oldApkPath
     * @param newApkPath
     * @param patchPath
     */
    public static native void combine(String oldApkPath,String newApkPath,String patchPath);
}
