package com.example.codetracer.frogger;

/**
 * Created by codetracer on 3/26/17.
 */

public class River extends Area {
    public River(Position leftTop, Position rightBottom) {
        super(leftTop, rightBottom);
    }

    public DeathStatus onDeathCheck(Frog frogger){
        int i = 0;
        DeathStatus result = DeathStatus.DEAD;
        for (i = 0;i < itemList.size(); i++) {
            result = itemList.get(i).onDeathCheck(frogger);
            if (result == DeathStatus.SAFE)
                return result;
        }

        return DeathStatus.DEAD;
    }
}
