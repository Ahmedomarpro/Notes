package com.ao.notesapp.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ao.notesapp.MainActivity;
import com.ao.notesapp.R;

public class splash_screen extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	setTheme(R.style.splashScreenTheme);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
