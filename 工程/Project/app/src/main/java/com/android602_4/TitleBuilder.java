package com.android602_4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 1 on 2018/6/29.
 */

public class TitleBuilder {

    private View titleView;
    private RelativeLayout titleBar;
    private TextView text;
    private ImageView leftIco;
    private ImageView rightIco;
    private Context context;

    /**
     * 构造方法：用于获取对象
     */
    public TitleBuilder(FragmentActivity context) {
        this.context = context;
        titleView = context.findViewById(R.id.title_bar);
        text = (TextView) titleView.findViewById(R.id.title_text);
        titleBar = (RelativeLayout) titleView.findViewById(R.id.title_bar);
        leftIco = (ImageView) titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageView) titleView.findViewById(R.id.title_rightIco);
    }

    public TitleBuilder(Activity context,int id){
        this.context = context;
        titleView = context.findViewById(id);
        text = (TextView) titleView.findViewById(R.id.title_text);
        titleBar = (RelativeLayout) titleView.findViewById(R.id.title_bar);
        leftIco = (ImageView) titleView.findViewById(R.id.title_leftIco);
        rightIco = (ImageView) titleView.findViewById(R.id.title_rightIco);
    }


    /**
     * 用于设置标题栏文字
     */
    public TitleBuilder setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            text.setText(titleText);
        }
        return this;
    }

    /**
     * 用于设置标题栏左边要显示的图片
     */
    public TitleBuilder setLeftIco(int resId) {
        leftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        leftIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏左边是否显示
     */
    public TitleBuilder setLeftIcoVisiable(boolean visiable){
        leftIco.setVisibility(visiable ? View.VISIBLE:View.GONE);
        return this;
    }

    /**
     * 用于设置标题栏左边要显示的图片为圆形
     */
    public TitleBuilder setLeftCircleIco(int resId) {
//        leftIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        if (resId > 0)
            this.setLeftIcoVisiable(true);
        else
            leftIco.setVisibility(View.GONE);
        leftIco.setImageResource(resId);
        Bitmap bm =((BitmapDrawable) (leftIco).getDrawable()).getBitmap();
        leftIco.setImageBitmap(createCircleImage(bm));
        return this;
    }

    /**
     * 用于设置标题栏右边是否显示
     */
    public TitleBuilder setRightIcoVisiable(boolean visiable){
        leftIco.setVisibility(visiable ? View.VISIBLE:View.GONE);
        return this;
    }

    /**
     * 用于设置标题栏右边要显示的图片
     */
    public TitleBuilder setRightIco(int resId) {
        rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        rightIco.setImageResource(resId);
        return this;
    }

    /**
     * 用于设置标题栏左边图片的单击事件
     */
    public TitleBuilder setLeftIcoListening(View.OnClickListener listener) {
        if (leftIco.getVisibility() == View.VISIBLE) {
            leftIco.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边图片的单击事件
     */
    public TitleBuilder setRightIcoListening(View.OnClickListener listener) {
        if (rightIco.getVisibility() == View.VISIBLE) {
            rightIco.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 将图片剪裁为圆形
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

}
