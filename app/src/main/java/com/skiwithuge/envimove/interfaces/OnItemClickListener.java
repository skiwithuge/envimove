package com.skiwithuge.envimove.interfaces;

/**
 * Created by skiwi on 3/12/18.
 */

public interface OnItemClickListener<T> {
    void onItemClick(T item);
    void onFavClick(T item);
}
