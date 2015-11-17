package com.example.mirko.tutorial1.states;

import com.example.mirko.tutorial1.backend.SandboxRepository;

/**
 * Created by mirko on 31/10/15.
 */
public interface SandboxStateOwner {
    void setState(SandboxState state);
    void setEnableCreateButton(boolean enable);
    void setEnableSandboxValue(boolean enable);
    void setEnableSandboxName(boolean enable);
    CharSequence getSandboxValue();
    CharSequence getSandboxName();
    SandboxRepository getSandboxRepository();
    String getUid();
    void setSandboxData(String uri, String uid);
    void setSandboxContents(String data, String uri);
    void startProgress();
    void endProgress();
    CharSequence getToken();
    void saveChannelInPreferences();
    boolean restoreChannelFromPreferences();
}
