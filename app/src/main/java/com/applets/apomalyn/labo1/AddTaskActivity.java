package com.applets.apomalyn.labo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.applets.apomalyn.labo1.task.TaskContent;

public class AddTaskActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText detailsField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        nameField = findViewById(R.id.nameText);
        detailsField = findViewById(R.id.detailsText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.done){
            return true;
        }
        return true;
    }

    public void onClickItem(MenuItem item){
        if(!(nameField.getText().toString().equals(null) || nameField.getText().toString().equals(""))){
            TaskContent.createTask(nameField.getText().toString(), detailsField.getText().toString());
            setResult(MainActivity.SAVE_TASK);
            finish();
        }else{
            Toast.makeText(this, "Vous devez saisir un nom",
                    Toast.LENGTH_LONG).show();
        }
    }
}
