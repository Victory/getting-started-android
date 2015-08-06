package org.dfhu.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MyActivity extends AppCompatActivity implements InfoFragment.OnFragmentInteractionListener {

    private List<LifeCycleEvent> allEvents;

    private LifeCycleEventsSource lifeCycleEventsSource;
    private ListView listView;

    public static final String EXTRA_MESSAGE = "org.dfhu.myfirstapp.MESSAGE";

    private AtomicReference<String> whenStopped = new AtomicReference<>("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_activity_my);

        // if we have a savedInstanceState we probably already have the fragment, return so
        // we don't have double fragments
        if (savedInstanceState == null) {
            setupInfoFragment();
        }


        lifeCycleEventsSource = new LifeCycleEventsSource(this);
        try {
            lifeCycleEventsSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setupListView();

    }

    private void setupListView() {
        allEvents = lifeCycleEventsSource.getAll();
        final EventsListAdapter  adapter = new EventsListAdapter(this, R.layout.life_cycle_event_item, allEvents);
        getListView().setAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyActivity.this);

                final LifeCycleEvent item = adapter.getItem(position);
                final int itemId = adapter.getRowId(position);

                dialogBuilder.setTitle("Are you sure?");
                dialogBuilder.setMessage("The item will be deleted");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            lifeCycleEventsSource.deleteById(itemId);
                        } catch (SQLException e) {
                            Toast.makeText(MyActivity.this, "Could not Delete", Toast.LENGTH_LONG).show();
                            return;
                        }
                        adapter.remove(item);
                        Toast.makeText(MyActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                    }
                });

                dialogBuilder.setNegativeButton(android.R.string.cancel, null);
                dialogBuilder.show();

            }
        });
    }


    private ListView getListView () {
        if (listView == null) {
            listView = (ListView) findViewById(R.id.eventsList);
        }
        return listView;
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

    /** called when user clicks insertLifeCycleEvent */
    public synchronized void insertLifeCycleEvent (View view) throws SQLException {
        lifeCycleEventsSource = new LifeCycleEventsSource(this);
        LifeCycleEvent event = lifeCycleEventsSource.insertValue("magic", "information");
        EventsListAdapter adapter = (EventsListAdapter) getListView().getAdapter();
        adapter.add(event);
        adapter.notifyDataSetChanged();
    }


    public void clearList (View view) throws SQLException {
        lifeCycleEventsSource = new LifeCycleEventsSource(this);
        lifeCycleEventsSource.empty();
        EventsListAdapter adapter = (EventsListAdapter) getListView().getAdapter();
        adapter.clear();
    }


    public void swapOutFragment (View view) {
        if (findViewById(R.id.swapedFragment) == null) {
            SwapedFragment fragment = SwapedFragment.newInstance();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        } else {
            InfoFragment fragment = InfoFragment.newInstance("foo", "bar");
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
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
