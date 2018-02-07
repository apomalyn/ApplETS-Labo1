package com.applets.apomalyn.labo1.task;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskContent {

    /**
     * An array of sample (Task) items.
     */
    public static final List<Task> ITEMS = new ArrayList<>();

    /**
     * A map of sample (Task) items, by ID.
     */
    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();

    public static void add(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put("" + item.getId(), item);
    }

    private static Task createTask(String name) {
        int id = 0;
        for(Task task: ITEMS) {
            if(task.getId() > id)
                id = task.getId();
        }
        return new Task(id, name);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        Task task = ITEMS.get(position);
        if(task.getDetails().isEmpty()){
            builder.append("Aucun details sur cette tache.");
        }else{
            builder.append("Details about ").append(task.getName()).append(" :\n").append(task.getDetails());
        }

        return builder.toString();
    }
}
