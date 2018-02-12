package com.applets.apomalyn.labo1.task;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TaskContent {

    private static final String fileName = "taskList.json";

    /**
     * An array of sample (Task) items.
     */
    public static final List<Task> ITEMS = new ArrayList<>();

    /**
     * A map of sample (Task) items, by ID.
     */
    public static final Map<Integer, Task> ITEM_MAP = new HashMap<Integer, Task>();



    public static void add(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static Task createTask(String name, String details) {
        int id = 0;
        for(Task task: ITEMS) {
            if(task.getId() > id)
                id = task.getId();
        }
        id++;
        Task task = new Task(id, name, details);
        add(task);
        return task;
    }

    public static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        Task task = ITEM_MAP.get(position);
        if(task.getDetails().isEmpty()){
            builder.append("Aucun details sur cette tache.");
        }else{
            builder.append(task.getDetails());
        }
        return builder.toString();
    }

    public static void saveTasks(Context context) throws IOException{
        save(context);
    }

    public static void loadTasks(Context context){
        JSONObject json = TaskContent.getJSON(context);
        int idCurrent = 0;
        String nameCurrent = "";
        String detailsCurrent = "";
        boolean isCompletedCurrent = false;
        Date completedDateCurrent = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m", Locale.ENGLISH);

        ITEMS.clear();
        ITEM_MAP.clear();

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
                add(new Task(idCurrent, nameCurrent, detailsCurrent, isCompletedCurrent, completedDateCurrent));
            }
        }
    }

    public static void save(Context context) throws IOException{
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

        out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        String j = json.toString();
        out.write(json.toString().getBytes());
        out.close();
    }

    public static JSONObject getJSON(Context context) {
        String json = null;
        JSONObject object = null;
        try {
            InputStream is = context.openFileInput(fileName);
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
}
