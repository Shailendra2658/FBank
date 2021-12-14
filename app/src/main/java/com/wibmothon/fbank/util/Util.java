package com.wibmothon.fbank.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.sound.waves.LogHelper;

public class Util {


    public static class RegHandler extends Handler {

        private final static String TAG = "RegHandler";
        public final static int MSG_SET_RECG_TEXT = 1;
        public final static int MSG_RECG_START = 2;
        public final static int MSG_RECG_END = 3;

        private StringBuilder mTextBuilder = new StringBuilder();
        private TextView mRecognisedTextView;

        public RegHandler(TextView textView) {
            mRecognisedTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    mTextBuilder.append(ch);
                    if (null != mRecognisedTextView) {
                        //mRecognisedTextView.setText(mTextBuilder.toString());
                    }
                    break;

                case MSG_RECG_START:
                    mTextBuilder.delete(0, mTextBuilder.length());
                    break;

                case MSG_RECG_END:
                    LogHelper.d(TAG, "recognition end");
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
