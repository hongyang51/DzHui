package com.lanou3g.mydazahui.receive;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.Log;

import com.lanou3g.mydazahui.R;
import com.lanou3g.mydazahui.activity.PatchWorkOneActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dllo on 15/10/5.
 */
public class MyNetWorkBroadcastReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {

        }
        if (isOrderedBroadcast()) {

        }
        // 自定义消息处理
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
// 读取发送过来的消息数据
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.i("lanou", intent + "收到广播了" + msg);
            showNotificition(msg, context);
        }

    }

    public void showNotificition(String msg, Context context) {
        // 先获得NotificationManager管理对象
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new Builder(context);

        builder.setTicker(msg);
        builder.setContentTitle(msg);
        builder.setContentText(msg);
        builder.setWhen(java.lang.System.currentTimeMillis());
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.dzhtubiao);
        builder.setLargeIcon(icon);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setSmallIcon(R.mipmap.dzhreceiver);
        // 设置点击之后的跳转
        Intent intent = new Intent(context, PatchWorkOneActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // 发送系统通知
        manager.notify(1, builder.build());
    }
}
