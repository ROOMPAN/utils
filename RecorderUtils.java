package com.leshu.leshuvoice.__util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by liupanpan on 2017/4/14.
 */

public class RecorderUtils {
    private final String TAG = RecorderUtils.class.getName();
    private String path;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;


    public RecorderUtils(String path) {
        this.path = path;
        mRecorder = new MediaRecorder();
        mPlayer = new MediaPlayer();
    }

    /*
     * 开始录音
     * @return boolean
     */
    public void recorderstart() {
        //设置音源为Micphone
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置封装格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(path);
        //设置编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        //录音
        mRecorder.start();
    }

    /*
     * 停止录音
     * @return boolean
     */
    public void recorderstop() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public void payerstart() {
        try {
            //设置要播放的文件
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            //播放
            mPlayer.start();
        } catch (Exception e) {
            Log.e(TAG, "prepare() failed");
        }

    }

    public void playerstop() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
}
