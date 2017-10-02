package com.example.batrakov.activitytaskgrid;

import java.io.Serializable;

/**
 * Created by batrakov on 28.09.17.
 */

class Cat implements Serializable {
    private String mName;
    private String mBreed;
    private String mAge;

    Cat(String aName, String aBreed, String aAge) {
        mName = aName;
        mBreed = aBreed;
        mAge = aAge;
    }


    public String getName() {
        return mName;
    }

    public void setName(String aName) {
        this.mName = aName;
    }

    String getBreed() {
        return mBreed;
    }

    String getAge() {
        return mAge;
    }
}
