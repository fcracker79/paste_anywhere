package com.example.mirko.tutorial1;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ShowDBDataFragment extends Fragment {
    private ListView lv;
    private AsyncTask<Void, Void, Cursor> task;
    private ListAdapter adapter;
    private MyDatabase db;
    public static ShowDBDataFragment newInstance() {
        return new ShowDBDataFragment();
    }

    public ShowDBDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_show_dbdata, container, false);

        this.lv = (ListView) v.findViewById(R.id.db_row_list);

        this.adapter = new SimpleCursorAdapter(getActivity(), R.layout.db_row,
                null, new String[] {"name", "surname", "age"},
                new int[] { R.id.nameField, R.id.surnameField, R.id.ageField},
                0);
        lv.setAdapter(this.adapter);

        task = new DBAsyncTask(getActivity());
        task.execute();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.lv = null;
        if (task != null) {
            task.cancel(false);
            task = null;
        }
        if (adapter != null && ((CursorAdapter) adapter).getCursor() != null) {
            ((CursorAdapter) adapter).getCursor().close();
            adapter = null;
        }
        db.close();
        db = null;
    }

    private class DBAsyncTask extends AsyncTask<Void, Void, Cursor> {
        private final Context ctx;
        public DBAsyncTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            return MyDatabase.instance(ctx.getApplicationContext())
                    .getReadableDatabase()
                    .query("people", new String[] {"ROWID AS _id", "name", "surname", "age"},
                            null, null, null, null, "surname");

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            ((CursorAdapter) adapter).changeCursor(cursor);
        }
    }
}
