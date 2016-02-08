package com.tobz0r.greed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageButton;

import java.util.List;
import java.util.Random;

/**
 * Created by Tobz0r on 2016-01-24.
 */
public class Game {

    private final int nrOfDice=6;
    private final int nrOfDieValues=6;
    private List<ImageButton> diceList;

    private int[] dices;

    public Game(List<ImageButton> diceList){
        dices=new int[nrOfDice];
        this.diceList=diceList;
    }

    /**
     *
     */
    public void rollTheDice(){

        Random rand = new Random();

        for(int i=0; i < nrOfDice; i++) {
            if(diceList.get(i).isEnabled()) {
                int dice = 1 + rand.nextInt((nrOfDieValues));
                dices[i] = dice;
                switch (dice) {
                    case 1:
                        diceList.get(i).setBackgroundResource(R.drawable.white1);
                        break;
                    case 2:
                        diceList.get(i).setBackgroundResource(R.drawable.white2);
                        break;
                    case 3:
                        diceList.get(i).setBackgroundResource(R.drawable.white3);
                        break;
                    case 4:
                        diceList.get(i).setBackgroundResource(R.drawable.white4);
                        break;
                    case 5:
                        diceList.get(i).setBackgroundResource(R.drawable.white5);
                        break;
                    case 6:
                        diceList.get(i).setBackgroundResource(R.drawable.white6);
                        break;
                    default:
                        Log.e("Game", "Error while rolling");
                        break;
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public int getScore(){
        int retval;
        int[] dieValues={0,0,0,0,0,0};
        for(int i = 0; i < nrOfDice; i++) {
            if(dices[i] != 0) {
                dieValues[dices[i]-1]++;
            }
        }
        if(isStraight(dieValues)){
            return 1000;
        }
        if((retval=isThreeOfAKind(dieValues))!=0){
            return retval==1 ? 1000 : 100*retval;
        }
        return (100*dieValues[0])+(50*dieValues[4]);
    }

    /**
     * @param dieValues
     * @return
     */
    private int isThreeOfAKind( int dieValues[]) {
        for(int i = 0; i < nrOfDieValues; i++) {
            if(3 <= dieValues[i]) {
                return i+1;
            }
        }
        return 0;
    }

    /**
     * @param dieValues
     * @return
     */
    private boolean isStraight( int dieValues[]) {
        for(int i=0; i < nrOfDieValues; i++) {
            if(dieValues[i] != 1) {
                return false;
            }
        }
        return true;
    }
}
