package com.example.kamal.flat;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       try {

           String data = loadJSONFromAsset();

           JSONObject obj = new JSONObject(data);

           JSONArray chats = (JSONArray) obj.get("chats");

           MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);


           for(int counter = 0; counter< chats.length(); counter++){
               String singleChat = chats.getString(counter);
               JSONObject newobj = new JSONObject(singleChat);
               Message message =
                       new Message(newobj.getString("timestamp"),newobj.getString("msg_id"),newobj.getString("msg_data"),newobj.getString("msg_type"));
               dbHandler.addMessage(message);

               //System.out.println("Id : "+ newobj.get("msg_id"));
           }


       }catch (Exception e){
           e.getStackTrace();
       }

try {
    mListView = (ListView) findViewById(R.id.listview);

    mAdapter = new SimpleCursorAdapter(getBaseContext(),
            R.layout.listview_item_layout,
            null,
            new String[]{MyDBHandler.COLUMN_TIMESTAMP, MyDBHandler.COLUMN_ID, MyDBHandler.COLUMN_DATA, MyDBHandler.COLUMN_TYPE},
            new int[]{R.id.timestamp, R.id.id, R.id.data, R.id.type}, 0);

    mListView.setAdapter(mAdapter);

    /** Creating a loader for populating listview from sqlite database */
    /** This statement, invokes the method onCreatedLoader() */
    getSupportLoaderManager().initLoader(0, null, this);
}catch (Exception e){
    e.getStackTrace();
}
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getResources().openRawResource(
                    getResources().getIdentifier("raw/aqziuquq",
                            "raw", getPackageName()));

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** A callback method invoked by the loader when initLoader() is called */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = Message_Content_Provider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    /** A callback method, invoked after the requested content provider returned all the data */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
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
}
