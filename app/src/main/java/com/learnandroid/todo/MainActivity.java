package com.learnandroid.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ItemStore store;
    // holds reference to
    private ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store = new ItemStore();
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, store.getItems());
        store.setAdapter(itemsAdapter);
        initListView(itemsAdapter);
    }

    /* Invoked when the user taps the Add Button*/
    public void addItem(View view) {
        EditText txtAdd = (EditText) findViewById(R.id.txtAdd);
        String txt = txtAdd.getText().toString();
        store.addItem(txt);
        txtAdd.setText("");
        Toast.makeText(getApplicationContext(), "Item added to list.", Toast.LENGTH_SHORT).show();
    }

    private void initListView(ArrayAdapter<String> adapter) {
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(adapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                boolean removeResult = store.removeItem(position);
                if (removeResult) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Item removed from list at position "+ position,
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Failed to remove item from list at position "+ position,
                            Toast.LENGTH_SHORT).show();
                }
                return removeResult;
            }
        });
    }

    /***
     * Gives API to list, add and delete items.
     */
    public class ItemStore {
        private ArrayList<String> items;
        private ArrayAdapter<String> itemsAdapter;
        public ItemStore() {
            items = new ArrayList<>();
            items.add("Get Milk!");
            items.add("Eat Banana");
        }

        public ArrayList<String> getItems() {
            return items;
        }

        public void setAdapter(ArrayAdapter<String> adapter) {
            itemsAdapter = adapter;
        }

        public void addItem(String item) {
            items.add(item);
            itemsAdapter.notifyDataSetChanged();
        }

        public boolean removeItem(int position) {
            items.remove(position);
            itemsAdapter.notifyDataSetChanged();
            return true;
        }

    }
}
