package org.dfhu.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class MyActivity extends AppCompatActivity implements InfoFragment.OnFragmentInteractionListener {

    public static final String EXTRA_MESSAGE = "org.dfhu.myfirstapp.MESSAGE";

    private AtomicReference<String> whenStopped = new AtomicReference<>("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // if we have a savedInstanceState we probably already have the fragment, return so
        // we don't have double fragments
        if (savedInstanceState == null) {
            setupInfoFragment();
        }
    }

    private void setupInfoFragment() {
        View fragmentContainer = findViewById(R.id.fragmentContainer);

        // if this view doesn't have a fragmentContainer return
        if (fragmentContainer == null) {
            return;
        }

        InfoFragment infoFragment = InfoFragment.newInstance("foo", "bar");
        // pass along any special extras from the intent
        infoFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, infoFragment).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                //openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** called when user clicks the send button  */
    public void sendMessage (View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void swapOutFragment (View view) {
        SwapedFragment fragment = SwapedFragment.newInstance();
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }


    @Override
    protected void onRestart () {
        TextView lifeCycle = (TextView) findViewById(R.id.lifeCycle);
        String prefix = getResources().getString(R.string.called_restart);
        lifeCycle.setText(prefix + " " + whenStopped + " Now: " + nowString());


        super.onRestart();
    }

    @Override
    protected void onStop() {

        whenStopped.set(nowString());
        super.onStop();
    }

    /** return pretty printed now string */
    private String nowString () {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss");
        return sdf.format(new Date());
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
