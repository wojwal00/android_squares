package com.company.davidgame.utils;

import android.graphics.Canvas;

import com.company.davidgame.ElemRect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by IK0214041 on 2014-12-03.
 */
public class UtilsHelper {

    static public List<ElemRect> removeRandomElementFromList(List<ElemRect> elemRects){
        Random random = new Random();
        int freeElem = random.nextInt(elemRects.size()-1);
        elemRects.remove(freeElem);
        return elemRects;
    }

    public static ElemRect[] setInactiveRandomElementFromList(ElemRect[] elemRects) {
        Random random = new Random();
        int freeElem = random.nextInt(elemRects.length-1);
        elemRects[freeElem].setInactiveState();
        return elemRects;
    }

    public static AnimationParams initAnimationParams() {
        AnimationParams animationParams = new AnimationParams();
        animationParams.setTranslationX(0);
        animationParams.setTranslationY(0);
        animationParams.setScale(1.0);
        animationParams.setRotation(0);
        return animationParams;
    }

    public static AnimationParams copyAnimationParams(AnimationParams animationParams){
        return new AnimationParams(animationParams.getTranslationX(), animationParams.getTranslationY(), animationParams.getScale(), animationParams.getRotation());
    }

    public static ElemRect[] insertRandomPointsValues(ElemRect[] elemRects) {
        List<Integer> points = new ArrayList<Integer>();
        for(int i = 1; i <= elemRects.length; i++){
            points.add(i);
        }
        Collections.shuffle(points);
        for(int i = 0; i < elemRects.length; i++){
            elemRects[i].setPoints(points.get(i));
        }
        return elemRects;
    }

    public static Canvas setAnimationParametersToCanvas(Canvas canvas, AnimationParams animationParams, ElemRect elemRect) {
        canvas.scale((float) animationParams.getScale(),(float) animationParams.getScale());
        canvas.translate(animationParams.getTranslationX(), animationParams.getTranslationY());
        canvas.rotate(animationParams.getRotation(),elemRect.getRect().centerX(),elemRect.getRect().centerY());
        return canvas;
    }
}
