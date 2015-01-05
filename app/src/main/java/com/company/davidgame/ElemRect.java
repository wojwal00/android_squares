package com.company.davidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.company.davidgame.squaregame.Level;
import com.company.davidgame.squaregame.MainGamePanel;
import com.company.davidgame.squaregame.animations.ElemState;
import com.company.davidgame.squaregame.animations.IAnimation;
import com.company.davidgame.utils.AnimationParams;
import com.company.davidgame.utils.UtilsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IK0214041 on 2014-12-03 .
 */
public class ElemRect extends View {

    private int id;
    private AnimationParams animationParams;
    private AnimationParams primaryAnimationParams;
    private Rect rect;
    private Paint paintText;
    private double xMySizeElement;
    private double yMySizeElement;
    private int points = 3;
    private Level parent;
    private List<IAnimation> animations = new ArrayList<IAnimation>();
    boolean animationActive;
    private ElemState state;
    private Context context;

    private Drawable elem;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ElemRect(Level parent, int id,Context context, AnimationParams animationParams,Drawable drawable, Paint paintText, double xSizeElement, double ySizeElement) {
        super(context);
        this.context = context;
        this.parent = parent;
        this.id = id;
        this.paintText = paintText;
        this.xMySizeElement = xSizeElement;
        this.yMySizeElement = ySizeElement;
        this.animationParams = animationParams;
        this.primaryAnimationParams = UtilsHelper.copyAnimationParams(animationParams);
        rect = initRect();
        this.elem = drawable;
        setActiveState();
    }


    private Rect initRect() {
        Rect rectShape = new Rect();
        rectShape.set(0,0,(int) xMySizeElement -2,(int) yMySizeElement -2);
        return rectShape;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public AnimationParams getAnimationParams() {
        return animationParams;
    }

    public void setAnimationParams(AnimationParams animationParams) {
        this.animationParams = animationParams;
    }

    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such
     * as alpha (set via setAlpha) and color filter (set via setColorFilter).
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(Canvas canvas) {
       // if(this.getState() == ElemState.ACTIVE || this.getState() == ElemState.CLICKED){
            canvas = UtilsHelper.setAnimationParametersToCanvas(canvas, animationParams,this);
            this.elem.setBounds(rect);
            this.elem.draw(canvas);
            //canvas.drawRect(rect, paint);

           //display points on Rects
            canvas.translate((float)(xMySizeElement/(2*MainGamePanel.normalizeFactorWidth)),(float)(yMySizeElement/(2*MainGamePanel.normalizeFactorHeight)));
            //if(parent.getType()==2){
               // canvas.drawText(String.valueOf(getPoints()),0,0,paintText);
                //canvas.drawText(String.valueOf(getId()),0,0,paintText);
            //}
       // }
    }

    public boolean isTouched(int pointerMyX, int pointerMyY) {
        if(primaryAnimationParams.getTranslationX()+rect.centerX()-pointerMyX < xMySizeElement /2 &&
                primaryAnimationParams.getTranslationX()+rect.centerX()-pointerMyX > (-xMySizeElement /2) &&
                primaryAnimationParams.getTranslationY()+rect.centerY()-pointerMyY < yMySizeElement /2 &&
                primaryAnimationParams.getTranslationY()+rect.centerY()-pointerMyY > (-yMySizeElement /2)){
            return true;
        }
        return false;
    }

    public void playActionDown() {
        changeState(state);
    }

    public void changeState(ElemState state) {
        if(state == ElemState.ACTIVE){
            setClickedState();
        }else if(state == ElemState.CLICKED){
            setActiveState();
        }
    }

    public void setClickedState(){
        this.state = ElemState.CLICKED;
        elem = context.getResources().getDrawable(R.drawable.elem_klik);
        elem.mutate().setAlpha(255);
    }
    public void setActiveState(){
        this.state = ElemState.ACTIVE;
        elem = context.getResources().getDrawable(R.drawable.elem);
        elem.mutate().setAlpha(255);
    }
    public void setInactiveState(){
        this.state = ElemState.INACTIVE;
        elem = context.getResources().getDrawable(R.drawable.elem);
        elem.mutate().setAlpha(10);
    }

    public List<IAnimation> getActiveAnimations() {
        return animations;
    }

    public boolean isAnimationActive() {
        return animationActive;
    }

    public void setAnimationActive(boolean animationActive) {
        this.animationActive = animationActive;
    }

    public AnimationParams getPrimaryAnimationParams() {
        return primaryAnimationParams;
    }


    public double getxMySizeElement() {
        return xMySizeElement;
    }

    public double getyMySizeElement() {
        return yMySizeElement;
    }

    public Drawable getElem() {
        return elem;
    }

    public void setElem(Drawable elem) {
        this.elem = elem;
    }

    public ElemState getState() {
        return state;
    }

    public void setState(ElemState state) {
        this.state = state;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
