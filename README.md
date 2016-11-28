# Adapt

Adapt is a RecyclerView adapter which supports any number
of items in a single list out-of-the-box.

## Install

In your repositories, add the following:

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

To get the latest, bleeding-edge version, add this
to your dependencies:

```
dependencies {
    compile 'com.github.manabreak:adapt:-SNAPSHOT'
}
```

## Usage

The adapter is created by calling its constructor:

```
Adapt adapter = new Adapt();
recyclerView.setAdapter(adapter);
```

Next, add all the item types you want to use:

```
adapter.addType(R.layout.string_item, String.class, StringRule.class);
adapter.addType(R.layout.complex_item, ComplexItem.class, ComplexRule.class);
```

The first parameter in `addType()` is always the layout you
want to use, the second parameter is the type of the item
you want to show in the list, and the third one is a special
"rule" class which dictates how the data should be bound from
the item to the views. For example, the `StringRule` would
look something like this:

```
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
```

And you're good to go! Let's add some items:

```
adapter.add("Hello, world!");
adapter.add(new ComplexItem("Foo", "Bar"));
adapter.notifyDataSetChanged();
```

You can find a complete example 
[here](https://github.com/manabreak/adapt/blob/master/app/src/main/java/me/manabreak/adapt_dev/MainActivity.java).


## Why Adapt?

When writing adapters, you'll soon notice you're
repeating the same boilerplate over and over again. With
Adapt, you don't need to care about that anymore.

When dealing with multiple item types in a single adapter,
it tends to get messy, adding a new item type, and a new view holder.
With Adapt, it's just a single line of code to add a new
item type, plus the view binding rules.

The third reason is re-use. You can easily re-use the
same layout with the same rule class anywhere. You simply
write your view binding rules once and re-use them anywhere
you want.

## I found a bug, how can I help?

Use the issues page to report the bug. Try to include as
much information as possible.

## I have an idea for Adapt!

Great! Use the issues page to suggest new features.

## License

MIT License

Copyright (c) 2016 manabreak

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
