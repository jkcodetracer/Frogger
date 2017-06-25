package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by codetracer on 3/27/17.
 */

public class TimeBar extends Area {
    int totalTime;
    int currentTime;
    int updatePeriod;
    public TimeBar(Position leftTop, Position rightBottom) {
        super(leftTop, rightBottom);
    }

    public void setTotalTime(int time) {
        this.totalTime = time;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public void setUpdatePeriod(int period) {
        this.updatePeriod = period;
    }

    public void onDraw(Canvas canvas){
        int i = 0;
        float rate = 0.0f;
        float right = 0.0f;
        Paint p = new Paint();

        p.setColor(this.backgroudColor);
        canvas.drawRect(getRPos(canvas.getWidth(), leftTop.x),
                getRPos(canvas.getHeight(), leftTop.y),
                getRPos(canvas.getWidth(), rightBottom.x),
                getRPos(canvas.getHeight(), rightBottom.y), p);

        p.setColor(this.nameColor);
        p.setFakeBoldText(true);
        //p.setTextScaleX(30);
        p.setTextSize(60);
        canvas.drawText(name,
                getRPos(canvas.getWidth(), leftTop.x),
                getRPos(canvas.getHeight(), leftTop.y+8),p);

        rate = ((float)totalTime-currentTime)/totalTime;
        right = ((rightBottom.x-1)-(leftTop.x+20))*rate + (leftTop.x+20);
        p.setColor(Color.GREEN);
        canvas.drawRect(getRPos(canvas.getWidth(), leftTop.x+20),
                getRPos(canvas.getHeight(), leftTop.y+1),
                getRPos(canvas.getWidth(), right),
                getRPos(canvas.getHeight(), rightBottom.y-1), p);

    }

    public void reset(){
        currentTime = 0;
    }

    public void onStep(){
        currentTime += updatePeriod;
    }

    public DeathStatus onDeathCheck(Frog frogger){
        if (currentTime >= totalTime) {
            return DeathStatus.DEAD;
        }

        return DeathStatus.SAFE;
    }
}
