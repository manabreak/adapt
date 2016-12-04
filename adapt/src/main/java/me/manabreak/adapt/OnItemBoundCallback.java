package me.manabreak.adapt;

import android.support.annotation.NonNull;

public interface OnItemBoundCallback<T> {
    void itemBound(@NonNull T item);
}
