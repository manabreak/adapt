package me.manabreak.adapt;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An easy-to-use adapter which supports multiple item types out of the box.
 */
public class Adapt extends RecyclerView.Adapter<Adapt.ViewHolder> {

    private final List<Object> items = new ArrayList<>();
    private final SparseArray<Class> bindRules = new SparseArray<>();
    private final SparseArray<Class> layoutsToTypes = new SparseArray<>();
    private final Map<Class, Integer> typesToLayouts = new HashMap<>();
    private final Map<Class, OnClick> onClicks = new HashMap<>();
    private final Map<Class, OnItemBoundCallback> onBounds = new HashMap<>();

    /**
     * Adds a new type to this adapter.
     * <p>
     * This method basically tells the adapter to "use this layout
     * when this kind of object is bound, using these rules."
     *
     * @param layout for the item
     * @param clazz  type of the item
     * @param rule   type of the rule class
     * @param <R>    Type of the rule class
     * @param <T>    Type of the item
     * @return This adapter for chaining
     */
    public <R extends BindRule<T>, T> Adapt addType(@LayoutRes int layout, @NonNull Class<T> clazz, @NonNull Class<R> rule) {
        bindRules.put(layout, rule);
        typesToLayouts.put(clazz, layout);
        layoutsToTypes.put(layout, clazz);
        return this;
    }

    /**
     * Constructs a new view holder.
     *
     * @param parent   of the view
     * @param viewType layout used for the view
     * @return A newly created view holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class ruleClass = bindRules.get(viewType);
        if (ruleClass != null) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            //noinspection unchecked
            return new ViewHolder<>(itemView, ruleClass, layoutsToTypes.get(viewType));
        }
        throw new IllegalStateException("No rules added for viewType " + viewType);
    }

    /**
     * Binds the item at the given position to the given view holder.
     *
     * @param holder   to bind the item to
     * @param position of the item
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(items.get(position));
    }

    /**
     * Retrieves the total count of items.
     *
     * @return numer of items
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Retrieves the registered layout for the item at 'position'.
     *
     * @param position of the item
     * @return layout for the item
     */
    @Override
    public int getItemViewType(int position) {
        return typesToLayouts.get(items.get(position).getClass());
    }

    /**
     * Adds a single item to this adapter.
     *
     * @param item to add
     */
    public void add(@NonNull Object item) {
        //noinspection unchecked
        items.add(item);
    }

    /**
     * Adds all the items to this adapter.
     *
     * @param items to add
     */
    public void add(@NonNull Object... items) {
        Collections.addAll(this.items, items);
    }

    /**
     * Adds all the items from the given collection to this adapter.
     *
     * @param items to add
     */
    public void add(@NonNull Collection<?> items) {
        //noinspection unchecked
        this.items.addAll(items);
    }

    /**
     * Clears all the items added to this adapter.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Sets the items this adapter holds to the given items.
     *
     * @param items to set
     */
    public void set(@NonNull Object... items) {
        this.items.clear();
        Collections.addAll(this.items, items);
    }

    /**
     * Sets the items this adapter holds to the items held by the given collection.
     *
     * @param items to set
     */
    public void set(@NonNull Collection<?> items) {
        this.items.clear();
        //noinspection unchecked
        this.items.addAll(items);
    }

    /**
     * Sets an OnClick listener for the given type.
     *
     * @param clazz   type of the item
     * @param onClick callback to invoke when the item is clicked
     * @param <T>     Type of the item
     */
    public <T> void onClick(@NonNull Class<T> clazz, @NonNull OnClick<T> onClick) {
        onClicks.put(clazz, onClick);
    }

    public <T> void onItemBound(@NonNull Class<T> clazz, @NonNull OnItemBoundCallback<T> callback) {
        onBounds.put(clazz, callback);
    }

    /**
     * The single view holder class used with this adapter.
     *
     * @param <R> Type of the BindRule
     * @param <T> Type of the item
     */
    class ViewHolder<R extends BindRule<T>, T> extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final R rule;
        @NonNull private final Class<T> itemClass;
        private T boundItem = null;

        ViewHolder(@NonNull View itemView, @NonNull Class<R> ruleClass, @NonNull Class<T> itemClass) {
            super(itemView);
            this.itemClass = itemClass;
            R rule = null;
            try {
                rule = ruleClass.newInstance();
                rule.itemView = itemView;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (rule == null) throw new IllegalStateException("Rule is null");
            this.rule = rule;
            this.rule.init();
            itemView.setOnClickListener(this);
        }

        void bind(T item) {
            boundItem = item;
            rule.bind(item);
            if (onBounds.containsKey(itemClass)) {
                //noinspection unchecked
                onBounds.get(itemClass).itemBound(item);
            }
        }

        /**
         * Retrieves the BindRule associated with this view holder.
         * <p>
         * Should only be called from the tests.
         *
         * @return The BindRule set for this view holder
         */
        @VisibleForTesting
        R getBindRule() {
            return rule;
        }

        @Override
        public void onClick(@NonNull View view) {
            if (onClicks.containsKey(itemClass)) {
                //noinspection unchecked
                onClicks.get(itemClass).onClick(boundItem);
            }
        }
    }
}
