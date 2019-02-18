package com.imzgw.holiday.util;

import com.imzgw.holiday.db.EverydayEvent;
import com.imzgw.holiday.db.Holiday;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    static long aDay=1000*60*24*60;
    public static Boolean judgeIsShowToday(EverydayEvent event,int today){

        //give you an instant
        if(event.getRule()!=null){

            return getShowDate(event.getRule(), today);
        }else{
            return false;
        }

    }

    //Get back an boolean value that indicates whether it is going
    //to show today or not.
    private static Boolean getShowDate(String str,int today) {
        String[] ruleText  = new String[3];
        try {
            ruleText = str.split("-");

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        int offset=Integer.parseInt(ruleText[0]);
        int interval=Integer.parseInt(ruleText[1]);
        int duration=Integer.parseInt(ruleText[2]);
        switch (ruleText.length) {
            case 3:
                return ((today-offset)%interval)<=duration;
            case 5:
                int startTime=Integer.parseInt(ruleText[3]);
                int endTime = Integer.parseInt(ruleText[4]);
                return ((today-offset)%interval)<=duration && today>=startTime && today<=endTime;

            default:
                return false;
        }

    }
    public static boolean jugdeIsIlleegal(String str){
        String[] ruleText  = new String[3];
        try {
            ruleText = str.split("-");

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        switch (ruleText.length) {
            case 3:
                return true;
            case 5:
                return true;
            default:
                return false;
        }
    }


    public static List<EverydayEvent> readStack() {
        //TODO:fetch data from database
        List<EverydayEvent> list= DataSupport.findAll(EverydayEvent.class);

        return list;
    }

    public static String showText(List<EverydayEvent> list, int today) {
        int i=0;
        String returnValue;
        returnValue="今天你要完成:\n";
        for (EverydayEvent everydayEvent : list) {
            if(judgeIsShowToday(everydayEvent, today)){
                i=i+1;
                //This will be modified when an android app launcher
                returnValue=returnValue+("    "+i+"."+everydayEvent.getName()+"\n");

            }
        }
        return returnValue;
    }


    public static int getTimeSpan(Holiday holiday){
        if(holiday.getStartDate()!=null && holiday.getEndDate()!=null){

            return (int)
                    (getLongTimeSpan
                            (holiday.getStartDate(),
                                    holiday.getEndDate())
                            /aDay);
        }else {
            return -1;
        }
    }

    public static int getTodayIndicator(Holiday holiday) {
        Date date = new Date(System.currentTimeMillis());
        Date startDay=holiday.getStartDate();
        Date endDay=holiday.getEndDate();
        return (int) (getLongTimeSpan(startDay, date)/aDay);

    }
    private static long getLongTimeSpan(Date d1, Date d2){
        return Math.abs(d1.getTime()-d2.getTime());
    }

    public static double getProgress(Holiday holiday) {
        double span= getTimeSpan(holiday);
        double today=getTodayIndicator(holiday);
        return (today/span)*100;

    }
}
