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

    /**
     * onCreate is going to be where i create the GUI because
     * it will be shown when the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        playAgain=(Button) findViewById(R.id.playAgain);
        gzText = (TextView) findViewById(R.id.endText);
        turnText = (TextView) findViewById(R.id.turnText);
        Bundle extras = getIntent().getExtras();

        gzText.setText("Congratulations, you won!");
        turnText.setText("You got "+ extras.getInt("Score") + " points in " +
                extras.getInt("Turns")+ " rounds!");

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
            }
        });
    }
    /**
     * Called when the activity has detected the user's press of the back
     * key.
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
