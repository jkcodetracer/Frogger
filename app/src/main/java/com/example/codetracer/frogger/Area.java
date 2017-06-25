package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by codetracer on 3/26/17.
 */

public class Area {
    Position leftTop;
    Position rightBottom;
    String name;
    ArrayList<Item> itemList;
    int nameColor;
    int backgroudColor;

    public Area(Position leftTop, Position rightBottom){
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        this.itemList = new ArrayList<Item>();
    }

    public int getTop(){
        return (int)leftTop.y;
    }

    public int getBottom(){
        return (int)rightBottom.y;
    }

    public int getWidth(){
        return (int)(rightBottom.x - leftTop.x);
    }

    public int getHeight(){
        return (int)(rightBottom.y - leftTop.y);
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getName(){
        return this.name;
    }

    public void setBackgroudColor(int color){
        this.backgroudColor = color;
    }

    public int getBackgroudColor(){
        return this.backgroudColor;
    }

    public void setNameColor(int color){
        this.nameColor = color;
    }

    public int getNameColor() {
        return this.nameColor;
    }

    public float getRPos(float obsPos, float rate){
        return (rate/100)*obsPos;
    }

    public void addItem(Item newItem){
        this.itemList.add(newItem);
    }

    public void removeItem(Item item){
        this.itemList.remove(item);
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

        for (i=0; i < itemList.size(); i++) {
            itemList.get(i).onDraw(canvas);
        }
    }

    public void onStep(){
        int i = 0;
        for (i = 0; i < itemList.size(); i++) {
            itemList.get(i).onStep();
        }
    }

    public DeathStatus onDeathCheck(Frog frogger){
        int i = 0;
        DeathStatus result = DeathStatus.UNKNOWN;
        for (i = 0;i < itemList.size(); i++) {
            result = itemList.get(i).onDeathCheck(frogger);
            if (result == DeathStatus.DEAD || result == DeathStatus.WIN)
                return result;
        }

        return DeathStatus.SAFE;
    }

    public boolean isInArea(float y) {
        if (y >= leftTop.y && y < rightBottom.y) {
            Log.d("in area", name);
            return true;
        } else {
            return false;
        }
    }
}
