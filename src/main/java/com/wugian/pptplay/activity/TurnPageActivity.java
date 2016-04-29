package com.wugian.pptplay.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.wugian.pptplay.effects.BlackSquareFadeAway;
import com.wugian.pptplay.effects.BlackSquareZoomIn;
import com.wugian.pptplay.effects.ObliqueFringe;
import com.wugian.pptplay.effects.ShutterDown2Up;
import com.wugian.pptplay.effects.ShutterLeft2Right;
import com.wugian.pptplay.effects.ShutterUp2Down;
import com.wugian.pptplay.effects.TranslateLeft;
import com.wugian.pptplay.effects.TranslateRight;
import com.wugian.pptplay.util.BitmapUtil;
import com.wugian.pptplay.widget.IFillingEvent;
import com.wugian.pptplay.widget.TurnPageView;
import com.wugian.rxstudy.R;


public class TurnPageActivity extends Activity {

    int curBitmapIndex = 0;
    private TurnPageView mTurnPageView = null;
    private Bitmap[] mBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mTurnPageView = new TurnPageView(getApplicationContext());
        int length = 2;
        mBitmaps = new Bitmap[length];
        for (int i = 0; i < length; i++) {
            mBitmaps[i] = BitmapUtil.getFitBitmapFromResource(getResources(), R.drawable.img_1 + i,
                    SysUtil.getScreenWidth(getApplicationContext()), SysUtil.getScreenHeight(getApplicationContext()) - SysUtil.getStatusBarHeight(getApplicationContext()));
        }
        setContentView(mTurnPageView);
    }

    private int changeIndex = 1;
    private Handler handler = new Handler();
    private int currentChangeIndexCount = 0;

    Runnable change = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(this);
            switch (changeIndex) {
                case 1:
                    curBitmapIndex = (curBitmapIndex + 1) % 2;
                    turnPage1();
                    break;
                case 2:
                    curBitmapIndex = (curBitmapIndex + 1) % 2;
                    turnPage2();
                    break;
                case 3:
                    curBitmapIndex = (curBitmapIndex + 1) % 2;
                    turnPage3();
                    break;
                case 4:
                    turnPage4();
                    curBitmapIndex = (curBitmapIndex + 1) % 2;
                    break;
            }
            handler.postDelayed(this, 10 * 1000);
