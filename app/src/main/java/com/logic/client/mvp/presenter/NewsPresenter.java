package com.logic.client.mvp.presenter;

import com.logic.client.R;
import com.logic.client.app.AppConstants;
import com.logic.client.bean.IdataNews;
import com.logic.client.bean.NewsChannelTabs;
import com.logic.client.mvp.model.NewsModel;
import com.logic.client.mvp.view.fragment.NewsFragment;
import com.logic.client.rx.RxBus;
import com.logic.client.rx.RxSchedulers;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BasePresenter;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/28
 * @desc
 */

public class NewsPresenter extends BasePresenter<NewsModel,NewsFragment> {


    /**
     * @param iData
     * @param type
     * @param page
     */
    public void getQQnews(final List<IdataNews.Idate> iData, String type, int page){
            getNews(iData,type,page,"qq.com");
    }

    public void getNews(final List<IdataNews.Idate> iData, String type, int page, String site) {

        getModel().getNews(type, page, site)
                .map(new Function<IdataNews, List<IdataNews.Idate>>() {
                    private boolean hasNext;
                    @Override
                    public List<IdataNews.Idate> apply(@NonNull IdataNews idataNews) throws Exception {
                        List<IdataNews.Idate> data = idataNews.getData();
                        hasNext = idataNews.getHasNext();
                        if (data.size() > 0){
                            int size = data.size();
                            for (int i = 0; i <size; i++) {
                                IdataNews.Idate item = data.get(i);
                                List<String> imageUrls = item.getImageUrls();
                                if (imageUrls!=null){
                                    int len = imageUrls.size();
                                    if (len<=0){
                                        item.setItemType(IdataNews.PIC_NO);
                                    }else if (len==1){
                                        item.setItemType(IdataNews.PIC_ONLY);
                                    }else if (len>1){
                                        item.setItemType(IdataNews.PIC_MULTI);
                                        if (len>3){
                                            ArrayList<String> temp=new ArrayList<>();
                                            for (int j = 0; j <3 ; j++) {
                                                temp.add(imageUrls.get(j));
                                            }
                                            item.setImageUrls(temp);
                                        }
                                    }
                                }else
                                    item.setItemType(IdataNews.PIC_NO);
                            }
                            iData.addAll(data);
                        }
                        return data;
                    }
                })
                .compose(RxSchedulers.<List<IdataNews.Idate>>FlowableToMain())
                .subscribe(new FlowableSubscriber<List<IdataNews.Idate>>() {

                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(1000);
                        getView().showLoading(BaseApplication.getResource().getString(R.string.loadding));
                    }

                    @Override
                    public void onNext(List<IdataNews.Idate> idates) {
                        getView().returnData(idates);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        getView().showError(t.toString());
                    }

                    @Override
                    public void onComplete() {
                        getView().stopLoading();
                    }
                });
    }


    public void ScrollToTop(){
        Disposable event = RxBus.getIntance().doSubscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
               getView().ScrollToTop();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        RxBus.getIntance().addSubscription(AppConstants.NEWS_TO_TOP,event);
    }

    @Override
    public void onStart() {
        super.onStart();
        ScrollToTop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntance().unSubscribe(AppConstants.NEWS_TO_TOP);
    }

    @Override
    protected NewsModel initModel() {
        return new NewsModel();
    }
}
