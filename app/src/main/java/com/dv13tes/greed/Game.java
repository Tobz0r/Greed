package com.dv13tes.greed;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageButton;


import java.util.List;
import java.util.Random;

/**
 * Gameclass containing all logic for the game.
 * @author Tobias Estefors
 */
public class Game implements Parcelable {

    private final int nrOfDice=6;
    private final int nrOfDieValues=6;
    private List<ImageButton> diceList;
    private int mData;
    private int[] dices;


    public static final Parcelable.Creator<Game> CREATOR
            = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };


    public Game(List<ImageButton> diceList){
        this.diceList=diceList;
        dices=new int[nrOfDice];
    }

    private Game(Parcel in){
        mData=in.readInt();
    }


    /**
     * Rolls each dice and adds the results to an array
     */
    public void rollTheDice(){

        Random rand = new Random();

        for(int i=0; i < nrOfDice; i++) {
            if(diceList.get(i).isActivated()) {
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
     * Calculates the score for this throw.
     * @return the score based on what dies was thrown
     */
    public int getScore(){
        int retVal;
        int[] dieValues={0,0,0,0,0,0};
        for(int i = 0; i < nrOfDice; i++) {
            if(dices[i] != 0) {
                dieValues[dices[i]-1]++;
            }
        }
        if(isStraight(dieValues)){
            return 1000;
        }
        if((retVal=isThreeOfAKind(dieValues))!=0){
            return retVal==1 ? 1000 : 100*retVal;
        }
        return (100*dieValues[0])+(50*dieValues[4]);
    }

    /**
     * Checks if throw contains a three of a kind
     * @param dieValues array containing the throw
     * @return Wich value who was a three of a kind
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
     * Checks if throw was a straight
     * @param dieValues array containing the throw
     * @return true if a straight, else false
     */
    private boolean isStraight( int dieValues[]) {
        for(int i=0; i < nrOfDieValues; i++) {
            if(dieValues[i] != 1) {
                return false;
            }
        }
        return true;
    }

    public List<ImageButton> getDiceList(){
        return diceList;
    }

    public void setDiceList(List diceList){
        this.diceList=diceList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
