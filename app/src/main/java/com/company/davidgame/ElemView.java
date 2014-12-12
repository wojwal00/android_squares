package com.company.davidgame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.company.davidgame.utils.Board;

import java.util.List;

/**
 * Created by IK0214041 on 2014-12-05.
 */
public class ElemView extends View{

    private Board board;
    private List<ElemView> children;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ElemView(Context context) {
        super(context);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(android.content.Context, android.util.AttributeSet, int)
     */
    public ElemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<ElemView> getChildren() {
        return children;
    }

    public void setChildren(List<ElemView> children) {
        this.children = children;
    }
}
