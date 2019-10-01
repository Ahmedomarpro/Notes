package com.ao.notesapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ao.notesapp.R;
import com.ao.notesapp.database.model.Note;
import com.ao.notesapp.touchHelper.ItemMoveCallback;

import java.util.Collections;
import java.util.List;

public class Note_adapter extends RecyclerView.Adapter<Note_adapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {
	List<Note> noteList;
	public final StartDragListener mStartDragListener;

	/*public Note_adapter(StartDragListener mStartDragListener) {
		this.mStartDragListener = mStartDragListener;
	}
*/


	public Note_adapter(List<Note> noteList, StartDragListener mStartDragListener) {
		this.noteList = noteList;
		//this.mStartDragListener = mStartDragListener;

		this.mStartDragListener = mStartDragListener;
	}



	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);

		return new ViewHolder(view);

	}


	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
		Note note = noteList.get(position);
		holder.title.setText(note.getTitle());
		holder.time.setText(note.getTime());
		holder.content.setText(note.getContent());

		holder.content.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
						mStartDragListener.requestDrag(holder);

				}


				return false;
			}
		});



	}
	public void  changeList(List<Note>notes){
		this.noteList = notes;
		notifyDataSetChanged();
 	}


	@Override
	public int getItemCount() {
		if (noteList == null) return  0;
			return noteList.size();

		 	}






	public   class ViewHolder extends RecyclerView.ViewHolder {
		TextView title,time,content;
		    View rowView;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			rowView = itemView;
			title = itemView.findViewById(R.id.title);
			time = itemView.findViewById(R.id.time);
			content = itemView.findViewById(R.id.content);


		}
	}

	@Override
	public void onRowMoved(int fromPosition, int toPosition) {


		if (fromPosition < toPosition) {
			for (int i = fromPosition; i < toPosition; i++) {
				Collections.swap(noteList, i, i + 1);
			}
		} else {
			for (int i = fromPosition; i > toPosition; i--) {
				Collections.swap(noteList, i, i - 1);
			}
		}
		notifyItemMoved(fromPosition, toPosition);


	}

	@Override
	public void onRowSelected(ViewHolder ViewHolder) {
		ViewHolder.rowView.setBackgroundColor(Color.GRAY);

	}

	@Override
	public void onRowClear(ViewHolder ViewHolder) {
		ViewHolder.rowView.setBackgroundColor(Color.WHITE);

	}

	public List<Note> getNoteDtat(){
		return noteList;
	}
		public List<Note> removeItem(int position){
		noteList.remove(position);
		notifyItemRemoved(position);
			notifyDataSetChanged();
		return noteList;
		}
		public void restoreItem (Note  item , int position){

			noteList.add(position, item);

			notifyItemInserted(position);

		}

	public interface StartDragListener {
		void requestDrag(RecyclerView.ViewHolder viewHolder);
	}



}
