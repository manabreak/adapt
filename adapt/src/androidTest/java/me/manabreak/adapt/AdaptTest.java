package me.manabreak.adapt;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AdaptTest {

    Adapt a;

    @Before
    public void setUp() {
        a = new Adapt();
    }

    @After
    public void tearDown() {
        a = null;
    }

    @Test
    public void testViewHolderCreation() {
        int layout = android.R.layout.simple_list_item_1;
        a.addType(layout, String.class, StringRule.class);

        RecyclerView r = new RecyclerView(InstrumentationRegistry.getContext());
        r.setLayoutManager(new LinearLayoutManager(InstrumentationRegistry.getContext()));

        Adapt.ViewHolder vh = a.createViewHolder(r, layout);
        assertNotNull(vh);
        assertNotNull(vh.itemView.findViewById(android.R.id.text1));
    }

    @Test
    public void testAddSetClearItems() {
        assertEquals(0, a.getItemCount());
        a.add("Hello");
        assertEquals(1, a.getItemCount());
        a.add("World", "Morning");
        assertEquals(3, a.getItemCount());
        a.add(1, 2, 3, 4);
        assertEquals(7, a.getItemCount());
        a.clear();
        assertEquals(0, a.getItemCount());
        a.set(1, 2, 3);
        assertEquals(3, a.getItemCount());
    }

    @Test(expected = IllegalStateException.class)
    public void testNoItemTypes() {
        LinearLayout parent = new LinearLayout(InstrumentationRegistry.getContext());
        a.onCreateViewHolder(parent, 123);
    }

    @Test
    public void testOnClick() {
        final boolean[] clicked = new boolean[]{false};
        final String[] s = new String[]{null};

        int layout = android.R.layout.simple_list_item_1;
        a.addType(layout, String.class, StringRule.class);
        a.onClick(String.class, new OnClick<String>() {
            @Override
            public void onClick(@NonNull String item) {
                clicked[0] = true;
                s[0] = item;
            }
        });

        Adapt.ViewHolder vh = a.onCreateViewHolder(new LinearLayout(InstrumentationRegistry.getContext()), layout);
        vh.bind("Foo");
        vh.itemView.callOnClick();

        assertTrue(clicked[0]);
        assertEquals("Foo", s[0]);
    }

    @Test
    public void testOnItemBound() {
        int layout = android.R.layout.simple_list_item_1;
        a.addType(layout, String.class, StringRule.class);

        final String[] boundItem = new String[]{null};
        OnItemBoundCallback<String> callback = new OnItemBoundCallback<String>() {
            @Override
            public void itemBound(@NonNull String item) {
                boundItem[0] = item;
            }
        };

        a.onItemBound(String.class, callback);

        Adapt.ViewHolder vh = a.onCreateViewHolder(new LinearLayout(InstrumentationRegistry.getContext()), layout);
        vh.bind("Foo");
        assertEquals("Foo", boundItem[0]);
    }
}