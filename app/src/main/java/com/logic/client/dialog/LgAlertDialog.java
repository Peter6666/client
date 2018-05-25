package com.logic.client.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.logic.client.R;
import com.logic.client.rx.base.BaseApplication;

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

    private AlertDialog mDialog=null;

    public void showDialog(String titleInfo) {
        if (mDialog != null)
            return;
        mDialog = new AlertDialog.Builder(BaseApplication.getAppContext()).create();
        View inflate = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.dialog_view, null);
        inflate.findViewById(R.id.ly_dialog).setVisibility(View.VISIBLE);
        inflate.findViewById(R.id.ly_loadding).setVisibility(View.GONE);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        mDialog.setView(inflate);
        mDialog.setCancelable(true);
    }

    public void showLoadding() {
        if (mDialog != null)
            return;
        mDialog = new AlertDialog.Builder(BaseApplication.getAppContext()).create();
        View inflate = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.dialog_view, null);
        inflate.findViewById(R.id.ly_dialog).setVisibility(View.GONE);
        inflate.findViewById(R.id.ly_loadding).setVisibility(View.VISIBLE);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_load);
        mDialog.setView(inflate);
        mDialog.setCancelable(false);
    }

    public void  stopDialog(){
        if (mDialog !=null){
            mDialog.dismiss();
            mDialog =null;
        }
    }
}
