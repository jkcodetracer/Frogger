package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by codetracer on 3/26/17.
 */

public class WoodLog extends Item {
    public WoodLog(float x, float y) {
        super(x, y);
    }

    public void onDraw(Canvas canvas){
        Paint p = new Paint();

        p.setColor(Color.GRAY);
        canvas.drawRect(getRPos(canvas.getWidth(), x),
                getRPos(canvas.getHeight(), y),
                getRPos(canvas.getWidth(), x+length),
                getRPos(canvas.getHeight(), y+width), p);
    }

    public DeathStatus onDeathCheck(Frog frogger){
        Log.v("set speed.", String.valueOf(this.speed));
        if (overlapCheck(this.getLeftBound(),this.getRightBound(),
                         frogger.getLeftBound(), frogger.getRightBound())) {
            Log.v("set speed.", String.valueOf(this.speed));
            frogger.setSpeed(this.speed);

            return DeathStatus.SAFE;
        }

        return DeathStatus.UNKNOWN;
    }
}
