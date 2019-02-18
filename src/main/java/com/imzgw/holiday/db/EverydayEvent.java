package com.imzgw.holiday.db;

import org.litepal.crud.DataSupport;

public class EverydayEvent extends DataSupport {
    private int id;
    private int type;

    //0 -> temp 1->everyday
    private String name;

    private String rule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getRule() {
        return rule;
    }
    public void setRule(String rule) {

        this.rule = rule;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
//    public EverydayEvent(int type, String name, String rule) {
//        super();
//        this.type = type;
//        this.name = name;
//        this.rule = rule;
//    }

}
