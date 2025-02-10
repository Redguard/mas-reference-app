package com.masreferenceapp.auth;

import android.security.ConfirmationAlreadyPresentingException;
import android.security.ConfirmationCallback;
import android.security.ConfirmationNotAvailableException;
import android.security.ConfirmationPrompt;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.util.concurrent.Executor;


public class AuthProtectedConfirmation extends ReactContextBaseJavaModule {

    ReactApplicationContext context;

    public AuthProtectedConfirmation(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "AuthProtectedConfirmation";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String create() throws ConfirmationAlreadyPresentingException, ConfirmationNotAvailableException {

        try {
            final int MY_EXTRA_DATA_LENGTH = 100;
            byte[] myExtraData = new byte[MY_EXTRA_DATA_LENGTH];
            ConfirmationPromptData myDialogData = new ConfirmationPromptData("Max", "Moritz", "500");
            Executor threadReceivingCallback = Runnable::run;
            MyConfirmationCallback callback = new MyConfirmationCallback();
            ConfirmationPrompt dialog = (new ConfirmationPrompt.Builder(context))
                    .setPromptText(myDialogData.sender + ", send " + myDialogData.amount + " to " + myDialogData.receiver + "?")
                    .setExtraData(myExtraData)
                    .build();
            dialog.presentPrompt(threadReceivingCallback, callback);
        } catch (ConfirmationNotAvailableException e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }

        ReturnStatus r = new ReturnStatus("OK", "Protected confirmation created.");
        return r.toJsonString();
    }

    private static class ConfirmationPromptData {
        String sender, receiver, amount;

        ConfirmationPromptData(String sender, String receiver, String amount) {
            this.sender = sender;
            this.receiver = receiver;
            this.amount = amount;
        }
    }

    private static class MyConfirmationCallback extends ConfirmationCallback {

        @Override
        public void onConfirmed(@NonNull byte[] dataThatWasConfirmed) {
            super.onConfirmed(dataThatWasConfirmed);
            // Sign dataThatWasConfirmed using your generated signing key.
            // By completing this process, you generate a signed statement.
        }

        @Override
        public void onDismissed() {
            super.onDismissed();
            // Handle case where user declined the prompt in the
            // confirmation dialog.
        }

        @Override
        public void onCanceled() {
            super.onCanceled();
            // Handle case where your app closed the dialog before the user
            // responded to the prompt.
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            // Handle the exception that the callback captured.
        }
    }
}