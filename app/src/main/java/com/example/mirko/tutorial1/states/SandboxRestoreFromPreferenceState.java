package com.example.mirko.tutorial1.states;

import android.util.Log;

import com.example.mirko.tutorial1.SandboxRepository;

/**
 * Created by mirko on 15/11/15.
 */
public class SandboxRestoreFromPreferenceState implements SandboxState {
    private static final SandboxState INSTANCE = new SandboxRestoreFromPreferenceState();
    public static SandboxState instance() {
        return INSTANCE;
    }
    private SandboxRestoreFromPreferenceState() {}

    @Override
    public void onEnter(final SandboxStateOwner owner, SandboxState previous) {
        owner.getSandboxRepository().getData(owner.getToken().toString(),
                new SandboxRepository.SandboxDataReceiveEventListener() {
                    @Override
                    public void onDataReceiveFailed(int statusCode, Throwable cause) {
                        // FIXME error handling
                        Log.e("NETWORK", cause.getMessage(), cause);
                        owner.endProgress();
                    }

                    @Override
                    public void onDataReceived(String data, String uri) {
                        owner.setSandboxContents(data, uri);
                        owner.endProgress();
                        owner.setState(SandboxInSyncState.instance());
                    }
                });
    }

    @Override
    public void onExit(SandboxStateOwner owner, SandboxState next) {

    }

    @Override
    public void createClicked(SandboxStateOwner owner) {

    }

    @Override
    public void sandboxValueChanged(SandboxStateOwner owner) {

    }

    @Override
    public void sandboxNameChanged(SandboxStateOwner owner) {

    }
}
