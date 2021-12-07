package com.example.readon;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.example.readon.model.User;

import java.util.Date;
import java.util.List;

@Entity
public class Notes {
    @PrimaryKey(autoGenerate = true)
    public int notesId;

    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "days")
    public Integer days;

    @ColumnInfo(name = "months")
    public Integer months;

    @ColumnInfo(name = "years")
    public Integer years;

    @ColumnInfo(name = "hours")
    public Integer hours;

    @ColumnInfo(name = "minutes")
    public Integer minutes;

    public Notes(String desc, Integer days, Integer months, Integer years, Integer hours, Integer minutes) {
        this.desc = desc;
        this.days = days;
        this.months = months;
        this.years = years;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
