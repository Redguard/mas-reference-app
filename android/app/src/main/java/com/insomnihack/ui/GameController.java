package com.insomnihack.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.insomnihack.RsaThingies;
import com.insomnihack.utils.JniThingies;
import com.insomnihack.utils.LocalGameState;
import com.masreferenceapp.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

    /**
     * Helper function to get the text content of a specific tag.
     */
    private String getTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            // Out XML only has one element, we don't have to worry about more complex scenarios
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    private String loadSavedGame () {

        String result = "";

        File downloadsDir = Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, "CTF_saved_state.xml");

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
            boolean isValidSign = true; //new RsaThingies().verifySignature (xml.getBytes (StandardCharsets.UTF_8), signature);

            if ( ! isValidSign) {
                return "Wrong signature";
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();
            /* The data was properly signed, so we can completely trust this input, right? */
            //factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);

            SAXParser parser = factory.newSAXParser();
            XmlHandler handler = new XmlHandler();

            parser.parse(new InputSource(new StringReader(xml)), handler);

            result = handler.getParsedValue();
            Log.i ("CTF", result);

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

        private final StringBuilder parsedMessage = new StringBuilder();

        // Called at the start of an element
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentValue.setLength(0); // Clear the StringBuilder
        }

        // Called when character data is encountered
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            currentValue.append(ch, start, length);
        }

        // Called at the end of an element
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String local = localName.isEmpty() ? qName : localName;

            try {

                String data = local + ": " + currentValue.toString() + "\n";
                parsedMessage.append(data);

            } catch (NumberFormatException e) {
                throw new SAXException("Invalid XML value", e);
            }
        }

        /**
         * Function to be called after the XML is parsed, to retrieve the result
         */
        public String getParsedValue () {
            return new String (parsedMessage);
        }

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

            default -> "🤔";
        };

        Log.i ("CTF", s);
        ((TextView) findViewById(R.id.deeplink_textview)).setText(s);
    }
}
