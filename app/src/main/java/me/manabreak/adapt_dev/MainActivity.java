package me.manabreak.adapt_dev;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.manabreak.adapt.Adapt;
import me.manabreak.adapt.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the recycler view
        RecyclerView r = (RecyclerView) findViewById(R.id.recycler);
        r.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with two item types
        Adapt a = new Adapt();
        a.addType(R.layout.string_item, String.class, StringRule.class);
        a.addType(R.layout.complex_item, ComplexItem.class, ComplexRule.class);
        a.onClick(String.class, new OnClick<String>() {
            public static final String TAG = "OnClick";

            @Override
            public void onClick(me.manabreak.adapt.R rule, @NonNull String item) {
                Log.d(TAG, "onClick: " + item);
            }
        });
        r.setAdapter(a);

        // Add some items
        a.add("Hello world!");
        a.add("It's easy to create lists now.");
        a.add(new ComplexItem("Like...", "...this one!"));
        a.add("You can combine items just like you want.");
        a.add(new ComplexItem("And it'll behave!", "Neat!"));

        a.notifyDataSetChanged();
    }


}
