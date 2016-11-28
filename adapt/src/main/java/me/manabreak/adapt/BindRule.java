package me.manabreak.adapt;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * A base class for all the bind rules.
 * <p>
 * A bind rule declares how to populate the given layout
 * with the given item.
 *
 * @param <T> Type of the item
 */
public abstract class BindRule<T> {

    /**
     * The 'root' view of the layout
     */
    @NonNull protected View itemView;

    /**
     * Sets the item view; called internally.
     *
     * @param itemView to set as the root view
     */
    void setItemView(@NonNull View itemView) {
        this.itemView = itemView;
    }

    /**
     * Invoked once when the view holder is created.
     * <p>
     * This method should be used to initialize the
     * views in the bind rule, as well as any other
     * initializations that should be done just once.
     */
    public abstract void init();

    /**
     * Invoked whenever a new item is bound to the
     * view holder.
     * <p>
     * This method should be used to set the views
     * accordingly.
     *
     * @param item to bind
     */
    public abstract void bind(@NonNull T item);
}
