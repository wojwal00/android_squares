package com.company.davidgame.squaregame.animations;

import com.company.davidgame.ElemRect;
import com.company.davidgame.utils.UtilsHelper;

/**
 * Created by IK0214041 on 2014-12-19.
 */
public class ElemRemoveAnimation implements IAnimation {


    private int advancement = 20;

    @Override
    public void animate(ElemRect elemRect) {
        elemRect.getAnimationParams().setScale(elemRect.getAnimationParams().getScale() * 0.9);
        elemRect.getAnimationParams().setRotation(elemRect.getAnimationParams().getRotation() + 20);
        advancement -= 1;
        if(advancement < 1){
            elemRect.setInactiveState();
            elemRect.setAnimationParams(UtilsHelper.copyAnimationParams(elemRect.getPrimaryAnimationParams()));
            //todo elemRect.setAlpha(1);
        }

    }

    @Override
    public int getAdvancement() {
        return advancement;
    }
}
