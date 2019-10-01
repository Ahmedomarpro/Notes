package com.ao.notesapp;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.ao.notesapp.adapter.Note_adapter;
import com.ao.notesapp.database.RunDatabase;
import com.ao.notesapp.database.model.Note;
import com.ao.notesapp.note_Add.Add_notes;
import com.ao.notesapp.touchHelper.ItemMoveCallback;
import com.ao.notesapp.touchHelper.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Note_adapter.StartDragListener {

	private AppBarConfiguration mAppBarConfiguration;

	Note_adapter adapter;
	RecyclerView recyclerView;
	RecyclerView.LayoutManager layoutManager;
	CoordinatorLayout coordinatorLayout;
	ItemTouchHelper touchHelper;
	   Note_adapter.StartDragListener mStartDragListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = findViewById(R.id.fab);
		recyclerView = findViewById(R.id.recyc);
		OnItemTounchHelper();
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();*/

				startActivity( new Intent(MainActivity.this, Add_notes.class));

			}
		});

		viewRecyclerData();





		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		mAppBarConfiguration = new AppBarConfiguration.Builder(
				R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
				R.id.nav_tools, R.id.nav_share, R.id.nav_send)
				.setDrawerLayout(drawer)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);


	}

	private void viewRecyclerData() {
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		adapter = new Note_adapter(null,mStartDragListener);


		recyclerView.setAdapter(adapter);

		ItemTouchHelper.Callback callback =
				new ItemMoveCallback(adapter);
		touchHelper = new ItemTouchHelper(callback);
		touchHelper.attachToRecyclerView(recyclerView);




	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		return NavigationUI.navigateUp(navController, mAppBarConfiguration)
				|| super.onSupportNavigateUp();
	}

	@Override
	protected void onStart() {
		super.onStart();

		List<Note> notesList =  RunDatabase.getInstance(this)
				.notesDaos()
				.getAllNotes();
		adapter.changeList(notesList);
 	}


	private void OnItemTounchHelper(){


		coordinatorLayout = findViewById(R.id.coordinatorLayout);
		final SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
				final int position = viewHolder.getAdapterPosition();
				final Note item = adapter.getNoteDtat().get(position);

				RunDatabase.getInstance(MainActivity.this)
						.notesDaos()
						.deleteNote(item);



				//adapter.notifyItemRemoved(position);
				adapter.removeItem(position);







				Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item was removed from the list....", Snackbar.LENGTH_LONG);
				snackbar.setAction("UNDO", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						adapter.restoreItem(item, position);
						recyclerView.scrollToPosition(position);


					}
				});
				snackbar.setActionTextColor(Color.YELLOW);
				snackbar.show();

			}

		};
		ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
		itemTouchhelper.attachToRecyclerView(recyclerView);





	}


	@Override
	public void requestDrag(RecyclerView.ViewHolder viewHolder) {
		touchHelper.startDrag(viewHolder);

	}
}
