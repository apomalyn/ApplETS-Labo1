package com.applets.apomalyn.labo1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.applets.apomalyn.labo1.task.Task;
import com.applets.apomalyn.labo1.task.TaskContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    private static final String fileName = "taskList.json";

    public static final int ADD_TASK_ACTIVITY = 0;
    public static final int DETAILS_TASK_ACTIVITY = 1;

    public static final int SAVE_TASK = 10;

    private TaskFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        this.fragment = (TaskFragment) getFragmentManager().findFragmentById(R.id.list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_ACTIVITY);
            }
        });

        constructTaskList();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == SAVE_TASK){
            saveTask();
        }
    }

    private void constructTaskList(){
        JSONObject json = getJSON();

            int idCurrent = 0;
        String nameCurrent = "";
        String detailsCurrent = "";
        boolean isCompletedCurrent = false;
        Date completedDateCurrent = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m", Locale.ENGLISH);

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
            InputStream is = openFileInput(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            object = new JSONObject(json);
        } catch (IOException ex) {
            return null;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }

    public void saveTask(){
        JSONObject json = new JSONObject();
        FileOutputStream out;
        Task current = null;

        try {
            for (int i = 0; i < TaskContent.ITEMS.size(); i++){
                current = TaskContent.ITEMS.get(i);
                json.put("task_" + i, current.save());
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            String j = json.toString();
            out.write(json.toString().getBytes());
            out.close();
        }catch(IOException e){
            Toast.makeText(this, "Une erreur est survenue durant la sauvegarde",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListFragmentInteraction(Task item) {
//        Intent intent = new Intent(MainActivity.this, DetailsTaskActivity.class);
//        startActivityForResult(intent, DETAILS_TASK_ACTIVITY);
        Toast.makeText(this, "Details !",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListCheckBoxChange(Task item, boolean isChecked) {
        if(isChecked){
            item.setCompleted(true);
            item.setCompletedDate(new Date());
        }else{
            item.setCompleted(false);
            item.setCompletedDate(null);
        }
        saveTask();
    }
}
