package com.company.davidgame.utils;

/**
 * Created by IK0214041 on 2014-12-05.
 */
public class Board {

    public static float BOARD_SIZE = 1000;
    private int translationX;
    private int translationY;
    private double scale;
    private int rotation;


    public Board() {
    }

    public Board(int translationX, int translationY, double scale, int rotation) {
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

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
