package com.applets.apomalyn.labo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.applets.apomalyn.labo1.task.Task;
import com.applets.apomalyn.labo1.task.TaskContent;

public class AddTaskActivity extends AppCompatActivity {

    private EditText nameField;

    private EditText detailsField;

    private int id = -1;

    private final static String titleAdd = "Add";
    private final static String titleModify = "Edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(titleAdd);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        nameField = findViewById(R.id.nameText);
        detailsField = findViewById(R.id.detailsText);

        Bundle params = this.getIntent().getExtras();

        if(params != null){
            myToolbar.setTitle(titleModify);
            id = params.getInt("id") ;
            Task task = TaskContent.ITEM_MAP.get(id);
            if(task != null){
                nameField.setText(task.getName());
                detailsField.setText(task.getDetails());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }


    public void onClickItem(MenuItem item){
        if(!(nameField.getText().toString().equals(null) || nameField.getText().toString().equals(""))){
            if(id > -1){
                TaskContent.ITEM_MAP.get(id).setName(nameField.getText().toString());
                TaskContent.ITEM_MAP.get(id).setDetails(detailsField.getText().toString());
            }else{
                TaskContent.createTask(nameField.getText().toString(), detailsField.getText().toString());
            }
            setResult(MainActivity.SAVE_TASK);
            finish();
        }else{
            Toast.makeText(this, "Vous devez saisir un nom",
                    Toast.LENGTH_LONG).show();
        }
    }
}
