package com.insomnihack.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.insomnihack.RsaThingies;
import com.insomnihack.utils.DataManager;
import com.insomnihack.utils.JniThingies;
import com.insomnihack.utils.LocalGameState;
import com.masreferenceapp.R;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GameController extends AppCompatActivity  {

    /* Some hardcoded value to force players to reverse-engineer this part a bit */
    public static final String AUTH_TOKEN = "5dcee5aa573d199ccabc64631db935f198ab7cee";

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

    private String loadSavedGame () {

        String result = "";

        File downloadsDir = Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, "CTF_saved_stats.xml");

        if ( ! file.exists ()) {
            return "No saved game available...";
        }

        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append('\n');
            }
        } catch (IOException e) {

            Log.e ("CTF", e.getLocalizedMessage (), e);
            return "Error reading the file: " + e.getLocalizedMessage ();
        }

        /* The expected format is:
             <?xml version='1.0' encoding='UTF-8' standalone='yes' ?>
             <game_stats>
                <score>1</score>
                <fastest_time>2</fastest_time>
                <least_moves>1</least_moves>
                <flag>49dc53f1-ac08-5f0e-b05c-237f51fb8dec</flag>
            </game_stats>§[signature in base64...]
         */
        String fileContents = new String (content);

        String[] fileParts = fileContents.split ("§");

        if (fileParts.length != 2) {
            return "Wrong file format";
        }

        String xml = fileParts [0];
        String signature = fileParts [1];

        try {
            boolean isValidSign = new RsaThingies().verifySignature (xml.getBytes (StandardCharsets.UTF_8), signature);

            if ( ! isValidSign) {
                return "Wrong signature";
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();
            XmlHandler handler = new XmlHandler();

            parser.parse(new InputSource(new StringReader(xml)), handler);

            String query = handler.getParsedValue();
            /* The data was properly signed by our app, so we can completely trust this input, right? */
            Log.i ("CTF", "-----------");
            Log.i ("CTF", query);
            DataManager db = DataManager.Companion.getInstance (this);

            db.importGame (query);
            result = "Import successful!";

        } catch (Exception e) {
            Log.e ("CTF", e.getLocalizedMessage (), e);
            return "Wrong signature: " + e;
        }

        return result;
    }

    /**
     * This inner class is needed to parse the XML using SAXParser
     */
    private static class XmlHandler extends DefaultHandler {
        private final StringBuilder currentValue = new StringBuilder();
        private String rootNode = "";

        private final HashMap<String,String> values = new HashMap<>();

        private final StringBuilder parsedMessage = new StringBuilder("INSERT INTO ");

        // Called at the start of an element
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            String local = localName.isEmpty() ? qName : localName;

            if (rootNode.isEmpty()) { /* The root node will always be the first element */
                rootNode = local.trim();
                // Starts the query with "INSERT INTO <root_node> " ...
                parsedMessage.append (rootNode).append(" ");
            }

            currentValue.setLength(0);
        }

        // Called when character data is encountered
        @Override
        public void characters(char[] ch, int start, int length) {
            currentValue.append(ch, start, length);
        }

        // Called at the end of an element
        @Override
        public void endElement(String uri, String localName, String qName) {
            String local = localName.isEmpty() ? qName : localName;

            if (local.trim ().equals (rootNode)) {
                // (<columns...>)
                parsedMessage.append (" (");
                parsedMessage.append (
                        String.join (",", values.keySet ())
                    );
                parsedMessage.append (") ");
                // Ends the query with ... " VALUES ( <...> )"
                parsedMessage.append (" VALUES (");
                parsedMessage.append (
                        String.join (",", values.values ())
                    );
                parsedMessage.append (");");

                return;
            }

            if (local.trim().equals("flag")) {
                local = "other_data";
            }

            values.put (local, currentValue.toString ());
            currentValue.setLength (0);
        }

        /**
         * Function to be called after the XML is parsed, to retrieve the result
         */
        public String getParsedValue () {
            return new String (parsedMessage);
        }
    }


    private String saveGameToDb (Uri data) {
        DataManager db = DataManager.Companion.getInstance (this);

        String scoreStr = data.getQueryParameter ("score");
        String streakStr = data.getQueryParameter ("streak");
        String totalTimeStr = data.getQueryParameter ("time");
        String moves = data.getQueryParameter ("moves");
        String otherData = data.getQueryParameter ("data");

        if (scoreStr == null || streakStr == null || totalTimeStr == null || moves == null || otherData == null) {
            return "🙈";
        }

        long id;

        try {
            id = db.addGameStats (
                    Integer.parseInt (scoreStr),
                    Integer.parseInt (streakStr),
                    Integer.parseInt (totalTimeStr),
                    Integer.parseInt (moves),
                    otherData
                );

        } catch (NumberFormatException ignored) {

            return "💩";
        }

        return "Game saved to the DB!\nID: " + id;
    }


    private String readFromDb (String idStr) {
        DataManager db = DataManager.Companion.getInstance (this);

        if (idStr == null) {
            return "🙈";
        }

        DataManager.GameStats stats;
        try {
            stats = db.getGameStats (
                    Long.parseLong(idStr)
                );

        } catch (NumberFormatException ignored) {

            return "💩";
        }

        if (stats == null) {
            return "🙈";
        }

        return "Timestamp: " + stats.getTimestamp()
            + "\nScore: " + stats.getScore()
            + "\nStreak: " + stats.getStreak()
            + "\nTotal time: " + stats.getTotalTime()
            + "\nMoves: " + stats.getMoves()
            + "\nAdditional data: " + stats.getOtherData()
            ;
    }

    private String calcStats () {

        DataManager db = DataManager.Companion.getInstance (this);

        List<DataManager.GameStats> stats = db.getAllGameStats();

        int totalEntries = stats.size();
        int sumTime = 0;
        int maxStreak = 0;
        int totalScore = 0;
        int totalMoves = 0;

        for (int i = 0; i < stats.size() ; i ++) {

            DataManager.GameStats game = stats.get (i);

            sumTime += game.getTotalTime();
            totalScore += game.getScore();
            totalMoves += game.getMoves();

            int streak = game.getStreak ();

            if (streak > maxStreak) {
                maxStreak = streak;
            }
        }

        return "Total games played: " + totalEntries
            + "\nTotal time played: " + sumTime
            + "\nLongest streak: " + maxStreak
            + "\nTotal accumulated score: " + totalScore
            + "\nTotal moves: " + totalMoves
            ;
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

            case "read_saved_game" -> loadSavedGame();

            case "save_session" -> saveGameToDb (data);

            case "read_session" -> readFromDb (data.getQueryParameter("id"));

            case "calc_stats" -> calcStats ();

            default -> "🤔";
        };

        Log.i ("CTF", s);
        ((TextView) findViewById(R.id.deeplink_textview)).setText(s);
    }
}
