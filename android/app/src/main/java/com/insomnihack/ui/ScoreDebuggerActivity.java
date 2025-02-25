package com.insomnihack.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.masreferenceapp.R;

public class ScoreDebuggerActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_debugger);

        // Initialize views
        scoreTextView = findViewById(R.id.score_text);
        Button increaseScoreButton = findViewById(R.id.increase_score_button);

        // Set initial score display
        scoreTextView.setText(String.valueOf(score));

        // Set onClickListener for the button
        increaseScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseScore();
            }
        });

        Button backButton = findViewById(R.id.go_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void increaseScore() {
        score += 100;
        scoreTextView.setText(String.valueOf(score));  // Update the score on the TextView
    }

    /* Intentionally unused (for another flag, potentially) */
    private void decreaseScore() {
        score -= 1;
        scoreTextView.setText(String.valueOf(score));  // Update the score on the TextView
    }
}
