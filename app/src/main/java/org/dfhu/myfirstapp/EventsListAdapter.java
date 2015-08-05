package org.dfhu.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class EventsListAdapter extends ArrayAdapter<LifeCycleEvent> {

    private static class ViewCache {
        public TextView eventKey;
        public TextView eventValue;
        public TextView eventDateAdded;
        public TextView eventId;
    }

    public EventsListAdapter(Context context, int resource, List<LifeCycleEvent> objects) {
        super(context, resource, objects);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LifeCycleEvent lifeCycleEvent = getItem(position);

        ViewCache viewCache;
        if (convertView == null) {
            viewCache = new ViewCache();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.life_cycle_event_item, parent, false);
            viewCache.eventKey = (TextView) convertView.findViewById(R.id.eventKey);
            viewCache.eventValue = (TextView) convertView.findViewById(R.id.eventValue);
            viewCache.eventDateAdded = (TextView) convertView.findViewById(R.id.eventDateAdded);
            viewCache.eventId = (TextView) convertView.findViewById(R.id.eventTableId);
            convertView.setTag(viewCache);
        } else {
            viewCache = (ViewCache) convertView.getTag();
        }

        viewCache.eventKey.setText(lifeCycleEvent.getKey());
        viewCache.eventValue.setText(lifeCycleEvent.getValue());
        viewCache.eventDateAdded.setText(lifeCycleEvent.getDateAdded());
        viewCache.eventId.setText(lifeCycleEvent.getId().toString());

        return convertView;
    }

    public int getRowId (int position) {
        return getItem(position).getId();
    }
}
