package com.example.codetracer.frogger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import android.util.Log;


/**
 * Created by codetracer on 3/26/17.
 */

public class Scenario extends View implements View.OnTouchListener, Runnable{
    ArrayList<Area> areas;
    TimeBar timeBar;
    Handler timer;
    Frog frogger;
    int stepLength = 5;
    int updatePeriod = 100;
    int score = 0;
    int drawCounter;
    int drawSpeed;

    public Scenario(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitAllArea();
        onInitPlayer();

        timer = new Handler();
        timer.postDelayed(this, updatePeriod);

        this.setOnTouchListener(this);
    }

    private void onInitAllArea() {
        Item newItem;
        Area homes;
        Area river;
        Area road;
        Area safeZoneOne;
        Area safeZoneTwo;
        int i;

        score = 0;
        drawCounter = 0;
        drawSpeed = 10;

        areas = new ArrayList<Area>();
        homes = new Homes(new Position(0, 0), new Position(100,10));
        river = new River(new Position(0, 10), new Position(100, 35));
        safeZoneOne = new Area(new Position(0, 35), new Position(100, 40));
        road = new Road(new Position(0, 40), new Position(100, 65));
        safeZoneTwo = new Area(new Position(0, 65), new Position(100, 70));
        timeBar = new TimeBar(new Position(0,90), new Position(100,100));
        timeBar.setName("TIME");
        timeBar.setBackgroudColor(Color.BLACK);
        timeBar.setNameColor(Color.YELLOW);
        timeBar.setTotalTime(60000);
        timeBar.setUpdatePeriod(updatePeriod);

        homes.setName("Homes");
        homes.setBackgroudColor(Color.GREEN);
        homes.setNameColor(Color.BLACK);
        for (i = 0; i < homes.getWidth()/10; i+=2){
            newItem = new Home((i+1)*10, homes.getHeight()/2);
            newItem.setLength(10);
            newItem.setWidth(10);
            homes.addItem(newItem);
        }

        river.setName("River");
        river.setBackgroudColor(Color.BLUE);
        river.setNameColor(Color.BLACK);
        for (i=0; i < river.getHeight()/stepLength; i++) {
            if (i%2 == 0) {
                newItem = new WoodLog(0, river.getTop() + i*stepLength);
                newItem.setSpeed((-1)*2);
                newItem.setLength(35);
            } else {
                newItem = new Turtle(0, river.getTop() + i*stepLength);
                newItem.setSpeed(2);
                newItem.setLength(10);
            }
            river.addItem(newItem);
        }

        safeZoneOne.setName("SafeZone1");
        safeZoneOne.setBackgroudColor(Color.YELLOW);
        safeZoneOne.setNameColor(Color.BLACK);

        road.setName("Road");
        road.setBackgroudColor(Color.GRAY);
        road.setNameColor(Color.BLACK);
        for (i = 0; i < road.getHeight()/stepLength; i++) {
            newItem = new Car(0, road.getTop()+i*stepLength);
            if (i%2 == 0) {
                newItem.setSpeed((-1)*(i+1));
            } else {
                newItem.setSpeed((i+1));
            }

            newItem.setLength(10);

            road.addItem(newItem);
        }

        safeZoneTwo.setName("SafeZone2");
        safeZoneTwo.setBackgroudColor(Color.YELLOW);
        safeZoneTwo.setNameColor(Color.BLACK);

        areas.add(homes);
        areas.add(river);
        areas.add(safeZoneOne);
        areas.add(road);
        areas.add(safeZoneTwo);
    }

    public void onInitPlayer(){
        frogger = new Frog(0, 70);
        frogger.setLength(10);

        frogger.setLife(5);
    }
    /*
    *  \brief get traingle's area
    * */
    private float GetTriangleSquar(Position A, Position B, Position C) {
        float xVecA, yVecA, xVecB, yVecB;

        xVecA = A.x - B.x;
        yVecA = A.y - B.y;
        xVecB = C.x - A.x;
        yVecB = C.y - A.y;

        return Math.abs((xVecA * yVecB - yVecA * xVecB))/2.0f;
    }

