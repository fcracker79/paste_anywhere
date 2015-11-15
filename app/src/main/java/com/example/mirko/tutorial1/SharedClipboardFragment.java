package com.example.mirko.tutorial1;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mirko.tutorial1.states.SandboxInSyncState;
import com.example.mirko.tutorial1.states.SandboxInitState;
import com.example.mirko.tutorial1.states.SandboxRestoreFromPreferenceState;
import com.example.mirko.tutorial1.states.SandboxState;
import com.example.mirko.tutorial1.states.SandboxStateOwner;

public class SharedClipboardFragment extends Fragment implements SandboxStateOwner {
    private volatile SandboxState state;

    private volatile Button mCreateButton;
    private volatile EditText mSandbox;
    private volatile EditText mSandboxName;
    private volatile SandboxRepository sandboxRepository;
    private volatile String uid;
    private volatile ProgressBar mProgressBar;
    private volatile TextView mHyperlink;
    private volatile Context ctx;
    private volatile boolean restoreFromSharedPreferences;
    public static SharedClipboardFragment newInstance(boolean restoreFromSharedPreferences) {
        final SharedClipboardFragment result = new SharedClipboardFragment();
        result.restoreFromSharedPreferences = restoreFromSharedPreferences;

        return result;
    }

    public SharedClipboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        final View v = inflater.inflate(R.layout.fragment_shared_clipboard, container, false);

        /*
        This should be in the ' onAttach()' method
         */
        this.ctx = inflater.getContext();
        this.sandboxRepository = new SandboxRepository(this.ctx);

        this.mCreateButton = (Button) v.findViewById(R.id.button_attach_sandbox);
        this.mSandbox = (EditText) v.findViewById(R.id.edit_text_sandbox);
        this.mSandboxName = (EditText) v.findViewById(R.id.edit_text_sandbox_name);

        this.mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedClipboardFragment.this.state.createClicked(SharedClipboardFragment.this);
            }
        });

        this.mSandbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedClipboardFragment.this.state.sandboxValueChanged(SharedClipboardFragment.this);
            }
        });

        mSandboxName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (SharedClipboardFragment.this.state != null) {
                    SharedClipboardFragment.this.state.sandboxNameChanged(SharedClipboardFragment.this);
                }
            }
        });

        this.mProgressBar = (ProgressBar) v.findViewById(R.id.progress_loading);

        this.mHyperlink = (TextView) v.findViewById(R.id.text_view_hyperlink);
        this.mHyperlink.setMovementMethod(LinkMovementMethod.getInstance());

        boolean successfullyRestoredFromPreferences = false;
        if (restoreFromSharedPreferences) {
            /*
            This is to prevent the fragment to accidentally run the refresh from the preferences.
            This should not be a problem, as the current state is automatically saved.
            Maybe, though, I might think of changing this behaviour.
             */
            restoreFromSharedPreferences = false;
            if (restoreChannelFromPreferences()) {
                setState(SandboxRestoreFromPreferenceState.instance());
                successfullyRestoredFromPreferences = true;
            }
        }

        if (!successfullyRestoredFromPreferences) {
            if (this.state != null) {
                // Refresh the current state.
                // This may happen when rotating the device
                this.setState(this.state);
            } else {
                this.setState(SandboxInitState.instance());
            }
        }

        return v;
    }

    @Override
    public void setState(SandboxState state) {
        final SandboxState previousState = this.state;
        if (previousState != null) {
            previousState.onExit(this, state);
        }

        this.state = state;
        state.onEnter(this, previousState);
    }

    @Override
    public void setEnableCreateButton(boolean enable) {
        this.mCreateButton.setVisibility(enable ? View.VISIBLE : View.GONE);
        this.mCreateButton.setEnabled(enable);
    }

    @Override
    public void setEnableSandboxValue(boolean enable) {
        this.mSandbox.setVisibility(enable ? View.VISIBLE : View.GONE);
        this.mSandbox.setEnabled(enable);
        this.mHyperlink.setVisibility(enable ? View.VISIBLE : View.GONE);
        this.mHyperlink.setEnabled(enable);
    }

    @Override
    public String getSandboxValue() {
        return this.mSandbox.getText() == null ? null : this.mSandbox.getText().toString();
    }

    @Override
    public CharSequence getSandboxName() {
        return mSandboxName.getText();
    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        throw new RuntimeException("Incredibly enough, not called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.ctx = null;
        this.sandboxRepository = null;
    }

    @Override
    public SandboxRepository getSandboxRepository() {
        return sandboxRepository;
    }

    @Override
    public void setSandboxData(String uri, String uid) {
        this.uid = uid;
        this.mHyperlink.setText(uri);
    }

    @Override
    public void startProgress() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void endProgress() {
        this.mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getUid() {
        return uid;
    }

    private void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public CharSequence getToken() {
        return mSandboxName.getText();
    }

    private void setToken(String token) {
        mSandboxName.setText(token);
    }

    @Override
    public void setEnableSandboxName(boolean enable) {
        this.mSandboxName.setEnabled(enable);
    }

    private static final String PREF_NAME = "pasteAnywhereState";
    private static final String TOKEN = "token";
    private static final String UID = "uid";
    @Override
    public void saveChannelInPreferences() {
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, getToken().toString());
        editor.putString(UID, getUid());
        editor.apply();
    }

    @Override
    public boolean restoreChannelFromPreferences() {
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(TOKEN, null);
        if (token == null) {
            return false;
        }

        setToken(token);
        setUid(sharedPreferences.getString(UID, null));

        return true;
    }

    @Override
    public void setSandboxContents(String data, String uri) {
        this.mSandbox.setText(data);
        this.mHyperlink.setText(uri);
    }
}
