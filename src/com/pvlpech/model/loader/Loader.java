package com.pvlpech.model.loader;

import com.pvlpech.model.entity.Entity;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * Created by pvlpech on 11.11.2018.
 */
public interface Loader {

    void loadGroups();

    void storeGroup(int number, int faculty);

    void removeGroup(long id);

    void performAction(JSONObject jsonObject);

     void addPropertyChangeListener(PropertyChangeListener pcl);

    void removePropertyChangeListener(PropertyChangeListener pcl);

}
