package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by codetracer on 3/26/17.
 */

public class Homes extends Area {
    public Homes(Position leftTop, Position rightBottom) {
        super(leftTop, rightBottom);
    }

    public void onDraw(Canvas canvas){
        int i = 0;
        Paint p = new Paint();

        p.setColor(this.backgroudColor);
        canvas.drawRect(getRPos(canvas.getWidth(), leftTop.x),
                getRPos(canvas.getHeight(), leftTop.y),
                getRPos(canvas.getWidth(), rightBottom.x),
                getRPos(canvas.getHeight(), rightBottom.y), p);

        p.setColor(this.nameColor);
        p.setTextSize(30);
        canvas.drawText(name,
                getRPos(canvas.getWidth(), leftTop.x),
                getRPos(canvas.getHeight(), leftTop.y),p);

        p.setColor(Color.BLUE);
        canvas.drawRect(getRPos(canvas.getWidth(), leftTop.x),
                getRPos(canvas.getHeight(), (rightBottom.y - leftTop.y)/2+leftTop.y),
                getRPos(canvas.getWidth(), rightBottom.x),
                getRPos(canvas.getHeight(), rightBottom.y), p);

        for (i=0; i < itemList.size(); i++) {
            itemList.get(i).onDraw(canvas);
        }
    }
}
