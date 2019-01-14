package com.pvlpech.model.utils;

import com.pvlpech.model.entity.Group;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by pvlpech on 14.11.2018.
 */
public class Utils {

    public static JSONObject getGroupsJSON(Collection<Group> groups) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", "group");
        JSONArray array = new JSONArray();
        JSONObject item;
        for (Group group : groups) {
            item = new JSONObject();
            item.put("id", group.getId());
            item.put("number", group.getNumber());
            item.put("faculty", group.getFaculty());
            array.put(item);
        }
        jsonObject.put("entities", array);
        return jsonObject;
    }

}
