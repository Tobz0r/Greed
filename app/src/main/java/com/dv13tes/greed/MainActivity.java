package com.dv13tes.greed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoreScreen, turnScreen;
    private ImageButton dice1, dice2, dice3, dice4, dice5, dice6;
    private Button  throwBtn, saveBtn;
    private Game game;
    private List<ImageButton> diceList;

    private boolean first=true;

    private int gameScore, turnScore, turns, turnTurn;


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
                        img.setImageResource(R.drawable.save);
                        img.setImageAlpha(127);
                    } else {
                        img.setActivated(true);
                        img.setImageResource(R.drawable.blank);
                        img.setImageAlpha(0);
                    }
                }
            });
        }
        gameScore = 0;
        turnScore = 0;
        turns = 0;
        turnTurn=0;
        game = new Game(diceList);

        if (savedInstanceState != null) {
            gameScore = savedInstanceState.getInt("TotalScore");
            turnScore = savedInstanceState.getInt("TurnScore");
            turns= savedInstanceState.getInt("Turns");
            game = savedInstanceState.getParcelable("Game");
            turnScreen.setText("Turn score : " + turnScore);
            scoreScreen.setText("Score: " + gameScore);
            List<ImageButton> tempList = game.getDiceList();
            for(int j=0; j < diceList.size();j++){
                diceList.get(j).setBackground(tempList.get(j).getBackground());
                diceList.get(j).setActivated(tempList.get(j).isActivated());
                diceList.get(j).setImageResource(tempList.get(j).isActivated() ?
                                               R.drawable.blank : R.drawable.save);
            }
            game.setDiceList(diceList);
        }
        throwBtn = (Button) findViewById(R.id.throwBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        throwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // to avoid cheating with locking dices from earlier throws
                if(first) {
                    activateButtons();
                    first=false;
                }
                turnTurn++;
                turns++;
                game.rollTheDice();
                turnScore = game.getScore();
                if(turnTurn==1 && turnScore<300){
                    Toast.makeText(getApplicationContext(), "Too low score\n Rerolling",
                            Toast.LENGTH_SHORT).show();
                    game.rollTheDice();
                    turnScore = game.getScore();
                    turnTurn++;
                }
                if(turnTurn>=2 && turnScore == 0){
                    Toast.makeText(getApplicationContext(), "No dices giving points\n Rerolling",
                            Toast.LENGTH_SHORT).show();
                    activateButtons();
                    game.rollTheDice();
                    turnScore = game.getScore();
                }
                turnScreen.setText("Turn score : " + turnScore);
                turnScore = turnScore >= 300 ? turnScore : 0;
                saveBtn.setEnabled(true);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turnScore>=300) {
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
                    game.rollTheDice();
                    turnScore = game.getScore();
                    turnScreen.setText("Turn score : " + turnScore);
                }else{
                    Toast.makeText(getApplicationContext(), "Need 300 points to save",
                            Toast.LENGTH_SHORT).show();
                }

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


    private void activateButtons(){
        for (ImageButton img : diceList) {
            img.setActivated(true);
            img.setImageResource(R.drawable.blank);
        }
    }
}
