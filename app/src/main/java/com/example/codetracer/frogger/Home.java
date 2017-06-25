package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by codetracer on 3/26/17.
 */

public class Home extends Item {
    boolean hasFrog = false;
    public Home(float x, float y) {
        super(x, y);
    }

    public void onDraw(Canvas canvas){
        Paint p = new Paint();

        if (hasFrog) {
            p.setColor(Color.RED);
        } else {
            p.setColor(Color.GREEN);
        }

        canvas.drawRect(getRPos(canvas.getWidth(), x),
                getRPos(canvas.getHeight(), y),
                getRPos(canvas.getWidth(), x+length),
                getRPos(canvas.getHeight(), y+width), p);
    }

    public DeathStatus onDeathCheck(Frog frogger) {

        if(overlapCheck(this.getLeftBound(), this.getRightBound(),
                frogger.getLeftBound(),frogger.getRightBound())) {
            if (hasFrog) {
                return DeathStatus.DEAD;
            } else {
                hasFrog = true;
                return DeathStatus.WIN;
            }
        }

        return DeathStatus.UNKNOWN;
    }
}