    /**
     * \brief check whether a point is located in a triangle
     *
     * */
    private boolean IsInTriangle(Position A, Position B, Position C, Position D) {
        float SABC, SADB, SBDC, SADC;
        SABC = GetTriangleSquar(A, B, C);
        SADB = GetTriangleSquar(A, D, B);
        SBDC = GetTriangleSquar(B, D, C);
        SADC = GetTriangleSquar(A, D, C);

        float SumSuqar = SADB + SBDC + SADC;

        if ((-0.0001 < (SABC - SumSuqar)) && ((SABC - SumSuqar) < 0.0001)) {
            return true;
        } else {
            return false;
        }
    }

    public Position getMoveDirection(View v, float xt, float yt){
        Position current = new Position(xt, yt);
        Position leftTop, leftBottom, middle, rightTop, rightBottom;

        leftTop = new Position(0,0);
        leftBottom = new Position(0, v.getHeight());
        rightTop = new Position(v.getWidth(), 0);
        rightBottom = new Position(v.getWidth(), v.getHeight());
        middle = new Position(v.getWidth()/2, v.getHeight()/2);

        if (IsInTriangle(leftTop, leftBottom, middle, current)) {
            //left
            Log.d("Left", "Left");
            return new Position(-stepLength, 0);
        }

        if (IsInTriangle(leftTop, rightTop, middle, current)) {
            //up
            Log.d("Left", "UP");
            return new Position(0, -stepLength);
        }

        if (IsInTriangle(rightTop, rightBottom, middle, current)) {
            //right
            Log.d("Left", "right");
            return new Position(stepLength, 0);
        }

        if (IsInTriangle(leftBottom, rightBottom, middle, current)) {
            //down
            Log.d("Left", "down");
            return new Position(0, stepLength);
        }

        return new Position(0,0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float xt, yt;
        Position direction;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xt = event.getX();
            yt = event.getY();

            direction = getMoveDirection(v, xt, yt);

            frogger.onMove(direction);
            this.invalidate();
        }
        return false;
    }

    protected void onRestart(){
        timer.removeCallbacks(this);
        areas.clear();
        onInitAllArea();
        onInitPlayer();
        timer.postDelayed(this, updatePeriod);
    }

    protected void onWin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        timer.removeCallbacks(this);
        frogger.totalLife--;
        score += timeBar.currentTime;
        if (frogger.totalLife == 0) {
            builder.setMessage("Good Job!\nYour Score: "+String.valueOf(score));
            builder.setTitle("Finish");
            builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onRestart();
                }
            });
            builder.create().show();
        } else {
            onContinue();
        }
    }

    public void onContinue(){
        frogger.reset();
        timeBar.reset();
        timer.postDelayed(this, updatePeriod);
    }

    protected void onDead(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        timer.removeCallbacks(this);
        frogger.totalLife--;
        if (frogger.totalLife == 0) {
            builder.setMessage("Game is Over.\nYour Score: "+String.valueOf(score));
            builder.setTitle("Over");
            builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onRestart();
                }
            });
            builder.create().show();
        } else {
            builder.setMessage("Frogger Dead.\nYour Score: "+String.valueOf(score));
            builder.setTitle("Lose one life");
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onContinue();
                }
            });
            builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onRestart();
                }
            });
            builder.create().show();
        }
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int i = 0;

        //1 Draw all the areas
        for (i = 0; i < areas.size(); i++) {
            areas.get(i).onDraw(canvas);
        }

        frogger.onDraw(canvas);
        timeBar.onDraw(canvas);
    }

    @Override
    public void run() {
        int i = 0;

        drawCounter++;
        if (drawCounter == drawSpeed) {
            for (i = 0; i < areas.size(); i++) {
                areas.get(i).onStep();
            }
            frogger.onStep();
            drawCounter = 0;
        }

        timeBar.onStep();
        // time check
        if (timeBar.onDeathCheck(frogger) == DeathStatus.DEAD) {
            onDead();
            return;
        }
        DeathStatus chkresult = DeathStatus.UNKNOWN;

        //0 death check.
        for (i = 0; i < areas.size(); i++) {
            if (areas.get(i).isInArea(frogger.y)) {
                chkresult = areas.get(i).onDeathCheck(frogger);
                if (chkresult == DeathStatus.DEAD) {
                    onDead();
                    return ;
                }

                if (chkresult == DeathStatus.WIN) {
                    onWin();
                    return ;
                }
            }
        }

        this.invalidate();
        timer.postDelayed(this, updatePeriod);
    }
}
