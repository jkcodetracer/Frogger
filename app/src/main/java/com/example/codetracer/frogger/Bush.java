package com.example.codetracer.frogger;

import android.graphics.Canvas;

/**
 * Created by codetracer on 3/26/17.
 */

public class Bush extends Item {

    public Bush(float x, float y) {
        super(x, y);
    }

    public void onDraw(Canvas canvas){

    }

    public DeathStatus onDeathCheck(Frog frogger){

        return DeathStatus.UNKNOWN;
    }
}
