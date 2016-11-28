package me.manabreak.adapt_dev;

import android.support.annotation.NonNull;
import android.widget.TextView;

import me.manabreak.adapt.BindRule;

public class ComplexRule extends BindRule<ComplexItem> {

    private TextView first;
    private TextView second;

    @Override
    public void init() {
        first = (TextView) itemView.findViewById(R.id.first);
        second = (TextView) itemView.findViewById(R.id.second);
    }

    @Override
    public void bind(@NonNull ComplexItem item) {
        first.setText(item.getFirst());
        second.setText(item.getSecond());
    }
}
