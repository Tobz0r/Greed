package com.dv13tes.greed;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Mainclass for greed game. Creates the user interface and
 * adds logic to all buttons.
 * @author Tobias Estefors
 */
public class MainActivity extends AppCompatActivity {

    private TextView scoreScreen, turnScreen;
    private ImageButton dice1, dice2, dice3, dice4, dice5, dice6;
    private Button  throwBtn, saveBtn;
    private Game game;
    private List<ImageButton> diceList;

    private Color standard;

    private boolean resetFlag =true;

    private int gameScore=0, turnScore=0, turns=0, turnTurn=0, prevScore=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        scoreScreen = (TextView) findViewById(R.id.scoreScreen);
        turnScreen = (TextView) findViewById(R.id.turnScore);

        diceList = new ArrayList<>();

        dice1 = (ImageButton) findViewById(R.id.dice1);
        diceList.add(dice1);
        dice2 = (ImageButton) findViewById(R.id.dice2);
        diceList.add(dice2);
        dice3 = (ImageButton) findViewById(R.id.dice3);
        diceList.add(dice3);
        dice4 = (ImageButton) findViewById(R.id.dice4);
        diceList.add(dice4);
        dice5 = (ImageButton) findViewById(R.id.dice5);
        diceList.add(dice5);
        dice6 = (ImageButton) findViewById(R.id.dice6);
        diceList.add(dice6);


        for (final ImageButton img : diceList) {
            img.setActivated(true);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (img.isActivated()) {
                        img.setActivated(false);
                        img.setBackgroundColor(Color.GREEN);
                        // img.setImageAlpha(255);
                    } else {
                        img.setActivated(true);
                        img.setBackgroundColor(Color.WHITE);
                      //  img.setImageAlpha(255);
                    }
                }
            });
        }
        game = new Game(diceList);
        if (savedInstanceState != null) {
            gameScore = savedInstanceState.getInt("TotalScore");
            turnScore = savedInstanceState.getInt("TurnScore");
            turns= savedInstanceState.getInt("Turns");
            game = savedInstanceState.getParcelable("Game");
            turnScreen.setText("Turn score : " + turnScore);
            scoreScreen.setText("Score: " + gameScore);
            List<ImageButton> tempList = game.getDiceList();
            int[] images=game.getImages();
            for(int j=0; j < diceList.size();j++){
                diceList.get(j).setActivated(tempList.get(j).isActivated());
                diceList.get(j).setBackgroundColor(tempList.get(j).isActivated() ?
                      Color.WHITE : Color.GREEN);
                diceList.get(j).setImageResource(images[j]);

            }
            game.setDiceList(diceList);
        }
        throwBtn = (Button) findViewById(R.id.throwBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        throwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // to avoid cheating with locking dices from earlier throws
                if(resetFlag){
                    activateButtons();
                    resetFlag =false;
                }
                turnTurn++;
                turns++;
                turnScore = game.getScore();
                if((turnTurn==1 && turnScore<300)||(turnTurn>=2 && turnScore <= prevScore)){
                    Toast.makeText(getApplicationContext(), "Too low score\n Reroll",
                            Toast.LENGTH_SHORT).show();
                    resetFlag =true;
                    saveBtn.setEnabled(false);
                    turnTurn=0;
                }else{
                    saveBtn.setEnabled(true);
                }
                turnScreen.setText("Turn score : " + turnScore);
                prevScore=turnScore;
            }

        });
        saveBtn.setEnabled(false);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameScore += turnScore;
                scoreScreen.setText("Score: " + gameScore);
                //end the game if you reach 10000 score
                if (gameScore >= 10000) {
                    Intent i = new Intent(getApplicationContext(),
                            FinishActivity.class);
                    Bundle save = new Bundle();
                    save.putInt("Turns", turns);
                    save.putInt("Score", gameScore);
                    i.putExtras(save);
                    startActivity(i);
                }
                activateButtons();
                turnScore = 0;
                turnTurn = 0;
                turnScreen.setText("Turn score : " + turnScore);
                resetFlag = true;
                saveBtn.setEnabled(false);
            }
        });
    }

    /**
     * Used to save immportant variables when activity gets destroyed
     * if the phone if flipped.
     * @param savedInstanceState bundle where info will be stored
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("TurnScore",turnScore);
        savedInstanceState.putInt("TotalScore", gameScore);
        savedInstanceState.putInt("Turns", turns);
        savedInstanceState.putParcelable("Game", game);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Unlocks all dices
     */
    private void activateButtons(){
        for (ImageButton img : diceList) {
            img.setActivated(true);
            //img.setImageAlpha(255);
            img.setBackgroundColor(Color.WHITE);

        }
    }
}
