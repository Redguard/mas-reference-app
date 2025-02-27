package com.insomnihack.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.insomnihack.utils.JniThingies;
import com.insomnihack.utils.LocalGameState;
import com.masreferenceapp.R;

public class GameController extends AppCompatActivity  {

    /* Some hardcoded value to force players to reverse-engineer this part a bit */
    private final String AUTH_TOKEN = "5dcee5aa573d199ccabc64631db935f198ab7cee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        Button backButton = findViewById(R.id.go_back_button);
        backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        handleDeepLink(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {

        if (intent == null || (!Intent.ACTION_VIEW.equals(intent.getAction()))) {
            Log.i ("CTF", "Not handling this intent. Received: " + intent);
            return;
        }

        Uri data = intent.getData();

        if (data == null) {
            Log.i ("CTF", "No data available with the intent");
            return;
        }

        String command = data.getQueryParameter("command");
        String token = data.getQueryParameter("token");

        if ( (command == null) || (token == null)) {
            Log.i ("CTF", "Missing required parameters :(");
            return;
        }

        if ( ! AUTH_TOKEN.equals (token) ) {
            return;
        }

        String s = switch (command) {
            case "getScore" -> "Local score: " + LocalGameState.getInstance().getLocalScore()
                    + "\nVerification score: " + JniThingies.getInstance().getScore();

            case "resetScore" -> {
                LocalGameState.getInstance().resetScore();
                yield "Score reset. New value: " + LocalGameState.getInstance().getLocalScore();
            }

            case "wolololo" -> "You found the secret command!\nHere is your flag: "
                    + JniThingies.getInstance().genWololoFlag(command, token);

            default -> "🤔";
        };

        Log.i ("CTF", s);
        ((TextView) findViewById(R.id.deeplink_textview)).setText(s);
    }
}
