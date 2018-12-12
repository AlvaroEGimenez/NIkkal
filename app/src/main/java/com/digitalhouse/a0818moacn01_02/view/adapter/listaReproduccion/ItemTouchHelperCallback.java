package com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private OnMoveAndSwipedListener moveAndSwipedListener;

    public ItemTouchHelperCallback(OnMoveAndSwipedListener listener) {
        this.moveAndSwipedListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        moveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition(), direction);
    }
}
