package com.ao.notesapp.database.daos;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ao.notesapp.database.model.Note;

import java.util.List;

@Dao
public interface NotesDaos {

	@Insert
	void  addNote(Note note);

	@Delete
	void deleteNote(Note note);

	@Update
	void updateNote(Note note);

	@Query("select * from Note where id=:id")
	Note SearchNote(int id );

	@Query("select * from Note ")
	List <Note> getAllNotes();

}
