package com.kingyon.baseuilib.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingyon.baseuilib.R;

/**
 * created by arvin on 16/7/31 21:58
 * email：1035407623@qq.com
 */
public class ProgressDialog extends Dialog {

    private TextView tvMsg;
    private ImageView imgProgress;
    private MaterialProgressDrawable progress;

    public ProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setContentView(R.layout.ui_dialog_progress);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER;
        attributes.dimAmount = 0.0f;
    }

    @Override
    public void show() {
        super.show();
        if (imgProgress == null || progress == null) {
            imgProgress = (ImageView) getWindow().findViewById(R.id.img_progress);
            progress = new MaterialProgressDrawable(getContext(), imgProgress);
            progress.setBackgroundColor(0xFFFAFAFA);
            progress.setAlpha(255);
            progress.setColorSchemeColors(getContext().getResources().getColor(R.color.colorAccent));
            imgProgress.setImageDrawable(progress);
        }
        progress.start();
    }

    public ProgressDialog setMessage(String strMessage) {
        if (tvMsg == null) {
            tvMsg = (TextView) getWindow().findViewById(R.id.tv_message);
        }
        tvMsg.setText(strMessage);

        return this;
    }

    @Override
    public void dismiss() {
        progress.stop();
        super.dismiss();
    }

}
