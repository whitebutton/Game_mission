package com.example.administrator.game_mission.More.lh.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Turntable extends View {
    private Context context;
    private Paint dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int radius = 0;
    private int panNum = 10;
    private int InitAngle;
    private int diffRadius;
    private int verPanRadius;
    private Integer[] images;
    private String[] strs;
    private List<Bitmap> bitmaps = new ArrayList<>();

    public Turntable(Context context) {
        super(context, null);
    }

    public Turntable(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        init();

    }

    public Turntable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        InitAngle = 360 / panNum;
        verPanRadius = 360 / panNum;
        diffRadius = verPanRadius / 2;
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dip2px(context, 14));
        dPaint.setColor(Color.parseColor("#9cdebf"));
        sPaint.setColor(Color.parseColor("#26e48b"));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int MinValue = Math.min(width, height);

        radius = MinValue / 2;

        RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), width, height);

        int angle = (panNum % 4 == 0) ? InitAngle : InitAngle - diffRadius;
        for (int i = 0; i < panNum; i++) {
            if (i % 2 == 0) {
                canvas.drawArc(rectF, angle, verPanRadius, true, dPaint);
            } else {
                canvas.drawArc(rectF, angle, verPanRadius, true, sPaint);
            }
            angle += verPanRadius;
        }

        if (strs != null && strs.length == panNum) {
            for (int i = 0; i < panNum; i++) {
                drawText((panNum % 4 == 0) ? InitAngle + diffRadius + (diffRadius * 3 / 4) : InitAngle + diffRadius, strs[i], 2 * radius, textPaint, canvas, rectF);
                InitAngle += verPanRadius;
            }
        } else {
            Log.d("TAG", "文字数量不符合设置");
        }

        if (images != null && images.length == 8) {
            for (int i = 0; i < panNum; i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[i]);
                bitmaps.add(bitmap);
            }

            for (int i = 0; i < panNum; i++) {
                drawIcon(width / 2, height / 2, radius, (panNum % 4 == 0) ? InitAngle + diffRadius : InitAngle, i, canvas);
                InitAngle += verPanRadius;
            }
        } else {
            Log.d("TAG", "图片数量不符合设置");
        }


    }

    private void drawIcon(int xx, int yy, int mRadius, float startAngle, int i, Canvas mCanvas) {

        int imgWidth = mRadius / 4;

        float angle = (float) Math.toRadians(verPanRadius + startAngle);

        //确定图片在圆弧中 中心点的位置
        float x = (float) (xx + (mRadius / 2 + mRadius / 12) * Math.cos(angle));
        float y = (float) (yy + (mRadius / 2 + mRadius / 12) * Math.sin(angle));

        // 确定绘制图片的位置
        RectF rect = new RectF(x - imgWidth * 2 / 3, y - imgWidth * 2 / 3, x + imgWidth
                * 2 / 3, y + imgWidth * 2 / 3);

        Bitmap bitmap = bitmaps.get(i);

        mCanvas.drawBitmap(bitmap, null, rect, null);
    }

    private void drawText(float startAngle, String string, int mRadius, Paint mTextPaint, Canvas mCanvas, RectF mRange) {
        Path path = new Path();

        path.addArc(mRange, startAngle, verPanRadius);
        float textWidth = mTextPaint.measureText(string);

        //圆弧的水平偏移
        float hOffset  = (panNum % 4 == 0)?((float) (mRadius * Math.PI / panNum/2 ))
                :((float) (mRadius * Math.PI / panNum/2 - textWidth/2 ));
        //圆弧的垂直偏移
        float vOffset = mRadius / 2 / 6;

        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setImages(Integer[] images) {
        this.images = images;
        this.invalidate();
    }

    public void setStr(String[] strs) {
        this.strs = strs;
        this.invalidate();
    }
}
