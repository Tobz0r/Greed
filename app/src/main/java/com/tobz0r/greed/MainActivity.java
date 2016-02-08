package com.tobz0r.greed;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoreScreen, turnScreen;
    private ImageButton dice1, dice2, dice3, dice4, dice5, dice6;
    private Button scoreBtn, throwBtn, saveBtn;
    private Game game;
    private List<ImageButton> diceList;

    private int gameScore, turnScore, turns;

    private boolean clicked=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diceList = new ArrayList<>();
        scoreScreen = (TextView) findViewById(R.id.scoreScreen);
        turnScreen = (TextView) findViewById(R.id.turnScore);
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

        scoreBtn = (Button) findViewById(R.id.scoreBtn);
        throwBtn = (Button) findViewById(R.id.throwBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        gameScore = 0;
        turnScore = 0;
        turns = 0;
        game = new Game(diceList);

        for (final ImageButton img : diceList) {
            img.setActivated(true);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(img.isActivated()) {
                        img.setActivated(false);
                        img.setImageResource(R.drawable.save);
                    }
                    else{
                        img.setActivated(true);
                        img.setImageResource(R.drawable.blank);
                    }
                }
            });
        }

        throwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.rollTheDice();
                turnScore = game.getScore();
                turnScore = turnScore >= 300 ? turnScore : 0;
                turnScreen.setText("Turn score : " + turnScore);
                saveBtn.setEnabled(true);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameScore += turnScore;
                scoreScreen.setText("Score: " + gameScore);
                for (ImageButton img : diceList) {
                    img.setActivated(true);
                    img.setImageResource(R.drawable.blank);
                }
                turnScore = 0;
                turnScreen.setText("Turn score : " + turnScore);
                game.rollTheDice();
                saveBtn.setEnabled(false);
            }
        });
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"eliashej",Toast.LENGTH_SHORT).show();
            }
        });
        if(savedInstanceState!=null){
            gameScore=savedInstanceState.getInt("TotalScore");
            turnScore=savedInstanceState.getInt("TurnScore");
           // game=savedInstanceState.getParcelable("Game");
            turnScreen.setText("Turn score : " + turnScore);
            scoreScreen.setText("Score: " + gameScore);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("TurnScore",turnScore);
        savedInstanceState.putInt("TotalScore",gameScore);
      //  savedInstanceState.putParcelable("Game",game);
        super.onSaveInstanceState(savedInstanceState);
    }


}
