package com.pvlpech.model.loader;

import com.pvlpech.model.entity.Entity;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pvlpech on 11.11.2018.
 */
public class FileLoader extends AbstractLoader {

    @Override
    public void store(String storagePath, Entity entity) {
        Collection entities = load(storagePath);
        entities.add(entity);
        serializableObject(storagePath, entities);
    }

    private void serializableObject(String fileName, Object object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
//            TODO throw exception to the view layer
        }
    }

    @Override
    public Collection load(String storagePath) {
        Collection entries = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(storagePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            entries = (Collection) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
//            system should not do anything in case of file absence
        } catch (IOException e) {
            e.printStackTrace();
//            TODO throw exception to the view layer
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            TODO throw exception to the view layer
        }
        return entries;
    }

    @Override
    boolean remove(String storagePath, long id) {
        Collection<Entity> groups = load(storagePath);
        Entity groupToRemove = null;
        for (Entity group : groups) {
            if (id == group.getId()) {
                groupToRemove = group;
                break;
            }
        }
        if (groupToRemove != null) {
            groups.remove(groupToRemove);
            serializableObject(storagePath, groups);
            return true;
        } else {
            return false;
        }
    }

}
