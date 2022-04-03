package com.api.twitter;

import javax.swing.plaf.LabelUI;
import java.util.ArrayList;
import java.util.List;

public class StreamRuleRequest {

    private List<StreamRule> addList = new ArrayList<>();

    public List<StreamRule> getAddList() {
        return addList;
    }

    public void setAddList(List<StreamRule> addList){
        this.addList = addList;
    }

    public void addRule(String value, String tag) {
        this.addList.add(new StreamRule(value, tag));
    }

}
