package com.applets.apomalyn.labo1;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applets.apomalyn.labo1.task.Task;
import com.applets.apomalyn.labo1.task.TaskContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    private static final String fileName = "tasksList.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constructTaskList();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TaskFragment fragment = new TaskFragment();
        fragmentTransaction.add(R.id.list, fragment);
        fragmentTransaction.commit();
    }

    private void constructTaskList(){
        JSONObject json = getJSON();

        int idCurrent = 0;
        String nameCurrent = "";
        String detailsCurrent = "";
        boolean isCompletedCurrent = false;
        Date completedDateCurrent = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        if(json != null){
            Iterator<String> keys = json.keys();
            JSONObject next = null;

            while (keys.hasNext()){
                try{
                    next = json.getJSONObject(keys.next());
                    idCurrent = next.getInt("id");
                    nameCurrent = next.getString("name");
                    detailsCurrent = (next.isNull("details")) ? "":next.getString("details");
                    isCompletedCurrent = (next.isNull("isCompleted")) ? false:next.getBoolean("isCompleted");
                    completedDateCurrent = (next.isNull("completedDate")) ? null:format.parse(next.getString("completedDate"));
                }catch(JSONException e){
                    e.printStackTrace();
                }catch (ParseException e){
                    completedDateCurrent = null;
                }
                TaskContent.add(new Task(idCurrent, nameCurrent, detailsCurrent, isCompletedCurrent, completedDateCurrent));
            }
        }
    }

    private JSONObject getJSON() {
        String json = null;
        JSONObject object = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            object = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }

    @Override
    public void onListFragmentInteraction(Task item) {

    }
}
