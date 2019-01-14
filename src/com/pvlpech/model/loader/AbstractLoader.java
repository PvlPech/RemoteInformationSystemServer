package com.pvlpech.model.loader;

import com.pvlpech.model.entity.Entity;
import com.pvlpech.model.entity.Group;
import com.pvlpech.model.utils.Constants;
import com.pvlpech.model.utils.Utils;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

/**
 * Created by pvlpech on 11.11.2018.
 */
public abstract class AbstractLoader implements Loader {

    private PropertyChangeSupport support;
    private final String GET_TOPIC = "get";
    private final String INFO_TOPIC = "info";

    private final String ADD_PREFIX = "add";
    private final String GET_PREFIX = "get";
    private final String DEL_PREFIX = "del";
    private final String GROUP_PREFIX = "group";



    abstract void store(String storagePath, Entity entity);

    abstract Collection load(String storagePath);

    abstract boolean remove(String storagePath, long id);

    AbstractLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void loadGroups() {
        support.firePropertyChange(GET_TOPIC, null, Utils.getGroupsJSON(load(Constants.GROUP_STORAGE_PATH)));
    }

    public void storeGroup(int number, int faculty) {
        Group group = new Group(number, faculty);
        store(Constants.GROUP_STORAGE_PATH, group);
        support.firePropertyChange(GET_TOPIC, null, Utils.getGroupsJSON(load(Constants.GROUP_STORAGE_PATH)));
    }

    public void removeGroup(long id) {
        boolean isSuccess = remove(Constants.GROUP_STORAGE_PATH, id);
        if (isSuccess) {
            support.firePropertyChange(GET_TOPIC, null, Utils.getGroupsJSON(load(Constants.GROUP_STORAGE_PATH)));
        } else {
            support.firePropertyChange(INFO_TOPIC, null, "Group was not removed. Please check id");
        }
    }

    public void performAction(JSONObject jsonObject) {
        String operation = jsonObject.getString("operation");
        String entity = jsonObject.getString("entity");
        if (GROUP_PREFIX.equals(entity)) {
            switch (operation) {
                case ADD_PREFIX:
                    storeGroup(jsonObject.getInt("number"), jsonObject.getInt("faculty"));
                    break;
                case GET_PREFIX:
                    loadGroups();
                    break;
                case DEL_PREFIX:
                    removeGroup(jsonObject.getLong("id"));
                    break;
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        for(PropertyChangeListener propertyChangeListener : support.getPropertyChangeListeners()){
            System.out.println(propertyChangeListener.hashCode());
        }
        System.out.println("Current listener: " + pcl.hashCode());
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


}
