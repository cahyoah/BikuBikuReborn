package com.example.adhit.bikubiku;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.adhit.bikubiku.ui.notification.NotificationBuilderInterceptor;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyActivity;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.data.remote.QiscusApi;
import com.qiscus.sdk.event.QiscusChatRoomEvent;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.event.QiscusUserStatusEvent;
import com.qiscus.sdk.util.QiscusRxExecutor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adhit on 03/01/2018.
 */

public class BikuBiku extends Application{
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }
    public static BikuBiku instance;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        initQiscus();
        sContext = this;
        instance =this;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    public static BikuBiku getInstance(){
        return  instance;
    }


    public void initQiscus(){
        Qiscus.init(this, "bikubiku-it3hra928qv7");

        Qiscus.getChatConfig()
                .setSwipeRefreshColorScheme(R.color.colorPrimary, R.color.colorAccent)
                .setLeftBubbleColor(R.color.colorWhite)
                .setNotificationBigIcon(R.drawable.logo)
                .setNotificationSmallIcon(R.drawable.logo)
                .setOnlyEnablePushNotificationOutsideChatRoom(false)
                .setNotificationClickListener(new NotificationClickListener() {
                    @Override
                    public void onClick(Context context, QiscusComment qiscusComment) {
                        QiscusRxExecutor.execute(QiscusApi.getInstance().getChatRoom(qiscusComment.getRoomId()),
                                new QiscusRxExecutor.Listener<QiscusChatRoom>() {
                                    @Override
                                    public void onSuccess(QiscusChatRoom qiscusChatRoom) {
                                        Intent intent = ChattingPsychologyActivity.generateIntent(getApplicationContext(), qiscusChatRoom, false);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onError(Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                });
                    }
                })
                .setNotificationBuilderInterceptor(new NotificationBuilderInterceptor())
                .setLeftBubbleTextColor(R.color.color_black)
                .setLeftBubbleTimeColor(R.color.qiscus_secondary_text)
                .setLeftLinkTextColor(R.color.qiscus_primary_text)
                .setLeftProgressFinishedColor(R.color.colorPrimary)
                .setRightBubbleColor(R.color.colorGreen400)
                .setRightBubbleTextColor(R.color.color_black)
                //.setRightProgressFinishedColor(R.color.colorPrimaryLight)
                .setSelectedBubbleBackgroundColor(R.color.colorPrimary)
                .setReadIconColor(R.color.colorPrimary)
                .setAppBarColor(R.color.colorPrimary)
                .setStatusBarColor(R.color.colorPrimaryDark)
                .setAccentColor(R.color.colorAccent)
                .setAccountLinkingTextColor(R.color.colorPrimary)
               // .setAccountLinkingBackground(R.color.accountLinkingBackground)
                .setButtonBubbleTextColor(R.color.colorPrimary)
                //.setButtonBubbleBackBackground(R.color.accountLinkingBackground)
                .setReplyBarColor(R.color.colorPrimary)
                .setReplySenderColor(R.color.colorPrimary)
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off)
                //.setStopRecordIcon(R.drawable.ic_send_record)
                .setEnableAddFile(true)
                //.setCancelRecordIcon(R.drawable.ic_cancel_record)
                .setEnablePushNotification(true);
                //.setInlineReplyColor(R.color.colorPrimaryLight);
    }

    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {

    }

    @Subscribe
    public void onUserChanged(QiscusChatRoomEvent qiscusChatRoomEvent){
        if(qiscusChatRoomEvent.isTyping()){
            ChattingPsychologyFragment chatFragment = new ChattingPsychologyFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), true);
        }else{
            ChattingPsychologyFragment chatFragment = new ChattingPsychologyFragment();
            chatFragment.onUserTyping(qiscusChatRoomEvent.getUser(), false);
        }
    }

    @Subscribe
    public  void onUserTyping(QiscusUserStatusEvent qiscusUserStatusEvent){
        if(qiscusUserStatusEvent.isOnline()){
            ChattingPsychologyFragment.onUserChanged(true);
        }else {
            ChattingPsychologyFragment.onUserChanged(false);
        }
    }

}
