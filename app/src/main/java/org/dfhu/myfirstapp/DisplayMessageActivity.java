package org.dfhu.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class DisplayMessageActivity extends AppCompatActivity {

    // the activity is created each time so we need to make these incrementors static
    private static AtomicInteger numOnPause = new AtomicInteger(0);
    private static AtomicInteger numOnStop = new AtomicInteger(0);
    private static AtomicInteger numOnDestroy = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        message = message.concat(" numPause: " + numOnPause.get());
        message = message.concat(" numStop: " + numOnStop.get());
        message = message.concat(" numDestory: " + numOnDestroy.get());

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        setContentView(textView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Toast.makeText(
                    DisplayMessageActivity.this, "clicked for home", Toast.LENGTH_SHORT).show();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        numOnPause.incrementAndGet();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        numOnDestroy.incrementAndGet();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        numOnStop.incrementAndGet();
        super.onStop();
    }
}
