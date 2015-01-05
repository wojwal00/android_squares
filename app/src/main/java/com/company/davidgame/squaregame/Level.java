package com.company.davidgame.squaregame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.TextView;

import com.company.davidgame.ElemRect;
import com.company.davidgame.R;
import com.company.davidgame.squaregame.animations.ElemRemoveAnimation;
import com.company.davidgame.squaregame.animations.ElemState;
import com.company.davidgame.squaregame.animations.IAnimation;
import com.company.davidgame.utils.AnimationParams;
import com.company.davidgame.utils.UtilsHelper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by IK0214041 on 2014-12-12.
 */
public class Level {

    private Context context;
    private int xDimension;
    private int yDimension;
    private Drawable bg;
    private int score = 0;
    private boolean isActive;
    private boolean isFinished;
    private int rotation;
    private int type;
    private ElemRect elemRects[];
    List<ElemRect> touchedElems = new CopyOnWriteArrayList<ElemRect>();
    private ElemRect currentElem;
    private AnimationParams levelAnimationParams;
    private float cameraRotateX;
    private float cameraRotateY;
    private float cameraRotateZ;

    public Level(Context context, int xDimension, int yDimension) {
        this.context = context;
        levelAnimationParams = UtilsHelper.initAnimationParams();
        //bg =  (NinePatchDrawable) context.getResources().getDrawable(R.drawable.bg2);
        bg =  context.getResources().getDrawable(R.drawable.bg2);
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        elemRects = new ElemRect[xDimension * yDimension];
        initElemRect(elemRects);
    }

    private ElemRect[] initElemRect(ElemRect[] elemRects) {

            double xMyRectSize = ((int) MainGamePanel.BOARD_SIZE /xDimension);
            double yMyRectSize = ((int) MainGamePanel.BOARD_SIZE /yDimension);
            int id = 0;
            Drawable elem =  context.getResources().getDrawable(R.drawable.elem);
            for(int i = 0; i<yDimension; i++){
                for(int j = 0;j<xDimension;j++){
                    AnimationParams animationParams1 = UtilsHelper.initAnimationParams();
                    animationParams1.setTranslationX((int)xMyRectSize * j );
                    animationParams1.setTranslationY((int)yMyRectSize * i );

                    Paint paintText = new Paint();
                    paintText.setColor(Color.BLACK);
                    paintText.setTextSize(50);

                    Drawable clone = elem.getConstantState().newDrawable();
                    ElemRect elemRect = new ElemRect(this, id, context, animationParams1, clone, paintText ,xMyRectSize, yMyRectSize);
                    elemRects[id] = elemRect;
                    id++;
                }
            }
            elemRects = UtilsHelper.setInactiveRandomElementFromList(elemRects);
            elemRects = UtilsHelper.insertRandomPointsValues(elemRects);
                return elemRects;
    }

    public void draw(Canvas canvas) {
        if (bg != null && canvas != null) {
            bg.setBounds(0, 0, MainGamePanel.canvasWidth, MainGamePanel.canvasHeight);
            bg.draw(canvas);

            canvas.scale(1/MainGamePanel.normalizeFactorWidth, 1/MainGamePanel.normalizeFactorHeight);
            Camera mCamera = new Camera();
            Matrix mMatrix =  new Matrix();
            mCamera.save();
            mCamera.rotate(0, cameraRotateX/2, 0);
            mCamera.getMatrix(mMatrix);
            mMatrix.preTranslate(-MainGamePanel.canvasWidth / 2, -MainGamePanel.canvasHeight / 2);
            mMatrix.postTranslate(MainGamePanel.canvasWidth / 2, MainGamePanel.canvasHeight / 2);
            canvas.concat(mMatrix);

            for (ElemRect elemRect : elemRects) {
                canvas.save();
                elemRect.draw(canvas);
                canvas.restore();
            }

            mCamera.restore();
        }
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }



    public void actionDown(MotionEvent ev) {

        int pointerMyX = (int)(ev.getX()* MainGamePanel.normalizeFactorWidth);
        int pointerMyY = (int)(ev.getY()* MainGamePanel.normalizeFactorHeight);
        boolean aimed = false;
        for (ElemRect elemRect : elemRects) {
            if(elemRect.isTouched(pointerMyX,pointerMyY)){
                if(elemRect.getState() == ElemState.ACTIVE || elemRect.getState() == ElemState.CLICKED){
                    aimed = true;
                }
            }
        }
        if(aimed){
            for (ElemRect elemRect : elemRects) {
                    if(elemRect.isTouched(pointerMyX,pointerMyY)){
                        elemRect.playActionDown();
                        currentElem = elemRect;
                    }else{
                        if(elemRect.getState() == ElemState.CLICKED){
                            elemRect.setActiveState();
                        }
                    }
            }
        }
//        for (ElemRect elemRect : elemRects) {
//            if((elemRect.getState() == ElemState.ACTIVE || elemRect.getState() == ElemState.CLICKED) && elemRect.isTouched(pointerMyX, pointerMyY)){
//                elemRect.playActionDown();
//                currentElem = elemRect;
//                break;
//            }
//        }
    }

