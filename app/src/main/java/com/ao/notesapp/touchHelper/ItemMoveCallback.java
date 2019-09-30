package com.ao.notesapp.touchHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ao.notesapp.adapter.Note_adapter;

public class ItemMoveCallback extends  ItemTouchHelper.Callback {

	private final ItemTouchHelperContract mAdapter;

	public ItemMoveCallback(ItemTouchHelperContract mAdapter) {
		this.mAdapter = mAdapter;
	}
	@Override
	public boolean isLongPressDragEnabled() {
		return true;
	}

	@Override
	public boolean isItemViewSwipeEnabled() {
		return false;
	}
	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
		return makeMovementFlags(dragFlags, 0);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
		mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

		return true;
	}

	@Override
	public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

		if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
			if (viewHolder instanceof Note_adapter.ViewHolder) {
				Note_adapter.ViewHolder ViewHolder=
						(Note_adapter.ViewHolder) viewHolder;
				mAdapter.onRowSelected(ViewHolder);
			}

		}
		super.onSelectedChanged(viewHolder, actionState);
	}
	@Override
	public void clearView (RecyclerView recyclerView,
						   RecyclerView.ViewHolder viewHolder)
	{
		super.clearView(recyclerView, viewHolder);

		if (viewHolder instanceof Note_adapter.ViewHolder) {
			Note_adapter.ViewHolder ViewHolder=
					(Note_adapter.ViewHolder) viewHolder;
			mAdapter.onRowClear(ViewHolder);
		}

	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

	}
	public interface ItemTouchHelperContract {

		void onRowMoved(int fromPosition, int toPosition);
		void onRowSelected(Note_adapter.ViewHolder ViewHolder);
		void onRowClear(Note_adapter.ViewHolder ViewHolder);

	}

}
