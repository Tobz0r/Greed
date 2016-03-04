package com.dv13tes.greed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Endscreen for greedgame, shows your score and gives
 * option to play again
 */
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

        playAgain=(Button) findViewById(R.id.saveBtn);
        gzText = (TextView) findViewById(R.id.turnScore);
        turnText = (TextView) findViewById(R.id.scoreScreen);
        Bundle extras = getIntent().getExtras();

        gzText.setText(R.string.gz_message);
        turnText.setText(String.format(getResources().getString(R.string.end_message),
                extras.getInt("Score"), extras.getInt("Turns")));


        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
                FinishActivity.this.finish();
            }
        });
    }
    /**
     * Called when the activity has detected the user's press of the back
     * key. Starts a new game and end this acitivty
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        FinishActivity.this.finish();

    }
}
