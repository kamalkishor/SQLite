package com.example.kamal.flat;

/**
 * Created by Kamal on 16-Apr-15.
 */
public class Message {

    private String _timestamp;
    private String _id;
    private String _data;
    private String _type;

    public Message() {

    }

    public Message(String timestamp, String id, String data, String type) {
        this._timestamp = timestamp;
        this._id = id;
        this._data = data;
        this._type = type;
    }

    public void setTimeStamp(String timestamp) {
        this._timestamp = timestamp;
    }

    public String getTimeStamp() {
        return this._id;
    }

    public void setID(String id) {
        this._id = id;
    }

    public String getID() {
        return this._id;
    }

    public void setData(String data) {
        this._data = data;
    }

    public String getData() {
        return this._data;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getType() {
        return this._type;
    }


}