    public void actionMove(MotionEvent ev) {
        int pointerMyX = (int)(ev.getX()* MainGamePanel.normalizeFactorWidth);
        int pointerMyY = (int)(ev.getY()* MainGamePanel.normalizeFactorHeight);
        for (ElemRect elemRect : elemRects) {
            if(elemRect.isTouched(pointerMyX,pointerMyY)){
                touchedElems.add(elemRect);
            }
        }
        for (int i = 1; i < touchedElems.size();i++){
            if(touchedElems.get(i) == touchedElems.get(i-1)){
                touchedElems.remove(i);
            }
        }
        if(currentElem != null){
            currentElem.getAnimationParams().setTranslationX((int)(ev.getX()*(MainGamePanel.normalizeFactorWidth) - (currentElem.getxMySizeElement()/2)));
            currentElem.getAnimationParams().setTranslationY((int)(ev.getY()*(MainGamePanel.normalizeFactorHeight) - (currentElem.getyMySizeElement()/2)));
        }

    }


    public void actionUp(MotionEvent ev) {

        //jesli konczy na tym samym polu co zaczynal
        if(touchedElems.size() > 7 && touchedElems.get(0).isTouched((int)(ev.getX()* MainGamePanel.normalizeFactorWidth),(int)(ev.getY()* MainGamePanel.normalizeFactorHeight))) {
           // touchedElems.add(touchedElems.get(0));
        }
        if(properMove(touchedElems)){
            doMove(touchedElems);
        }
        touchedElems.clear();
        if(currentElem != null){
            currentElem.setAnimationParams(UtilsHelper.copyAnimationParams(currentElem.getPrimaryAnimationParams()));
        }
    }

    private void doMove(List<ElemRect> touchedElems) {
        touchedElems.get(0).setInactiveState();

        for (int i = 1; i < touchedElems.size(); i = i + 2){
            touchedElems.get(i).setAnimationActive(true);
            IAnimation iAnimation = new ElemRemoveAnimation();
            touchedElems.get(i).getActiveAnimations().add(iAnimation);
            score++;
            TextView scoreTextView = (TextView) ((Activity)context).findViewById(R.id.pointsTextView);
            scoreTextView.setText("Wynik: " + score);
            
        }
        touchedElems.get(touchedElems.size()-1).setActiveState();
        touchedElems.get(touchedElems.size()-1).setPoints(touchedElems.get(0).getPoints());
    }

    private boolean properMove(List<ElemRect> touchedElems) {

        if(touchedElems.size()>2 && touchedElems.size()%2==1){
            for(int i = 2; i<touchedElems.size(); i = i+2){
                //spra dla parzystych czyli wolnych
                if(
                        (touchedElems.get(i).getState() == ElemState.INACTIVE || touchedElems.size()-1 == i)&&
                                (touchedElems.get(i-1).getState() == ElemState.ACTIVE || touchedElems.get(i-1).getState() == ElemState.CLICKED) &&
                                ((touchedElems.get(i-2).getPrimaryAnimationParams().getTranslationX() == touchedElems.get(i-1).getPrimaryAnimationParams().getTranslationX() &&
                                        touchedElems.get(i-1).getPrimaryAnimationParams().getTranslationX() == touchedElems.get(i).getPrimaryAnimationParams().getTranslationX())
                                        ||
                                        (touchedElems.get(i-2).getPrimaryAnimationParams().getTranslationY() == touchedElems.get(i-1).getPrimaryAnimationParams().getTranslationY() &&
                                                touchedElems.get(i-1).getPrimaryAnimationParams().getTranslationY() == touchedElems.get(i).getPrimaryAnimationParams().getTranslationY())
                                )
                        ){

                    System.out.println("for true");
                }else{
                    System.out.println("for false");
                    return false;
                }
            }
            System.out.println("koniec petli for");
            return true;
        }
        return false;
    }


    public ElemRect[] getElemRects() {
        return elemRects;
    }

    public AnimationParams getLevelAnimationParams() {
        return levelAnimationParams;
    }

    public void setLevelAnimationParams(AnimationParams levelAnimationParams) {
        this.levelAnimationParams = levelAnimationParams;
    }

    public float getCameraRotateX() {
        return cameraRotateX;
    }

    public void setCameraRotateX(float cameraRotateX) {
        this.cameraRotateX = cameraRotateX;
    }

    public float getCameraRotateY() {
        return cameraRotateY;
    }

    public void setCameraRotateY(float cameraRotateY) {
        this.cameraRotateY = cameraRotateY;
    }

    public void setCameraRotateZ(float cameraRotateZ) {
        this.cameraRotateZ = cameraRotateZ;
    }
}
