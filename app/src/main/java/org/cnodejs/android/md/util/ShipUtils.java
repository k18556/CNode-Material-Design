package org.cnodejs.android.md.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.cnodejs.android.md.R;
import org.cnodejs.android.md.display.activity.TopicActivity;
import org.cnodejs.android.md.display.activity.UserDetailActivity;
import org.cnodejs.android.md.display.widget.ToastUtils;
import org.cnodejs.android.md.model.api.ApiDefine;

public final class ShipUtils {

    private ShipUtils() {}

    public static void openInAppStore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("您的系统中没有安装应用商店");
        }
    }

    public static void openInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("您的系统中没有安装浏览器");
        }
    }

    public static void sendEmail(Context context, String email, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.with(context).show("您的系统中没有安装邮件客户端");
        }
    }

    public static void share(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static void handleLink(Context context, String url) {
        if (FormatUtils.isUserLinkUrl(url)) {
            UserDetailActivity.start(context, Uri.parse(url).getPath().replace(ApiDefine.USER_PATH_PREFIX, ""));
        } else if (FormatUtils.isTopicLinkUrl(url)) {
            TopicActivity.start(context, Uri.parse(url).getPath().replace(ApiDefine.TOPIC_PATH_PREFIX, ""));
        } else {
            ShipUtils.openInBrowser(context, url);
        }
    }

}
