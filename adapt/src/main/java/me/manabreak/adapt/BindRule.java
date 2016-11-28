package me.manabreak.adapt;

import android.support.annotation.NonNull;
import android.view.View;

public abstract class BindRule<T> {

    @NonNull protected View itemView;

    void setItemView(@NonNull View itemView) {
        this.itemView = itemView;
    }

    public abstract void init();

    public abstract void bind(@NonNull T item);
}
