package org.insomnihack.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.insomnihack.utils.JniThingies;
import org.insomnihack.utils.LocalGameState;
import org.masreferenceapp.R;

public class ScoreAdminActivity extends AppCompatActivity {

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
        Log.i("CTF", "Here is your flag for copy+paste: " + flag);

        ((TextView) findViewById(R.id.flag_textView))
                .setText(flag);

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