//            currentChangeIndexCount++;
//            if (currentChangeIndexCount > 2) {
//                changeIndex = changeIndex % 3 + 1;
//                currentChangeIndexCount = 0;
//            }
        }
    };

    @Override
    protected void onResume() {
        int index = getIntent().getIntExtra("index", 1);
        switch (index) {
            case 1:
                turnPage1();
                break;
            case 2:
                turnPage2();
                break;
            case 3:
                turnPage3();
                break;
            case 4:
                turnPage4();
                break;
        }
        handler.postDelayed(change, 10 * 1000);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        for (int i = 0; i < mBitmaps.length; i++) {
            if (null != mBitmaps[i] && !mBitmaps[i].isRecycled()) {
                mBitmaps[i].recycle();
                mBitmaps[i] = null;
            }
        }
        handler.removeCallbacksAndMessages(null);
        System.gc();
        super.onDestroy();
    }

    private void prePage() {
        if (curBitmapIndex > 0) {
            curBitmapIndex--;
        } else {
            curBitmapIndex = mBitmaps.length - 1;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex]});
    }

    private void nextPage() {
        if (curBitmapIndex < mBitmaps.length - 1) {
            curBitmapIndex++;
        } else {
            curBitmapIndex = 0;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex]});
    }

    private void turnPage1() {
        IFillingEvent mFillingListenerBYC = new IFillingEvent() {

            @Override
            public void onFlingLeft() {
                nextPage();
                mTurnPageView.setTurnPageStyle(new ObliqueFringe());
            }

            @Override
            public void onFlingRight() {
                prePage();
                mTurnPageView.setTurnPageStyle(new ShutterLeft2Right());
            }

            @Override
            public void onFlingUp() {
                nextPage();
                mTurnPageView.setTurnPageStyle(new ShutterDown2Up());
            }

            @Override
            public void onFlingDown() {
                prePage();
                mTurnPageView.setTurnPageStyle(new ShutterUp2Down());
            }

        };

        mTurnPageView.setOnFillingListener(mFillingListenerBYC);
        mTurnPageView.setTurnPageStyle(new ObliqueFringe());
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex]});
    }

    private void turnPage2() {
        IFillingEvent mFillingListener = new IFillingEvent() {

            @Override
            public void onFlingLeft() {
                nextPage();
                mTurnPageView.setTurnPageStyle(new BlackSquareZoomIn());
            }

            @Override
            public void onFlingRight() {
                prePage();
                mTurnPageView.setTurnPageStyle(new BlackSquareFadeAway());
            }

            @Override
            public void onFlingUp() {
                nextPage();
                mTurnPageView.setTurnPageStyle(new BlackSquareZoomIn());
            }

            @Override
            public void onFlingDown() {
                prePage();
                mTurnPageView.setTurnPageStyle(new BlackSquareFadeAway());
            }
        };

        mTurnPageView.setOnFillingListener(mFillingListener);
        mTurnPageView.setTurnPageStyle(new BlackSquareZoomIn());
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex]});
    }

    private void turnPage3() {
        IFillingEvent mFillingListener = new IFillingEvent() {

            @Override
            public void onFlingLeft() {
                nextPage3();
                mTurnPageView.setTurnPageStyle(new TranslateLeft());
            }

            @Override
            public void onFlingRight() {
                prePage3();
                mTurnPageView.setTurnPageStyle(new TranslateRight());
            }

            @Override
            public void onFlingUp() {
                nextPage3();
                mTurnPageView.setTurnPageStyle(new TranslateRight());
            }

            @Override
            public void onFlingDown() {
                prePage3();
                mTurnPageView.setTurnPageStyle(new TranslateLeft());
            }

        };

        mTurnPageView.setOnFillingListener(mFillingListener);
        mTurnPageView.setTurnPageStyle(new TranslateLeft());
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex], mBitmaps[(curBitmapIndex + 1) % 2]});
    }

    private void prePage3() {
        if (curBitmapIndex > 0) {
            curBitmapIndex--;
        } else {
            curBitmapIndex = 0;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[curBitmapIndex], mBitmaps[(curBitmapIndex + 1) % 2]});
    }

    private void nextPage3() {
        if (curBitmapIndex < mBitmaps.length - 1) {
            curBitmapIndex++;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[(curBitmapIndex + 1) % 2], mBitmaps[curBitmapIndex]});
    }

    private void turnPage4() {
        IFillingEvent mFillingListener = new IFillingEvent() {

            @Override
            public void onFlingLeft() {
                nextPage4();
                mTurnPageView.setTurnPageStyle(new ObliqueFringe());
            }

            @Override
            public void onFlingRight() {
                prePage4();
                mTurnPageView.setTurnPageStyle(new ObliqueFringe());
            }

            @Override
            public void onFlingUp() {
                nextPage4();
                mTurnPageView.setTurnPageStyle(new ObliqueFringe());
            }

            @Override
            public void onFlingDown() {
                prePage4();
                mTurnPageView.setTurnPageStyle(new ObliqueFringe());
            }

        };

        mTurnPageView.setOnFillingListener(mFillingListener);
        mTurnPageView.setTurnPageStyle(new ObliqueFringe());
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[0], mBitmaps[1]});
    }

    private void prePage4() {
        if (curBitmapIndex > 0) {
            curBitmapIndex--;
        } else {
            curBitmapIndex = 0;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[0], mBitmaps[1]});
    }

    private void nextPage4() {
        if (curBitmapIndex < mBitmaps.length - 1) {
            curBitmapIndex++;
        }
        mTurnPageView.setBitmaps(new Bitmap[]{mBitmaps[0], mBitmaps[1]});
    }

}
