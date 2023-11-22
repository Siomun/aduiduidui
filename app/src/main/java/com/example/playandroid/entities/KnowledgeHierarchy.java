package com.example.playandroid.entities;

import java.util.ArrayList;

public class KnowledgeHierarchy {
    private String nameFirst;
    private String nameSecond;
    public ArrayList<KnowledgeHierarchyList> knowledgeHierarchyLists;

    public KnowledgeHierarchy(String nameFirst, String nameSecond,ArrayList<KnowledgeHierarchyList> knowledgeHierarchyLists){
        this.nameFirst=nameFirst;
        this.nameSecond=nameSecond;
        this.knowledgeHierarchyLists=knowledgeHierarchyLists;
    }

    public ArrayList<KnowledgeHierarchyList> getKnowledgeHierarchyLists() {
        return knowledgeHierarchyLists;
    }

    public void setKnowledgeHierarchyLists(ArrayList<KnowledgeHierarchyList> knowledgeHierarchyLists) {
        this.knowledgeHierarchyLists = knowledgeHierarchyLists;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameSecond() {
        return nameSecond;
    }

    public void setNameSecond(String nameSecond) {
        this.nameSecond = nameSecond;
    }

}
