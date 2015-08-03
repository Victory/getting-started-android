package org.dfhu.myfirstapp;

public class LifeCycleEvent {
    private String key;
    private String value;
    private String date_added;
    private int _ID;

    public Integer getId() {
        return _ID;
    }

    public void setId(int id) {
        this._ID = id;
    }

    public String getDateAdded() {
        return date_added;
    }

    public void setDateAdded(String date_added) {
        this.date_added = date_added;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getKey() + ": " + getValue() + " id: " + getId();
    }
}
