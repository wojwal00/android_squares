package com.company.davidgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.company.davidgame.utils.Board;
import com.company.davidgame.utils.UtilsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IK0214041 on 2014-11-30.
 */

public class DrawView extends ElemView implements SensorEventListener {

    Board board;

    int dimension = 5;
    List<ElemRect> elemRects = new ArrayList<ElemRect>();
    int x;
    int y;
    int elementSize;
    int scale;
    List<ElemRect> touchedElems = new ArrayList<ElemRect>();
    private int score = 0;
    private MainActivity activity;
    private int gameType;
    private Canvas canvas;


    public DrawView(Context context) {
        super(context);
    }
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        board = UtilsHelper.initBoard();
        activity = (MainActivity)context;
        gameType = activity.getGameType();
        init();
        SensorManager mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        Sensor mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        if(gameType == 1){
            TextView taskTextView = (TextView) activity.findViewById(R.id.taskTextView);
            taskTextView.setText("Pozostaw możliwie najmniej elementów");
        }

        for (ElemRect elemRect : elemRects) {
            canvas.save();
            canvas.scale(canvas.getWidth() / Board.BOARD_SIZE, canvas.getWidth() / Board.BOARD_SIZE);
            elemRect.draw(canvas);
            canvas.restore();
        }
        TextView scoreTextView = (TextView) activity.findViewById(R.id.pointsTextView);
        scoreTextView.setText("Wynik: "+ score);

    invalidate();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        x = y = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        this.setMeasuredDimension(x, y);

    }


    private void crossfade() {
View mContentView = elemRects.get(2);
       final View mLoadingView = elemRects.get(1);
        int mShortAnimationDuration = 2000;
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mContentView.animate()
                .alpha(1f)
                .rotation(150)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mLoadingView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       /* Animation animation = new RotateAnimation(0,60);
        animation.setDuration(1000);
        elemRects.get(0).startAnimation(animation);
*/
  /*      // StartAction
        elemRects.get(0).animate().translationX(100).withStartAction(new Runnable(){
            public void run(){
                elemRects.get(0).setTranslationX(100);
                // do something
            }
        });
*/
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            int pointerMyX = (int)(ev.getX()*Board.BOARD_SIZE/getWidth());
            int pointerMyY = (int)(ev.getY()*Board.BOARD_SIZE/getWidth());
            for (ElemRect elemRect : elemRects) {
                if((elemRect.getVisibility() == View.VISIBLE) && elemRect.isTouched(pointerMyX, pointerMyY)){canvas.save();
                    elemRect.playActionDown();
                    break;
                }
            }
            invalidate();


        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            final int historySize = ev.getHistorySize();
            for (int h = 0; h < historySize; h++) {
                System.out.printf("At time %d:", ev.getHistoricalEventTime(h));
                int p = 0;
                    //checking if touch over elemRect
                    for (ElemRect elemRect : elemRects) {
                        int pointerMyX = (int)(ev.getHistoricalX(p, h)*Board.BOARD_SIZE/getWidth());
                        int pointerMyY = (int)(ev.getHistoricalY(p, h)*Board.BOARD_SIZE/getWidth());
                        if(elemRect.isTouched(pointerMyX,pointerMyY)){
                            boolean isOnList = false;
                            for (ElemRect touched : touchedElems) {
                                if(touched == elemRect){
                                    isOnList = true;
                                }
                            }
                            if(!isOnList ){
                                touchedElems.add(elemRect);
                            }
                        }
                    }

                    System.out.printf("  pointer %d: (%f,%f)",
                            ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));

            }

        }else if(ev.getAction() == MotionEvent.ACTION_UP){
            //jesli konczy na tym samym polu co zaczynal
            if(touchedElems.size() > 7 && touchedElems.get(0).isTouched((int)(ev.getX()*Board.BOARD_SIZE/getWidth()),(int)(ev.getY()*Board.BOARD_SIZE/getWidth()))) {
                touchedElems.add(touchedElems.get(0));
            }

            if(properMove(touchedElems)){
                doMove(touchedElems);
            }
            touchedElems.clear();
            invalidate();
        }
        return true;
        }

    private void doMove(List<ElemRect> touchedElems) {
        touchedElems.get(0).setVisibility(INVISIBLE);
        for (int i = 1; i<touchedElems.size();i = i+2){
            touchedElems.get(i).setVisibility(View.INVISIBLE);
            if(gameType == 1){
                score  = score + 1;
            }else if(gameType == 2){
                score += touchedElems.get(i).getPoints();
            }
        }
        touchedElems.get(touchedElems.size()-1).setVisibility(View.VISIBLE);
        touchedElems.get(touchedElems.size()-1).getPaint().setColor(Color.WHITE);
        touchedElems.get(touchedElems.size()-1).setPoints(touchedElems.get(0).getPoints());
    }

    private boolean properMove(List<ElemRect> touchedElems) {
       boolean isProper = true;
        if(touchedElems.size()>2 && touchedElems.size()%2==1){
            for(int i = 2; i<touchedElems.size(); i = i+2){
                //spra dla parzystych czyli wolnych
                if(
                        touchedElems.get(i).getVisibility() == View.INVISIBLE &&
                                touchedElems.get(i-1).getVisibility() == View.VISIBLE &&
                                ((touchedElems.get(i-2).getBoard().getTranslationX() == touchedElems.get(i-1).getBoard().getTranslationX() &&
                                        touchedElems.get(i-1).getBoard().getTranslationX() == touchedElems.get(i).getBoard().getTranslationX())
                                        ||
                                        (touchedElems.get(i-2).getBoard().getTranslationY() == touchedElems.get(i-1).getBoard().getTranslationY() &&
                                                touchedElems.get(i-1).getBoard().getTranslationY() == touchedElems.get(i).getBoard().getTranslationY())
                                )
                        )
                {}else{
                    return false;
                }
            }
            return true;
        }
      return false;
    }


    public void init() {

        double rectSize = ((int)Board.BOARD_SIZE /dimension);
        int id = 0;
        for(int i = 0; i<dimension; i++){
            for(int j = 0;j<dimension;j++){
                Board board1 = UtilsHelper.copyBoard(board);
                board1.setTranslationX((int)rectSize * j );
                board1.setTranslationY((int)rectSize * i );

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);

                Paint paintText = new Paint();
                paintText.setColor(Color.BLACK);
                paintText.setTextSize(50);


                ElemRect elemRect = new ElemRect(this,id, getContext(),board1,paint,paintText,rectSize);
                elemRects.add(elemRect);

                id++;
            }
        }
        elemRects = UtilsHelper.setInactiveRandomElementFromList(elemRects);
        elemRects = UtilsHelper.insertRandomPointsValues(elemRects);
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    /**
     * Called when sensor values have changed.
     * <p>See {@link android.hardware.SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link android.hardware.SensorEvent SensorEvent}.
     * <p/>
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link android.hardware.SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link android.hardware.SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event ) {
        synchronized (this) {
            System.out.println("SENSOR: " + event.values.toString());
           /* canvas.rotate(10);
            onDraw(canvas);
            */
        }

    }

    /**
     * Called when the accuracy of the registered sensor has changed.
     * <p/>
     * <p>See the SENSOR_STATUS_* constants in
     * {@link android.hardware.SensorManager SensorManager} for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor, one of
     *                 {@code SensorManager.SENSOR_STATUS_*}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

