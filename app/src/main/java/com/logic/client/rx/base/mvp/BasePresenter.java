package com.logic.client.rx.base.mvp;


import android.content.Context;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    private M model;
    private V view;
    public Context ctx;

    public void  onStart (){

    }

    public void onDestroy(){

    }
    public BasePresenter() {
        this.model = initModel();
    }

    protected void setView(V view) {
        this.view = view;
    }

    protected M getModel() {
        return model;
    }

    protected V getView() {
        return view;
    }

    protected abstract M initModel();


}
