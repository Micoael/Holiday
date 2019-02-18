package com.imzgw.holiday.db;


import java.util.Date;

public class Holiday {
    Date startDate;
    Date endDate;
    String name;
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public String getName() {
        return name;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Holiday(Date startDate, Date endDate, String name) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

}
