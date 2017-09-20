package com.rfe_contracts.countdown.CounterDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Counters")
public class CounterEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public final long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "archive")
    public boolean archive;

//    "CREATE TABLE " + COUNTER_TABLE_NAME + "(" +
//    COUNTER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//    COUNTER_COLUMN_NAME + " TEXT, " +
//    COUNTER_COLUMN_DATE + " INTEGER, " +
//    COUNTER_COLUMN_CATEGORY + " TEXT, " +
//    COUNTER_COLUMN_DESC + " TEXT, " +
//    COUNTER_COLUMN_LOCATION + " TEXT, " +
//    COUNTER_COLUMN_NOTE + " TEXT, " +
//    COUNTER_COLUMN_ARCHIVE + " INTEGER)"


    public CounterEntity(long id, String name, Date date, String category, String desc, String location, String note, boolean archive) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.category = category;
        this.desc = desc;
        this.location = location;
        this.note = note;
        this.archive = archive;
    }

    public static CounterEntityBuilder builder(){
        return new CounterEntityBuilder();
    }


    public static class CounterEntityBuilder {
        private long id;
        private String name;
        private Date date;
        private String category;
        private String desc;
        private String location;
        private String note;
        private boolean archive;

        public CounterEntityBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public CounterEntityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CounterEntityBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public CounterEntityBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public CounterEntityBuilder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public CounterEntityBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public CounterEntityBuilder setNote(String note) {
            this.note = note;
            return this;
        }

        public CounterEntityBuilder setArchive(boolean archive) {
            this.archive = archive;
            return this;
        }

        public CounterEntity build() {
            return new CounterEntity(id,name,date,category,desc,location,note,archive);
        }

    }

    @Override
    public String toString() {
        return "CounterEntityBuilder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", desc='" + desc + '\'' +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                ", archive=" + archive +
                '}';
    }


}
