package me.manabreak.adapt;

import android.support.annotation.NonNull;
import android.widget.TextView;

public class StringRule extends BindRule<String> {

    private TextView text;

    @Override
    public void init() {
        text = find(android.R.id.text1);
    }

    @Override
    public void bind(@NonNull String item) {
        text.setText(item);
    }

    String getText() {
        return text.getText().toString();
    }
}
