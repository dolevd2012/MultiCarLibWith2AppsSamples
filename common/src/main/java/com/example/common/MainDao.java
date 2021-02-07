

package com.example.common;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    @Delete
    void delete(MainData mainData);

    @Delete
    void deleteAll(List<MainData> mainData);

    @Query("UPDATE DataB SET INT= :newInfo WHERE ID= :sID")
    void updateNumber(int sID,int newInfo);

    @Query("SELECT * FROM DataB WHERE ID= :sID")
    MainData getSpecificDataBysID(int sID);


    @Query("SELECT * FROM DataB WHERE INT= :num")
    MainData getSpecificDataBysNumber(int num);


}

