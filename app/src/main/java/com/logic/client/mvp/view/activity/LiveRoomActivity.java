package com.logic.client.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.logic.client.R;
import com.logic.client.bean.LiveRoom;
import com.logic.client.bean.StreamSrc;
import com.logic.client.mvp.presenter.LiveRoomPresenter;
import com.logic.client.rx.base.BaseApplication;
import com.logic.client.rx.base.mvp.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/5/24
 * @desc
 */

public class LiveRoomActivity extends BaseActivity<LiveRoomPresenter> implements SurfaceHolder.Callback, View.OnClickListener {
    @BindView(R.id.frameVideo)
    RelativeLayout frameVideo;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.tv_room_title)
    TextView tvRoomTitle;
    @BindView(R.id.iv_full_screen)
    ImageView ivFullScreen;
    @BindView(R.id.rl_room_info)
    RelativeLayout rlRoomInfo;
    @BindView(R.id.videoContent)
    RelativeLayout videoContent;
    @BindView(R.id.iv_favicon)
    CircleImageView ivFavicon;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_viewer)
    TextView tvViewer;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.tvEmotionalState)
    TextView tvEmotionalState;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.sv_video)
    SurfaceView svVideo;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.sv_info)
    ScrollView svInfo;

    private String mUid;
    private MediaPlayer mMediaPlayer;
    private String mPath;

    public static void main(Context ctx, String uid) {
        Intent intent = new Intent(ctx, LiveRoomActivity.class);
        intent.putExtra("uid", uid);
        ctx.startActivity(intent);

    }

    @Override
    protected LiveRoomPresenter initPresenter() {
        return new LiveRoomPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_liveroom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        updateVideoLayoutParams();

        mUid = getIntent().getStringExtra("uid");
        mPresenter.getLiveRoom(mUid);

        ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLandscape()){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLandscape()){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if(isLandscape()){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(isLandscape()){
            svInfo.setVisibility(View.GONE);
            ivFullScreen.setVisibility(View.GONE);
        }else{
            svInfo.setVisibility(View.VISIBLE);
            ivFullScreen.setVisibility(View.VISIBLE);
        }
        updateVideoLayoutParams();

    }

    @Override
    protected void initData() {

    }

    public void returnData(LiveRoom liveRoom) {
        String avatar = liveRoom.getAvatar();
        if (isNoEmpty(avatar)) {
            Glide.with(mActivity)
                    .load(avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivFavicon);
        }

        String announcement = liveRoom.getAnnouncement();
        if (isNoEmpty(announcement))
            tvNotice.setText(announcement);

        String getNick = liveRoom.getNick();
        if (isNoEmpty(getNick))
            tvNick.setText(getNick);

        int follow = liveRoom.getFollow();
        tvFans.setText("粉丝:" + follow);

        int getView = liveRoom.getView();
        tvViewer.setText("战斗力:" + getView);

        int getNo = liveRoom.getNo();
        tvAccount.setText("直播号:" + getNo);
    }

    public boolean isNoEmpty(String s) {

        if (s != null && !s.isEmpty())
            return true;
        return false;
    }

    public boolean isLandscape(){
        return mActivity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    public void updateVideoLayoutParams(){
        ViewGroup.LayoutParams lp = videoContent.getLayoutParams();
        if(isLandscape()){
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                resourceId = getResources().getDimensionPixelSize(resourceId);
            }
            lp.height = mActivity.getResources().getDisplayMetrics().heightPixels-resourceId;
        }else{
            lp.height = (int)(mActivity.getResources().getDisplayMetrics().widthPixels / 16.0f * 9.0f);
        }

        videoContent.setLayoutParams(lp);
    }

    public void showLive(LiveRoom.LiveBean.WsBean.FlvBean flv) {
        StreamSrc value = flv.getValue(false);
        mPath = value.getSrc();
        if (mPath != null) {
            svVideo.getHolder().addCallback(this);
            svVideo.setOnClickListener(this);
            svVideo.getHolder().setFormat(PixelFormat.RGBA_8888);

//            try {
//                mMediaPlayer.setDataSource(src);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mMediaPlayer = new MediaPlayer(this);
        try {
            mMediaPlayer.setDisplay(holder);
//            mMediaPlayer.setDataSource(mActivity,Uri.parse("cache:/sdcard/logic:" + mPath));
            mMediaPlayer.setDataSource(mPath);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    pbLoad.setVisibility(View.GONE);
                }
            });

            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                    if (percent>=100){
//                        pbLoad.setVisibility(View.GONE);
//                        tvPercent.setVisibility(View.GONE);
//                    }else {
//                        pbLoad.setVisibility(View.VISIBLE);
//                        tvPercent.setVisibility(View.VISIBLE);
//                        tvPercent.setText("已缓冲：" + percent + "% 请稍等");
//                    }

                }
            });

//            mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                @Override
//                public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                    switch (what) {
//                        //开始缓冲
//                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                            tvPercent.setVisibility(View.VISIBLE);
//                            pbLoad.setVisibility(View.VISIBLE);
//                            mp.pause();
//                            break;
//                        //缓冲结束
//                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                            tvPercent.setVisibility(View.GONE);
//                            pbLoad.setVisibility(View.GONE);
//                            mp.start(); //缓冲结束再播放
//                            break;
//                        //正在缓冲
//                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
//
//                            break;
//                    }
//                    return true;
//                }
//            });

            mMediaPlayer.prepareAsync();
            //缓存2M
            mMediaPlayer.setBufferSize(1024 * 1024 * 5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        //暂停
//        if (mMediaPlayer != null) {
//            if (mMediaPlayer.isPlaying()) {
//                //暂停
////                iv.setVisibility(View.VISIBLE);
////                iv.setImageResource(R.mipmap.pause);
//                mMediaPlayer.pause();
//            } else {
////                iv.setImageResource(R.mipmap.play);
//                mMediaPlayer.start();
////                iv.setVisibility(View.GONE);
//            }
//        }
    }

}
