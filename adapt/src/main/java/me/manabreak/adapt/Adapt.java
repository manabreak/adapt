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

public class Adapt extends RecyclerView.Adapter<Adapt.ViewHolder> {

    private final List<Object> items = new ArrayList<Object>();
    private final SparseArray<Class> bindRules = new SparseArray<>();
    private final Map<Class, Integer> typesToLayouts = new HashMap<>();

    public <R extends BindRule<T>, T> Adapt addType(@LayoutRes int layout, @NonNull Class<T> clazz, @NonNull Class<R> rule) {
        bindRules.put(layout, rule);
        typesToLayouts.put(clazz, layout);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class ruleClass = bindRules.get(viewType);
        if (ruleClass != null) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new ViewHolder<>(itemView, ruleClass);
        }
        throw new IllegalStateException("No rules added for viewType " + viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return typesToLayouts.get(items.get(position).getClass());
    }

    public void add(@NonNull Object item) {
        //noinspection unchecked
        items.add(item);
    }

    public void add(@NonNull Object... items) {
        Collections.addAll(this.items, items);
    }

    public void add(@NonNull Collection<?> items) {
        //noinspection unchecked
        this.items.addAll(items);
    }

    public void clear() {
        items.clear();
    }

    public void set(@NonNull Object... items) {
        this.items.clear();
        Collections.addAll(this.items, items);
    }

    public void set(@NonNull Collection<?> items) {
        this.items.clear();
        //noinspection unchecked
        this.items.addAll(items);
    }

    class ViewHolder<R extends BindRule<T>, T> extends RecyclerView.ViewHolder {

        private final R rule;

        ViewHolder(@NonNull View itemView, @NonNull Class<R> ruleClass) {
            super(itemView);
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
        }

        void bind(T item) {
            rule.bind(item);
        }

        @VisibleForTesting
        R getBindRule() {
            return rule;
        }
    }
}
