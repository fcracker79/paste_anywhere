package com.example.mirko.tutorial1.states;

public interface SandboxState {
    void onEnter(SandboxStateOwner owner, SandboxState previous);
    void onExit(SandboxStateOwner owner, SandboxState next);
    void createClicked(SandboxStateOwner owner);
    void sandboxValueChanged(SandboxStateOwner owner);
    void sandboxNameChanged(SandboxStateOwner owner);
}
