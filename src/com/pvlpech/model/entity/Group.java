package com.pvlpech.model.entity;

import java.util.UUID;

/**
 * Created by pvlpech on 11.11.2018.
 */
public class Group implements Entity {

    private long id;
    private int number;
    private int faculty;

    public Group(int number, int faculty) {
        this.number = number;
        this.faculty = faculty;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFaculty() {
        return faculty;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", number=" + number +
                ", faculty=" + faculty +
                '}';
    }
}
