package com.example.playandroid.entities;

import java.io.Serializable;

public class KnowledgeHierarchyList implements Serializable {
    private String name;
    private String id;
    public  KnowledgeHierarchyList(String name,String id){
        this.id=id;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
