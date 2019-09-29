package com.ao.notesapp.note_Add;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.ao.notesapp.R;
import com.ao.notesapp.base.BaseActivity;
import com.ao.notesapp.database.RunDatabase;
import com.ao.notesapp.database.model.Note;

import java.util.Calendar;

public class Add_notes extends BaseActivity implements View.OnClickListener {

	protected EditText textView2,textView3;
	protected  TextView textView4;
 	protected Button add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_add_notes);
		initView();
	}

	String chooseTime = "";

	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.button) {
			String tittleS  	= textView2.getText().toString();
			String  contentS 	= textView3.getText().toString();
			Note note = new Note(tittleS,contentS,chooseTime);
			RunDatabase.getInstance(this)
					.notesDaos()
					.addNote(note);
			showMessage(R.string.add, R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
					finish();
				}
			},false);



		} else if (view.getId() == R.id.textView4) {
			Calendar calendar = Calendar.getInstance();

 			final int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);

			TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					textView4.setText( hourOfDay + " : " + minute);
					chooseTime = 	hourOfDay + " : " + minute;
				}
			},hour,minute,true);

			timePickerDialog.show();

		}

	}

	private void initView() {
		textView2 =   findViewById(R.id.textView2);
		textView3 =   findViewById(R.id.textView3);
		textView4 =   findViewById(R.id.textView4);
		textView4.setOnClickListener(Add_notes.this);
		add =  findViewById(R.id.button);
		add.setOnClickListener(Add_notes.this);
	}
}

