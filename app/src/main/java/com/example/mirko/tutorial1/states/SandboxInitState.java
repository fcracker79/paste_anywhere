package com.example.mirko.tutorial1.states;

import android.util.Log;

import com.example.mirko.tutorial1.SandboxRepository;

public class SandboxInitState implements SandboxState {

    private static final SandboxState INSTANCE = new SandboxInitState();
    public static SandboxState instance() {
        return INSTANCE;
    }
    private SandboxInitState() {}
    @Override
    public void onEnter(SandboxStateOwner owner, SandboxState previous) {
        owner.setEnableCreateButton(false);
        owner.setEnableSandboxValue(false);
        owner.endProgress();
        owner.setSandboxData(null, null);
    }

    @Override
    public void onExit(SandboxStateOwner owner, SandboxState next) {
        // Do nothing
    }

    @Override
    public void createClicked(final SandboxStateOwner owner) {
        owner.startProgress();
        owner.getSandboxRepository().create(
                new SandboxRepository.SandboxCreationEventListener() {
                    @Override
                    public void onSandboxCreationFailed(int statusCode, Throwable cause) {
                        // FIXME Error handling
                        Log.e("NETWORK", cause.getMessage(), cause);
                        owner.endProgress();
                    }

                    @Override
                    public void onSandboxCreated(String uri, String uid) {
                        owner.setSandboxData(uri, uid);
                        owner.setState(SandboxInSyncState.instance());
                        owner.endProgress();
                    }
                }
        );
    }

    @Override
    public void sandboxValueChanged(SandboxStateOwner owner) {

    }

    @Override
    public void sandboxNameChanged(SandboxStateOwner owner) {
        owner.setEnableCreateButton(
                owner.getSandboxName() != null &&
                owner.getSandboxName().toString().trim().length() > 0
        );
    }
}
