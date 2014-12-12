package com.company.davidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.company.davidgame.utils.Board;
import com.company.davidgame.utils.UtilsHelper;

/**
 * Created by IK0214041 on 2014-12-03 .
 */
public class ElemRect extends View {

    private int id;
    private Board board;
    private Rect rect;
    private Paint paint;
    private Paint paintText;
    private double sizeElement;
    private int points = 3;
    private DrawView parent;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ElemRect(DrawView parent, int id,Context context, Board board, Paint paint,Paint paintText, double sizeElement) {
        super(context);
        this.parent = parent;
        this.id = id;
        this.paint = paint;
        this.paintText = paintText;
        this.sizeElement = sizeElement;
        this.board = board;
        rect = initRect();
    }



    private Rect initRect() {
        Rect rectShape = new Rect();
        rectShape.set(0,0,(int)sizeElement-2,(int)sizeElement-2);
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such
     * as alpha (set via setAlpha) and color filter (set via setColorFilter).
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(Canvas canvas) {
        if(this.getVisibility() == VISIBLE){
            canvas = UtilsHelper.setBoardParametersToCanvas(canvas,board);
            canvas.drawRect(rect, paint);
           //display points on Rects
            canvas.translate((float)(sizeElement * canvas.getWidth()/ (2*1000)-20),(float)(sizeElement * canvas.getWidth()/ (2*1000)));
            if(parent.getGameType()==2){
                canvas.drawText(String.valueOf(points),0,0,paintText);
            }
        }
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void onTouchEvent(MotionEvent ev, int eventX, int eventY) {
        if(board.getTranslationX()+rect.centerX()-eventX < sizeElement/2 &&
                board.getTranslationX()+rect.centerX()-eventX > -sizeElement/2 &&
                board.getTranslationY()+rect.centerY()-eventY < sizeElement/2 &&
                board.getTranslationY()+rect.centerY()-eventY > -sizeElement/2){
            paint.setColor(Color.GREEN);
        }

    }


    public boolean isTouched(int pointerMyX, int pointerMyY) {
        if(board.getTranslationX()+rect.centerX()-pointerMyX < sizeElement/2 &&
                board.getTranslationX()+rect.centerX()-pointerMyX > -sizeElement/2 &&
                board.getTranslationY()+rect.centerY()-pointerMyY < sizeElement/2 &&
                board.getTranslationY()+rect.centerY()-pointerMyY > (-sizeElement/2)){
            return true;
        }
        return false;
    }

    public void playActionDown() {
        paint.setColor(Color.CYAN);
    }
}
