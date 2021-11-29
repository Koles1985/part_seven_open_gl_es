package com.koles.part_7opengles;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
    interface PoolObjectFactory<T>{
        T createObject();
    }
    private final int maxSize;
    private List<T> freeObject;
    private PoolObjectFactory<T> factory;

    public Pool(PoolObjectFactory<T> factory, int maxSize){
        this.factory = factory;
        this.maxSize = maxSize;
        freeObject = new ArrayList<T>(maxSize);
    }

    public T newObject(){
        T object = null;
        if(freeObject.size() == 0){
            object = factory.createObject();
        }else {
            object = freeObject.get(freeObject.size() - 1);
        }
        return object;
    }

    public void tryAddObject(T object){
        if(freeObject.size() < maxSize){
            freeObject.add(object);
        }
    }
}
