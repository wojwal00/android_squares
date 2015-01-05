package com.company.davidgame.squaregame;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.company.davidgame.ElemRect;
import com.company.davidgame.squaregame.animations.IAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IK0214041 on 2014-12-12.
 */
public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback, SensorEventListener {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    public static float BOARD_SIZE = 1000;
    private Context context;
    private MainThread thread;
    private List<Level> levels = new ArrayList<Level>();
    private Level currentLevel;
    public static int canvasWidth;
    public static int canvasHeight;
    public static float normalizeFactorWidth;
    public static float normalizeFactorHeight;

    private SensorManager mSensorManager;
    private Sensor mSensor;


    public MainGamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getHolder().addCallback(this);

      //  android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(500, 600);
      //  setLayoutParams(params);
       // getHolder().setFixedSize();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL); // rejestracja sensora


        levels = loadLevelsFromDB();
        currentLevel = getCurrentLevel();
        if(currentLevel != null){
            // create the game loop thread
            thread = new MainThread(getHolder(), this);
        }

        // make the GamePanel focusable so it can handle events
        setFocusable(true);

    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("surfaceCreated","..."+thread.getState().toString());
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

        /**
         * This is the game update method. It iterates through all the objects
         * and calls their update method if they have one or calls specific
         * engine's update method.
         */
    public void update() {

        for (ElemRect elemRect : currentLevel.getElemRects()) {
            List<IAnimation> animationToRemove = new CopyOnWriteArrayList<IAnimation>();
            for(IAnimation animaton : elemRect.getActiveAnimations()){

                if(animaton.getAdvancement() > 0){
                    animaton.animate(elemRect);
                }else{
                    animationToRemove.add(animaton);
                }
            }
            for (IAnimation animation : animationToRemove) {
                elemRect.getActiveAnimations().remove(animation);
            }
        }
    }

    public Level getCurrentLevel() {
        for (Level level : levels) {
            if(!level.isFinished()){
                return level;
            }
        }
        return null;
    }

    public void render(Canvas canvas) {
            //this.canvas = canvas;
            currentLevel.draw(canvas);
    }

    public List<Level> loadLevelsFromDB(){
        List <Level> levels = new ArrayList<Level>();
        //TODO...
        levels.add(new Level(context,5, 7));

        return levels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
              currentLevel.actionDown(ev);
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
           currentLevel.actionMove(ev);
        }else if(ev.getAction() == MotionEvent.ACTION_UP){
            currentLevel.actionUp(ev);
        }
        return true;
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
    public void onSensorChanged(SensorEvent event) {
        currentLevel.setCameraRotateX(event.values[0]);

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
