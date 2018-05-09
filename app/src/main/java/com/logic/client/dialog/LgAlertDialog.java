package com.logic.client.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.logic.client.R;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/7
 * @desc
 */

public class LgAlertDialog {

    private static LgAlertDialog instance = null;

    public static LgAlertDialog getInstance() {
        if (instance == null) {
            instance = new LgAlertDialog();
        }
        return instance;
    }

    private LgAlertDialog(){}

    private AlertDialog dialog;

    public void showDialog(Context context, String titleInfo) {
        if (dialog != null)
            return;
        dialog = new AlertDialog.Builder(context).create();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        dialog.setView(inflate);
        dialog.setCancelable(true);
    }

}
