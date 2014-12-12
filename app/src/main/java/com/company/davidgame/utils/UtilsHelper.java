package com.company.davidgame.utils;

import android.graphics.Canvas;
import android.view.View;

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

    public static List<ElemRect> setInactiveRandomElementFromList(List<ElemRect> elemRects) {
        Random random = new Random();
        int freeElem = random.nextInt(elemRects.size()-1);
        elemRects.get(freeElem).setVisibility(View.INVISIBLE);
        return elemRects;
    }

    public static Board initBoard() {
        Board board = new Board();
        board.setTranslationX(0);
        board.setTranslationY(0);
        board.setScale(1.0);
        board.setRotation(0);
        return board;
    }

    public static Board copyBoard(Board board){
        return new Board(board.getTranslationX(),board.getTranslationY(),board.getScale(),board.getRotation());
    }

    public static List<ElemRect> insertRandomPointsValues(List<ElemRect> elemRects) {
        List<Integer> points = new ArrayList<Integer>();
        for(int i = 1; i <= elemRects.size(); i++){
            points.add(i);
        }
        Collections.shuffle(points);
        for(int i = 0; i < elemRects.size(); i++){
            elemRects.get(i).setPoints(points.get(i));
        }
        return elemRects;
    }

    public static Canvas setBoardParametersToCanvas(Canvas canvas, Board board) {
        canvas.scale((float)board.getScale(),(float)board.getScale());
        canvas.translate(board.getTranslationX(), board.getTranslationY());
        canvas.rotate(board.getRotation());
        return canvas;
    }
}
