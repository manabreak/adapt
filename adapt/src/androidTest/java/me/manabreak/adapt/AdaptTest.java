package me.manabreak.adapt;

import android.support.test.InstrumentationRegistry;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
}