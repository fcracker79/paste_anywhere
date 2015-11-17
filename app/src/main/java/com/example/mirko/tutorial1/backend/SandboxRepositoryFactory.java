package com.example.mirko.tutorial1.backend;

import android.content.Context;

/**
 * Created by mirko on 17/11/15.
 */
public final class SandboxRepositoryFactory {
    private static final SandboxRepositoryFactory INSTANCE =
            new SandboxRepositoryFactory();

    public static SandboxRepositoryFactory instance() {
        return INSTANCE;
    }
    private SandboxRepositoryFactory() {}

    public SandboxRepository create(Context ctx) {
        return new SandboxParseRepository();
    }

}
