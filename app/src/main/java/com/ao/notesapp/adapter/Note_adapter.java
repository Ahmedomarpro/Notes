package com.ao.notesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ao.notesapp.R;
import com.ao.notesapp.database.model.Note;

import java.util.List;

public class Note_adapter extends RecyclerView.Adapter<Note_adapter.ViewHolder> {

	public Note_adapter(List<Note> noteList) {
		this.noteList = noteList;
	}

	List<Note> noteList;
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);

		return new ViewHolder(view);

	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Note note = noteList.get(position);

		holder.title.setText(note.getTitle());
		holder.time.setText(note.getTime());
		holder.content.setText(note.getContent());

	}
	public void  changeList(List<Note>notes){
		this.noteList = notes;
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		if (noteList != null)
			return noteList.size();
		else return 0;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView title,time,content;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title);
			time = itemView.findViewById(R.id.time);
			content = itemView.findViewById(R.id.content);


		}
	}
}
