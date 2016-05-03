package com.wugian.pptplay.effects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * @author yanglonghui
 */
public class ObliqueFringe implements ITurnPage {

    private final int leafNum = 5;
    private int currentOffset = 0;
    private int duration = 2500;//动画持续时间
    private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private boolean isRunning;

    public ObliqueFringe() {
    }

    @Override
    public void onCreate() {
    }

    /**
     * @param holder    sh
     * @param bitmap    bitmap[0] new bitmap,bitmap[1] old bitmap
     * @param maxWidth  screen width
     * @param maxHeight screen height
     */
    @Override
    public void onTurnPageDraw(SurfaceHolder holder, Bitmap[] bitmap, int maxWidth, int maxHeight) {
        boolean hasRun = bitmap.length < 2;
        int dx = (maxWidth - bitmap[0].getWidth()) / 2;
        int dy = (maxHeight - bitmap[0].getHeight()) / 2;

        int width = bitmap[0].getWidth();
        int height = bitmap[0].getHeight();
        long start = System.currentTimeMillis();

        long runMills = 0;
        double cu = Math.sqrt(2.0 * (width * width + height * height));
        Canvas canvas = null;

        currentOffset++;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);

        Rect src = new Rect();
        Rect dst = new Rect();
        isRunning = true;
//        hasRun = true;
        while (isRunning) {
            long l = System.currentTimeMillis() - start;
            isRunning = ((runMills = l) < duration);
            if (!isRunning) {
                runMills = duration;
            }

            try {
                canvas = holder.lockCanvas(null);
                //for if the animation is running and back press may null
                if (canvas == null) {
                    return;
                }
                /**
                 * this is hide
                 */
                if (!hasRun) {
                    canvas.setDrawFilter(pdf);
                    canvas.drawColor(Color.BLACK);// 清除画布
                    canvas.save();
                    canvas.translate(dx, dy);
                    src.set(0, 0, width, height);
                    dst.set(0, 0, width, height);
                    if (isRunning) {
                        canvas.drawBitmap(bitmap[1], src, dst, null);
                        long width1 = l / 100;
                        for (int i = 0; i < cu + 1; i += 25) {
                            for (int i1 = 0; i1 < 25; i1++) {
                                canvas.drawLine(0f, (float) i + /*(l / 100) +*/ i1, (float) i + /*(l / 100) +*/ i1, 0f, paint);
                            }
                        }
                    } else {
                        hasRun = true;
                        isRunning = true;
                        start = System.currentTimeMillis();
                    }
                    canvas.restore();
                } else {
                    /**
                     * this is show
                     */
                    canvas.setDrawFilter(pdf);
                    canvas.drawColor(Color.BLACK);// 清除画布
                    canvas.save();
                    canvas.translate(dx, dy);
                    src.set(0, 0, width, height);
                    dst.set(0, 0, width, height);
                    canvas.drawBitmap(bitmap[0], src, dst, null);

                    if (isRunning) {
                        long width1 = 25 - l / 100;
//                        if (width1 > 2) {
                        for (int i = 0; i < cu; i += 25) {
                            for (int i1 = 0; i1 < 25; i1++) {
                                canvas.drawLine(0f, (float) i + /*(l / 100) + */i1, (float) i + /*(l / 100) + */i1, 0f, paint);
                            }
                        }
//                        }
                    }
                    canvas.restore();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas) {
                    holder.unlockCanvasAndPost(canvas);
                } else {
                    break;
                }

                if (!isRunning) {
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        isRunning = false;
    }

}
