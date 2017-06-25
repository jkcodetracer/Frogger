package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by codetracer on 3/26/17.
 */

public class Frog extends Item {
    float originalX = 0.0f;
    float originalY = 0.0f;
    int totalLife = 0;
    public Frog(float x, float y) {
        super(x, y);

        originalX = x;
        originalY = y;
    }

    public void setLife(int lifes){
        this.totalLife = lifes;
    }

    public void onDraw(Canvas canvas){
        int i = 0;
        float tx, ty;
        Paint p = new Paint();

        p.setColor(Color.BLACK);
        canvas.drawRect(getRPos(canvas.getWidth(), x),
                getRPos(canvas.getHeight(), y),
                getRPos(canvas.getWidth(), x+length),
                getRPos(canvas.getHeight(), y+width), p);
        //draw rest frogs
        tx = originalX;
        ty = originalY+10;
        for (i = 0; i < totalLife-1; i++) {
            canvas.drawRect(getRPos(canvas.getWidth(), tx),
                    getRPos(canvas.getHeight(), ty),
                    getRPos(canvas.getWidth(), tx+length),
                    getRPos(canvas.getHeight(), ty+width), p);
            tx += length + 2;
        }
    }

    protected float fixBound(float value, float lower, float upper) {
        if (value < lower) {
            return lower;
        }
        if (value > upper) {
            return upper;
        }

        return value;
    }

    public void reset(){
        x = originalX;
        y = originalY;
    }

    public void onStep() {
        int tmp = (int)x;

        if (speed < 0) {
            if (tmp+speed < 0) {
                x = 0;
            } else {
                x = tmp+speed;
            }
        } else {
            if (tmp+speed+length >= 100) {
                x = 100-length;
            } else {
                x = tmp + speed;
            }
        }
        speed = 0;
    }

    public void onMove(Position direction) {
        speed = 0;
        x += direction.x;
        x = fixBound(x, 0, 100-length);

        y += direction.y;
        y = fixBound(y, 0, originalY);

        Log.d("pos", String.valueOf(x)+", "+String.valueOf(y));
    }
}
