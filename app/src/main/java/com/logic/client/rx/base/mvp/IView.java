package com.logic.client.rx.base.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/18
 * @desc
 */

public interface IView {

    void showError(String msg);
    void showLoading(String title);
    void stopLoading();

}
