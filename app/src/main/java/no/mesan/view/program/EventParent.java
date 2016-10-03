package no.mesan.view.program;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class EventParent implements ParentObject {

    private String title;
    private List<Object> childrenList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        this.childrenList = list;
    }

}
