package com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion;

public interface OnMoveAndSwipedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position, int direction);
}
