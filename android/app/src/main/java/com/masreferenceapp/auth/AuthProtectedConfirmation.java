package com.masreferenceapp.auth;

import android.security.ConfirmationAlreadyPresentingException;
import android.security.ConfirmationCallback;
import android.security.ConfirmationNotAvailableException;
import android.security.ConfirmationPrompt;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
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

    @ReactMethod
    public void create(Promise promise) throws ConfirmationAlreadyPresentingException, ConfirmationNotAvailableException {
        ReturnStatus r = new ReturnStatus();
        try {
            Executor threadReceivingCallback = Runnable::run;
            MyConfirmationCallback callback = new MyConfirmationCallback(promise, r);
            byte[] myExtraData = new byte[10];
            ConfirmationPrompt dialog = (new ConfirmationPrompt.Builder(context))
                    .setPromptText("Thank you for supporting OWASP MAS with a donation of 25$. Do you want to confirm this donation?")
                    .setExtraData(myExtraData)
                    .build();
            dialog.presentPrompt(threadReceivingCallback, callback);
        } catch (Exception e) {
            r.fail(e.toString());
            promise.resolve(r.toJsonString());
        }
    }

    private static class MyConfirmationCallback extends ConfirmationCallback {

        private final Promise promise;
        private final ReturnStatus r;

        public MyConfirmationCallback(Promise promise, ReturnStatus r) {
            this.promise = promise;
            this.r = r;
        }

        @Override
        public void onConfirmed(@NonNull byte[] dataThatWasConfirmed) {
            super.onConfirmed(dataThatWasConfirmed);
            r.success("Android Protected Confirmation has been successfully used to confirm a message.");
            promise.resolve(r.toJsonString());
        }

        @Override
        public void onDismissed() {
            super.onDismissed();
            r.fail("Android Protected Confirmation was dismissed.");
            promise.resolve(r.toJsonString());
        }

        @Override
        public void onCanceled() {
            super.onCanceled();
            r.fail("Android Protected Confirmation was canceled.");
            promise.resolve(r.toJsonString());
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            r.fail(e.toString());
            promise.resolve(r.toJsonString());
        }
    }
}