package com.example.mirko.tutorial1;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MaintTutorial1Activity extends ListActivity {
    private static final String[] DATA = {"Minnie", "Topolino", "Gambadilegno", "Pluto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);

        final ArrayAdapter listAdapter =
                new ArrayAdapter(this, R.layout.myrow, R.id.label, DATA) {
                    @Override
                    public View getView(int position, View convertView,
                                        ViewGroup parent) {
                        final View row = super.getView(position, convertView, parent);
                        ViewHolder h = (ViewHolder) row.getTag();
                        if (h == null) {
                            h = new ViewHolder(row);
                            row.setTag(h);
                        }
                        if (position % 2 == 0) {
                            h.icon.setImageResource(R.drawable.mirkuccio);
                        }
                        else {
                            h.icon.setImageResource(R.drawable.ic_launcher);
                        }
                        h.size.setText(String.valueOf(DATA[position].length()));
                        h.label.setText(DATA[position]);
                        return row;
                    }
                };

        setListAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        SparseBooleanArray checkedItemPositions = l.getCheckedItemPositions();

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DATA.length; i++) {
            if (checkedItemPositions.get(i, false)) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(DATA[i]);
            }
        }
        Toast.makeText(MaintTutorial1Activity.this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maint_tutorial1, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class ViewHolder {
        private final ImageView icon;
        private final TextView size;
        private final TextView label;
        public ViewHolder(View row) {
            this.icon = (ImageView) row.findViewById(R.id.myicon);
            this.size = (TextView) row.findViewById(R.id.size);
            this.label = (TextView) row.findViewById(R.id.label);
        }
    }
}
