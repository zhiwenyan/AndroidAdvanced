package zhiwenyan.cmccaifu.com.androidadvanced.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import zhiwenyan.cmccaifu.com.androidadvanced.BaseApplication;
import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.activity.MainActivity;

/**
 * activity和service通信：通过binder
 */
public class UpdateVersionService extends Service {
    private final String tag = UpdateVersionService.class.getSimpleName();
    private Context context = this;
    private BaseApplication application;
    private DownloadBinder binder;
    private String apkUrl;// apk下载地址
    private String saveFileName;// 下载安装包路径
    private Thread downLoadThread;// 下载apk线程
    private int progress;// 进度条
    private final int NOTIFY_ID = 0;
    private NotificationManager notificationManager;
    private Notification notification;// 消息通知
    private Notification.Builder builder = null;
    private boolean canceled;
    private boolean serviceIsDestory = false;
    private int lastRate = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    //  Log.v(tag, "success---");下载完成并安装
                    application.setDownload(false);
                    notificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case 1:
                    int rate = msg.arg1;
                    //   Log.v(tag, "rate---");刷新进度
                    application.setDownload(true);
                    if (rate < 100) {
                        RemoteViews remoteView = notification.contentView;
                        remoteView.setTextViewText(R.id.tv_progress, rate + "%");
                        remoteView.setProgressBar(R.id.progressbar, 100, rate,
                                false);
                    } else {
                        //    Log.v(tag, "下载完成");
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        Intent intent = new Intent(context, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(
                                context, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentTitle(getResources().getString(
                                R.string.download_finish_title));
                        builder.setContentText(getResources().getString(
                                R.string.download_finish_text));
                        notification.contentIntent = contentIntent;
                        serviceIsDestory = true;
                        stopSelf();
                    }
                    notificationManager.notify(NOTIFY_ID, notification);
                    break;
                case 2:
                    // Log.v(tag, "cancel---");取消下载
                    application.setDownload(false);
                    notificationManager.cancel(NOTIFY_ID);
                    break;
                case 3:
                    // Log.v(tag, "error---");出现异常
                    application.setDownload(true);
                    notificationManager.cancel(NOTIFY_ID);
                    Toast.makeText(context,
                            getResources().getString(R.string.download_alter),
                            Toast.LENGTH_SHORT).show();
                    stopSelf();
                    break;
            }

        }

        ;
    };

    /**
     * 安装APK
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
//            ToastUtil.toasts(context,
//                    getResources().getString(R.string.download_alter));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //  Log.v(tag, "onCreate");
        application = ( BaseApplication ) getApplication();
        this.binder = new DownloadBinder();
        notificationManager = ( NotificationManager ) this
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //  Log.v(tag, "onStartCommand");
        apkUrl = intent.getStringExtra("apkUrl");
//        if (!StringUtil.isEmpty(apkUrl)) {
//            saveFileName = FileUtils.getAppPath(context, apkUrl).getPath();
//        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //Log.v(tag, "onDestroy");
        application.setDownload(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Log.v(tag, "onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //  Log.v(tag, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        //  Log.v(tag, "onRebind");
        super.onRebind(intent);
    }

    /**
     * 下载binder
     *
     * @author Administrator
     */
    public class DownloadBinder extends Binder {

        public void start() {
            if (downLoadThread == null || !downLoadThread.isAlive()) {
                setNotification();
                canceled = false;
                startDownloadApk();
            }
        }

        public void cancel() {
            canceled = true;
        }

        public int getProgress() {
            return progress;
        }

        public boolean getCanceled() {
            return canceled;
        }

        public boolean isDestoryService() {
            return serviceIsDestory;
        }

        public void cancleNotification() {
            handler.sendEmptyMessage(2);
        }
    }

    /**
     * 下载apk
     */
    private void startDownloadApk() {
        downLoadThread = new Thread(downApkRunnable);
        downLoadThread.start();
    }

    private Runnable downApkRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                // Log.v(tag, "apkUrl---" + apkUrl);
                HttpURLConnection connection = ( HttpURLConnection ) url
                        .openConnection();
                connection.connect();
                int length = connection.getContentLength();
                InputStream inputStream = connection.getInputStream();
                String apkUrls = saveFileName;
                // Log.v(tag, "apkUrls---" + apkUrls);
                File apkFile = new File(apkUrls);
                if (apkFile.exists()) {
                    Log.v(tag, "true---" + apkFile.getAbsolutePath());
                }
                FileOutputStream outputStream = new FileOutputStream(apkFile);
                int count = 0;
                byte[] buffer = new byte[1024];
                do {
                    int readNum = inputStream.read(buffer);
                    count += readNum;
                    progress = ( int ) ((( float ) count / length) * 100);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress > lastRate + 1) {
                        handler.sendMessage(msg);
                        lastRate = progress;
                    }
                    if (readNum <= 0) {
                        handler.sendEmptyMessage(0);
                        canceled = true;
                        break;
                    }
                    outputStream.write(buffer, 0, readNum);
                } while (!canceled);
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                Log.d("young", "Exception...." + e.toString());
                handler.sendEmptyMessage(3);
            }

        }
    };

    /**
     * 设置下载通知栏
     */
    private void setNotification() {
        // String tickerText = getResources().getString(R.string.download_start);
        String tickerText = "开始下载";
        long when = System.currentTimeMillis();
        builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(when);
        builder.setTicker(tickerText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        RemoteViews contentView = new RemoteViews(getPackageName(),
                R.layout.download_notification_layout);
        contentView.setTextViewText(R.id.tv_name,
                getResources().getString(R.string.download_title));
        notification.contentView = contentView;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        notificationManager.notify(NOTIFY_ID, notification);
    }
}
