package com.example.mirko.tutorial1.backend;

/**
 * Created by mirko on 17/11/15.
 */
public interface SandboxRepository {
    void getData(String uid, String token, final SandboxDataReceiveEventListener l);
    void postData(String uid, CharSequence data, final SandboxDataSaveEventListener l);
    void create(CharSequence token, final SandboxCreationEventListener l);

    interface SandboxCreationEventListener {
        void onSandboxCreationFailed(int statusCode, Throwable cause);
        void onSandboxCreated(String uri, String uid);
    }

    interface SandboxDataSaveEventListener {
        void onDataSaveFailed(int statusCode, Throwable cause);
        void onDataSaved();
    }

    interface SandboxDataReceiveEventListener {
        void onDataReceived(String data, String uri);
        void onDataReceiveFailed(int statusCode, Throwable cause);
    }
}
