package com.example.circularprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircularSeekbar extends View {

    private final Paint paint = new Paint();
    private final Paint txtPaint = new Paint();
    private int startRange = 0, endRange = 100, signPos = 30;
    private int flag = 0, flag2 = 0;
    private String mainColor = "#aaaaaa";
    private Bitmap bitmap;
    int ic_happy = R.drawable.ic_green;
    int ic_sad = R.drawable.ic_red;
    int ic_modest = R.drawable.ic_orange;
    private int biggerCircleR, smallerCircleR;
    private RectF biggerRectF, smallerRectF;
    private int sweepAngle;
    private int numOfLines = 30;
    private Shader shader;
    private float biggerTxtSize = 120;
    private float smallerTxtSize = 80;
    private String biggerTxt = String.valueOf(100);
    private String smallerTxt = "گیگ";
    private String state = "green";

    private enum states {
        green, orange, red
    }


    public CircularSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(3f);
        paint.setColor(Color.parseColor(mainColor));
        //   bitmap = getBitmap(ic_happy);
        txtPaint.setStrokeWidth(20f);
        txtPaint.setStyle(Paint.Style.STROKE);
        txtPaint.setStrokeCap(Paint.Cap.ROUND);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        paint.setTextAlign(Paint.Align.CENTER);
    }


    @SuppressLint({"ResourceAsColor", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        if (flag == 0) {//set for first running
            biggerCircleR = getWidth() / 4;
            smallerCircleR = 8 * biggerCircleR / 13;
            biggerTxtSize = 3 * smallerCircleR / 4;
            smallerTxtSize = 3 * biggerTxtSize / 4;
            biggerRectF = new RectF(getWidth() / 2 - biggerCircleR, getHeight() / 2 - biggerCircleR, getWidth() / 2 + biggerCircleR, getHeight() / 2 + biggerCircleR);
            smallerRectF = new RectF(getWidth() / 2 - smallerCircleR, getHeight() / 2 - smallerCircleR, getWidth() / 2 + smallerCircleR, getHeight() / 2 + smallerCircleR);
            flag = 1;
        }

        drawBase(canvas, paint);

        String color1 = null, color2 = null, color3 = null;
        switch (state) {
            case "green":
                color1 = "#f7931e";
                color2 = "#008744";
                color3 = "#00d170";
                bitmap = getBitmap(ic_happy);
                break;
            case "orange":
                color1 = "#ffc425";
                color2 = "#ed1c24";
                color3 = "#00d170";
                bitmap = getBitmap(ic_modest);
                break;
            case "red":
                color1 = "#f7931e";
                color2 = "#ed1c24";
                color3 = "#d62d20";
                bitmap = getBitmap(ic_sad);
                break;
        }

        drawArcs(canvas, color1, color2, color3);
        drawBitMap(canvas, paint);
        drawCentralTxt(canvas, txtPaint);

    }

    private void drawArcs(Canvas canvas, String color1, String colo2, String colo3) {
        //     txtPaint.setShader(shader);
        txtPaint.setStrokeWidth(20f);
        txtPaint.setStyle(Paint.Style.STROKE);
        int end = 60;
        if (signPos <= 60)
            end = signPos;
        shader = new LinearGradient(getWidth() / 4, 0, 3 * getWidth() / 4, 0, Color.parseColor(color1), Color.parseColor(colo2), Shader.TileMode.CLAMP);
        txtPaint.setShader(shader);
        canvas.drawArc(biggerRectF, 180, -1 * changePosToAngle(end), false, txtPaint);

        if (signPos > 60) {
            shader = new LinearGradient(getWidth() / 4, 0, 3 * getWidth() / 4, 0, Color.parseColor(colo3), Color.parseColor(colo2), Shader.TileMode.CLAMP);
            txtPaint.setShader(shader);
            canvas.drawArc(biggerRectF, 0, -1 * changePosToAngle(signPos - 60), false, txtPaint);
        }
    }

    private void drawCentralTxt(Canvas canvas, Paint txtPaint) {
        txtPaint.setStrokeWidth(6f);
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextSize(biggerTxtSize);
        canvas.drawText(biggerTxt, getWidth() / 2, getHeight() / 2, txtPaint);
        txtPaint.setTextSize(smallerTxtSize);
        canvas.drawText(smallerTxt, getWidth() / 2, getHeight() / 2 + biggerTxtSize, txtPaint);
    }

    private void drawBitMap(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float) (getWidth() / 2 - biggerCircleR * Math.cos(Math.toRadians(30))), (float) (getHeight() / 2 - biggerCircleR * Math.sin(Math.toRadians(30))), biggerCircleR / 8, paint);
        canvas.drawBitmap(bitmap, null, new RectF((float) (getWidth() / 2 - biggerCircleR * Math.cos(Math.toRadians(30))) - biggerCircleR / 8,
                (float) (getHeight() / 2 - biggerCircleR * Math.sin(Math.toRadians(30))) - biggerCircleR / 8,
                (float) (getWidth() / 2 - biggerCircleR * Math.cos(Math.toRadians(30))) + biggerCircleR / 8,
                (float) (getHeight() / 2 - biggerCircleR * Math.sin(Math.toRadians(30))) + biggerCircleR / 8), null);
    }

    private void drawBase(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor(mainColor));

        // sweepAngle =changePosToAngle(signPos);
        canvas.drawArc(biggerRectF, 0, (360), false, paint);

        int start = 0;
        for (int i = 0; i < numOfLines; i++) {
            canvas.drawArc(smallerRectF, start, 360 / (2 * numOfLines), false, paint);
            start += 360 / numOfLines;
        }

        addNums(canvas, paint);
    }

    private void addNums(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(biggerTxtSize/4);

        for (int i = 0; i <= 100; i += 10) {
            canvas.drawText(String.valueOf(i), (float) (getWidth() / 2 - 5 * biggerCircleR / 4 * Math.cos(Math.toRadians(i * -3)))
                    , (float) (getHeight() / 2 - 5 * biggerCircleR / 4 * Math.sin(Math.toRadians(i * -3))) + 1 * biggerCircleR / 12, paint);
        }

    }

    private int changePosToAngle(int pos) {
        sweepAngle = pos * 300 / 100;
        return sweepAngle;
    }


    private Bitmap getBitmap(int drawableRes) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


//    public void setRange(int startRange, int endRange) {
//        this.startRange = startRange;
//        this.endRange = endRange;
//    }


    public void setOnProgress(int x) {
        signPos = x;
        sweepAngle = changePosToAngle(x);
        invalidate();
    }

    public void setBiggerTxtSize(float biggerTxtSize) {
        this.biggerTxtSize = biggerTxtSize;
    }

    public void setSmallerTxtSize(float smallerTxtSize) {
        this.smallerTxtSize = smallerTxtSize;
    }

    public void setBiggerTxt(String biggerTxt) {
        this.biggerTxt = biggerTxt;
    }

    public void setSmallerTxt(String smallerTxt) {
        this.smallerTxt = smallerTxt;
    }

    public void setState(String state) {
        this.state = state;
    }
}
