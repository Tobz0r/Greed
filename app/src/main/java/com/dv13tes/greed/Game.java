package com.dv13tes.greed;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Gameclass containing all logic for the game.
 * @author Tobias Estefors
 */
public class Game implements Parcelable {

    private int dicesUsed;
    private final int nrOfDice=6;
    private final int nrOfDieValues=6;
    private int[] dieValues;
    private List<ImageButton> diceList;
    private List<Integer> givenPoints;
    private int threeOfAKindValue[];
    private int mData;
    private int[] dices;
    private int[] images;

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
        threeOfAKindValue=new int[2];
        givenPoints=new ArrayList<>();
        dices=new int[nrOfDice];
        images=new int[]{R.drawable.white1,
                R.drawable.white2,
                R.drawable.white3,
                R.drawable.white4,
                R.drawable.white5,
                R.drawable.white6};
    }

    private Game(Parcel in){
        mData=in.readInt();
    }


    /**
     * Calculates the score for this throw.
     * @return the score based on what dies was thrown
     */
    public int getScore(){
        threeOfAKindValue= new int[]{0, 0};
        dicesUsed=0;
        rollTheDice();
        Log.i("dieValues", Arrays.toString(dices));
        int retVal,secondRetVal;
        boolean threeOne=false,threeFive=false;
        int returnScore=0;
        dieValues= new int[]{0, 0, 0, 0, 0, 0};
        for(int i = 0; i < nrOfDice; i++) {
            if(dices[i] != 0) {
                dieValues[dices[i]-1]++;
            }
        }
        if(isStraight(dieValues)){
            dicesUsed=6;
            return 1000;
        }
        if((retVal=isThreeOfAKind(dieValues,0))!=0){
            returnScore = retVal==1 ? 1000 : 100*retVal;
            secondRetVal=isThreeOfAKind(dieValues,retVal);
            returnScore += secondRetVal==1?1000:100*secondRetVal;
            threeOne= retVal==1 || secondRetVal==1 ? true : false;
            threeFive = retVal==5 || secondRetVal==5? true : false;
            dicesUsed=secondRetVal!=0 ? 6 : 3;
            threeOfAKindValue=new int[]{retVal,secondRetVal};
        }
        returnScore=calculateScore(threeOne, threeFive, returnScore);
        return returnScore;
    }
    /**
     * Rolls each dice and adds the results to an array
     */
    private void rollTheDice(){
        Random rand = new Random();
        givenPoints.clear();
        for(int i=0; i < nrOfDice; i++) {
            if(diceList.get(i).isActivated()) {
                int dice = 1 + rand.nextInt((nrOfDieValues));
                dices[i] = dice;
                givenPoints.add(i);
                switch (dice) {
                    case 1:
                        diceList.get(i).setImageResource(R.drawable.white1);
                        images[i]=R.drawable.white1;
                        break;
                    case 2:
                        diceList.get(i).setImageResource(R.drawable.white2);
                        images[i]=R.drawable.white2;
                        break;
                    case 3:
                        diceList.get(i).setImageResource(R.drawable.white3);
                        images[i]=R.drawable.white3;
                        break;
                    case 4:
                        diceList.get(i).setImageResource(R.drawable.white4);
                        images[i]=R.drawable.white4;
                        break;
                    case 5:
                        diceList.get(i).setImageResource(R.drawable.white5);
                        images[i]=R.drawable.white5;
                        break;
                    case 6:
                        diceList.get(i).setImageResource(R.drawable.white6);
                        images[i]=R.drawable.white6;
                        break;
                    default:
                        Log.e("Game", "Error while rolling");
                        break;
                }
            }
        }
    }

    /**
     * Returns the number of dices giving points
     * @return A number containing dice giving points
     */
    public int getDicesUsed(){
        return dicesUsed;
    }

    /**
     * Tells the give if any dice in a new throw has given points
     * It checks for any value a potential three of a kind has given
     * and does not count overflowing three of a kind values(eg if four three's
     * has been thrown it wont count the fourth as a pointgiving)
     * Ones and fives will always give points either if their
     * in a three of a kind or not.
     * @return true if a new dice has given points, else false
     */
    public boolean newThrowGivenPoints(){

        if(givenPoints.isEmpty()){
            return false;
        }
        if(threeOfAKindValue[1]!=0){
            return true;
        }
        boolean returnValue=false;
        int threes=0;
        if(threeOfAKindValue[0]!=0) {
            if (dieValues[threeOfAKindValue[0] - 1] > 3) {
                threes = dieValues[threeOfAKindValue[0] - 1] - 3;
            }
        }
        for(int index: givenPoints){
            if(dices[index]==threeOfAKindValue[0] && threes==0){
                returnValue=true;
                break;
            }
            else if(dices[index]==threeOfAKindValue[0] && threes>0){
                threes--;
            }
            if(dices[index]==1 || dices[index]==5){
                returnValue=true;
                break;
            }
        }
        return returnValue;
    }

    /**
     * Calculates score based on the number of three of a kinds
     * @param threeOne true if three of a kind of ones
     * @param threeFive true if three of a kind of fives
     * @param returnScore the score to be returned
     * @return the new score
     */
    private int calculateScore(boolean threeOne,boolean  threeFive, int returnScore){
        if(threeOne){
            if(!threeFive){
                dicesUsed += dieValues[4];
                returnScore += 50*dieValues[4];
            }
            returnScore += ((100*dieValues[0])-300);
            dicesUsed += (dieValues[0]-3);
        }else if(threeFive){
            if(!threeOne){
                dicesUsed += dieValues[0];
                returnScore += 100*dieValues[0];
            }
            dicesUsed += dieValues[4]-3;
            returnScore += (50*dieValues[4])-150;
        }
        else{
            dicesUsed += dieValues[4];
            dicesUsed += dieValues[0];
            returnScore += 50*dieValues[4];
            returnScore += 100*dieValues[0];
        }
        return returnScore;
    }
    /**
     * Checks if throw contains a three of a kind
     * @param dieValues array containing the throw
     * @param except Value not to check for three of a kinds with.
     *               Used if you want to find more than one three of a kind
     *               <0 if you want all to be counted
     * @return Wich value who was a three of a kind
     */
    private int isThreeOfAKind( int dieValues[], int except) {
        for(int i = 0; i < nrOfDieValues; i++) {
            if(3 <= dieValues[i] && ((i+1)!=except)) {
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

    public int[] getImages(){
        return images;
    }

    /**
     * Returns a list of all dices
     * @return a arraylist with imagebuttons
     */
    public List<ImageButton> getDiceList(){
        return diceList;
    }

    /**
     *  Adds a dicelist to the game
     * @param diceList a list of dices
     */
    public void setDiceList(List<ImageButton> diceList){
        this.diceList=diceList;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
    }
}
