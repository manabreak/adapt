package me.manabreak.adapt;

import android.support.annotation.NonNull;

public interface OnItemBoundCallback<T, S extends BindRule<T>> {
    void itemBound(@NonNull S rule, @NonNull T item);
}
