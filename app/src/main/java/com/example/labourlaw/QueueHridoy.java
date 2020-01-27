package com.example.labourlaw;

import java.util.ArrayList;

public class QueueHridoy<T> {
    ArrayList<T> queue=new ArrayList<>();
    public void insertNew(T object){
        queue.add(object);
    }
    public T getFirst(){
        T temp=queue.get(0);
        return temp;
    }
    public T getFirstAndPop(){
        T temp=queue.get(0);
        queue.remove(0);
        return temp;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void clear(){
        queue.clear();
    }
}
