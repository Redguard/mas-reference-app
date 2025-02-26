package com.insomnihack.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.insomnihack.utils.JniThingies;
import com.insomnihack.utils.LocalGameState;
import com.masreferenceapp.R;

public class ScoreDebuggerActivity extends AppCompatActivity {

    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_debugger);

        // Initialize views
        scoreTextView = findViewById(R.id.score_text);
        Button increaseScoreButton = findViewById(R.id.increase_score_button);

        // Set initial score display
        scoreTextView.setText(String.valueOf(JniThingies.getInstance().getScore()));

        increaseScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseScore();
            }
        });

        // Get flag
        Button getFlagButton = findViewById(R.id.get_flag_button);
        getFlagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlag ();
            }
        });

        // Go Back
        Button backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void getFlag() {
        String flag = JniThingies.getInstance().genHighScoreFlag();
        Log.i("CTF", flag);
        // TODO: show it somewhere in the UI (?)
    }

    private void increaseScore() {
        LocalGameState.getInstance().add(1000);
        scoreTextView.setText(String.valueOf(JniThingies.getInstance().getScore()));
    }

    /* Intentionally unused (for another flag, potentially?) */
    private void decreaseScore() {
        LocalGameState.getInstance().add(-1);
        scoreTextView.setText(String.valueOf(JniThingies.getInstance().getScore()));
    }
}
