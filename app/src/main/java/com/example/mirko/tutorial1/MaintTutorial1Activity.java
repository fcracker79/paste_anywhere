package com.example.mirko.tutorial1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MaintTutorial1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_tutorial1);
        final TextView t = (TextView) findViewById(R.id.textView);

        t.setTextColor(Color.RED);

        ((CompoundButton) findViewById(R.id.title_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText(R.string.title_red);
                    ((TextView) findViewById(R.id.textView)).setTextColor(Color.BLACK);
                } else {
                    buttonView.setText(R.string.title_black);
                    ((TextView) findViewById(R.id.textView)).setTextColor(Color.RED);
                }
            }
        });

        ((Switch) findViewById(R.id.switch_pasticcia_colori)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            ((ImageView) findViewById(R.id.mirkuccioBellissimo))
                                    .setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
                        } else {
                            ((ImageView) findViewById(R.id.mirkuccioBellissimo))
                                    .setColorFilter(null);
                        }
                    }
                }

        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maint_tutorial1, menu);

        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                View view = findViewById(R.id.mirkuccioBellissimo);
                view.setAlpha(view.getAlpha() == 0.5f ? 1f : 0.5f);

                return true;
            }
        });

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

    public void onNascondiLoSgorbio(View theButtonView) {
        final Button theButton = (Button) theButtonView;
        final View mirkuccioBellissimo = findViewById(R.id.mirkuccioBellissimo);
        if (mirkuccioBellissimo.getVisibility() == View.VISIBLE) {
            mirkuccioBellissimo.setVisibility(View.INVISIBLE);
            theButton.setText(R.string.mostra_sgorbio);
        } else {
            mirkuccioBellissimo.setVisibility(View.VISIBLE);
            theButton.setText(R.string.nascondi_sgorbio);
        }
    }
}
