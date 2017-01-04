package me.manabreak.adapt;

import android.support.annotation.NonNull;

public interface OnClick<R extends BindRule<T>, T> {
    void onClick(@NonNull R rule, @NonNull T item);
}
