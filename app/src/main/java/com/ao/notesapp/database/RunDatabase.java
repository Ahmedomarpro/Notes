package com.ao.notesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ao.notesapp.database.daos.NotesDaos;
import com.ao.notesapp.database.model.Note;

@Database(entities = {Note.class},version = 1 , exportSchema = false)
public abstract class RunDatabase extends RoomDatabase {
	private static  RunDatabase dataBase;
	private final static String  NAME_DATABASE = "Notes";

	public abstract NotesDaos notesDaos();

	public static RunDatabase getInstance(Context context){

		/*
		if (dataBase == null) {
			synchronized (RunDatabase.class){
				if (dataBase == null){
					dataBase = Room.databaseBuilder(context.getApplicationContext(),RunDatabase.class,NAME_DATABASE )
							.build();

				}
			}
		}
*/



		if (dataBase==null){
			dataBase = Room.databaseBuilder(context, RunDatabase.class,NAME_DATABASE )
					.fallbackToDestructiveMigration()
					.allowMainThreadQueries()
					.build();

		}
		return dataBase;

	}




}
