package com.example.mirko.tutorial1.backend;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

class SandboxParseRepository implements SandboxRepository {
    private static final String URL = "https://pasteanywhere.parseapp.com?c=%s";
    private static final String SANDBOX_CLASS_NAME = "Sandbox";

    @Override
    public void getData(final String uid, final String token, final SandboxDataReceiveEventListener l) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(SANDBOX_CLASS_NAME);
        query.getInBackground(
                uid, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e != null) {
                            l.onDataReceiveFailed(500, e);
                        } else if (!token.equals(object.getString("token"))) {
                            l.onDataReceiveFailed(404, new IllegalArgumentException("Not found"));
                        } else {
                            l.onDataReceived(object.getString("data"),
                                    String.format(URL, uid));
                        }
                    }
                }
        );
    }
    public void postData(String uid, final CharSequence data, final SandboxDataSaveEventListener l) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(SANDBOX_CLASS_NAME);
        query.getInBackground(uid, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.put("data", data.toString());
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                l.onDataSaved();
                            } else {
                                l.onDataSaveFailed(500, e);
                            }
                        }
                    });
                } else {
                    l.onDataSaveFailed(500, e);
                }
            }
        });
    }

    public void create(final CharSequence token, final SandboxCreationEventListener l) {
        final ParseObject sandbox = new ParseObject(SANDBOX_CLASS_NAME);
        sandbox.put("token", token.toString());
        sandbox.saveInBackground(
                new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            l.onSandboxCreationFailed(500, e);
                        } else {
                            final String uid = sandbox.getObjectId();
                            l.onSandboxCreated(
                                    String.format(URL, uid),
                                    uid);
                                }
                        }
                    }
        );
    }
}
