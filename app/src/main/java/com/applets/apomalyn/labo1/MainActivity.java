package com.applets.apomalyn.labo1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.applets.apomalyn.labo1.task.Task;
import com.applets.apomalyn.labo1.task.TaskContent;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    private static final String fileName = "taskList.json";

    public static final int ADD_TASK_ACTIVITY = 0;
    public static final int DETAILS_TASK_ACTIVITY = 1;

    public static final int SAVE_TASK = 10;
    public static final int RELOAD_TASKS = 11;

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

        if(TaskContent.ITEMS.size() == 0)
            TaskContent.loadTasks(getApplicationContext());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == SAVE_TASK){
            saveTasks();
            fragment.updateList();
        }else if(resultCode == RELOAD_TASKS){
            TaskContent.loadTasks(getApplicationContext());
            fragment.updateList();
        }
    }

    private void saveTasks(){
        try{
            TaskContent.saveTasks(getApplicationContext());
        }catch (IOException e){
            Toast.makeText(this, "Une erreur est survenue durant la sauvegarde",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListFragmentInteraction(Task item) {
        Intent intent = new Intent(MainActivity.this, DetailsTaskActivity.class);
        intent.putExtra("id", item.getId());
        startActivityForResult(intent, DETAILS_TASK_ACTIVITY);
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
        saveTasks();
    }
}
