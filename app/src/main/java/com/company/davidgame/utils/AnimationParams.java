package com.company.davidgame.utils;

/**
 * Created by IK0214041 on 2014-12-05.
 */
public class AnimationParams {


    private int translationX;
    private int translationY;
    private double scale;
    private float rotation;


    public AnimationParams() {
    }

    public AnimationParams(int translationX, int translationY, double scale, float rotation) {
        this.translationX = translationX;
        this.translationY = translationY;
        this.scale = scale;
        this.rotation = rotation;
    }


    public int getTranslationX() {
        return translationX;
    }

    public void setTranslationX(int translationX) {
        this.translationX = translationX;
    }

    public int getTranslationY() {
        return translationY;
    }

    public void setTranslationY(int translationY) {
        this.translationY = translationY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
