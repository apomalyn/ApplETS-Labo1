package com.applets.apomalyn.labo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.applets.apomalyn.labo1.task.Task;
import com.applets.apomalyn.labo1.task.TaskContent;

import java.io.IOException;

import static com.applets.apomalyn.labo1.MainActivity.ADD_TASK_ACTIVITY;

public class DetailsTaskActivity extends AppCompatActivity {

    private Task task;

    private TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        details = findViewById(R.id.details);
        Bundle params = this.getIntent().getExtras();

        if(params != null){
            task = TaskContent.ITEM_MAP.get(params.getInt("id"));
            toolbar.setTitle(task.getName());
            details.setText(TaskContent.makeDetails(task.getId()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                Intent intent = new Intent(DetailsTaskActivity.this, AddTaskActivity.class);
                intent.putExtra("id", task.getId());
                startActivityForResult(intent, ADD_TASK_ACTIVITY);
                break;
            case R.id.delete:
                for (int i = 0; i < TaskContent.ITEMS.size(); i++){
                    if(TaskContent.ITEMS.get(i).getId() == task.getId()){
                        TaskContent.ITEMS.remove(i);
                        setResult(MainActivity.SAVE_TASK);
                        this.finish();
                        break;
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == MainActivity.SAVE_TASK){
            try{
                TaskContent.save(getApplicationContext());
                task = TaskContent.ITEM_MAP.get(task.getId());
                details.setText(TaskContent.makeDetails(task.getId()));
            }catch (IOException e){
                Toast.makeText(this, "Une erreur est survenue durant la sauvegarde",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
