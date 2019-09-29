package com.ao.notesapp.note_Add;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ao.notesapp.R;
import com.ao.notesapp.base.BaseActivity;
import com.ao.notesapp.database.RunDatabase;
import com.ao.notesapp.database.model.Note;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

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
	final Date currentTime = Calendar.getInstance().getTime();

	//Not Null
	String chooseTime = currentTime.toString();

	@Override
	public void onClick(View view) {
		String tittleS  	= textView2.getText().toString();
		if ( tittleS.isEmpty()){

			showMessage(R.string.title_add, R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
					//finish();
				}
			},true);
			textView2.requestFocus();
			return ;
 		}
		String  contentS 	= textView3.getText().toString();
		if (contentS.isEmpty()){
			showMessage(R.string.con_add, R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					dialog.dismiss();
					//finish();
				}
			},true);
			textView3.requestFocus();
			return ;
 		}

		/*Intent i = new Intent(android.provider.Settings.ACTION_DATE_SETTINGS);
 		startActivity(i);
 		*/
		//Issues  --------------------------------------------------------------------------------


		if (view.getId() == R.id.button) {

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

			//return;


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
			},hour,minute,false);

			timePickerDialog.show();
			if (textView4 != null){
				//Toast.makeText(this, "Time", Toast.LENGTH_SHORT).show();
				textView4.setText(currentTime.toString());
				textView4.requestFocus();


			}
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

