package me.manabreak.adapt_dev;

import android.support.annotation.NonNull;
import android.widget.TextView;

import me.manabreak.adapt.BindRule;

public class StringRule extends BindRule<String> {

    private TextView text;

    @Override
    public void init() {
        text = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    public void bind(@NonNull String item) {
        text.setText(item);
    }
}
