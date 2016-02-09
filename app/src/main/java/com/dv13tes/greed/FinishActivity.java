package com.dv13tes.greed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    TextView gzText, turnText;
    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        playAgain=(Button) findViewById(R.id.playAgain);
        gzText = (TextView) findViewById(R.id.endText);
        turnText = (TextView) findViewById(R.id.turnText);

        Bundle extras = getIntent().getExtras();

        int score=extras.getInt("Score");
        int turns=extras.getInt("Turns");

        turnText.setText("You got "+ score + " points in " +turns+ " rounds!");

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
