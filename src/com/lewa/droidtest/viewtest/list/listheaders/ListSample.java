package com.lewa.droidtest.viewtest.list.listheaders;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lewa.droidtest.R;

public class ListSample extends Activity
    {

        public final static String ITEM_TITLE = "title";
        public final static String ITEM_CAPTION = "caption";

        // SectionHeaders
        private final static String[] days = new String[]{"Mon", "Tue", "Wed", "Thur", "Fri"};

        // Section Contents
        private final static String[] notes = new String[]{"Ate Breakfast", "Ran a Marathan ...yah really", "Slept all day"};

        // MENU - ListView
        private ListView addJournalEntryItem;

        // Adapter for ListView Contents
        private SeparatedListAdapter adapter;

        // ListView Contents
        private ListView journalListView;

        public Map<String, ?> createItem(String title, String caption)
            {
                Map<String, String> item = new HashMap<String, String>();
                item.put(ITEM_TITLE, title);
                item.put(ITEM_CAPTION, caption);
                return item;
            }

        @Override
        public void onCreate(Bundle icicle)
            {
                super.onCreate(icicle);

                // Sets the View Layer
                setContentView(R.layout.main);

                // Interactive Tools
                final ArrayAdapter<String> journalEntryAdapter = new ArrayAdapter<String>(this, R.layout.add_journalentry_menuitem, new String[]{"Add Journal Entry"});

                // AddJournalEntryItem
                addJournalEntryItem = (ListView) this.findViewById(R.id.add_journalentry_menuitem);
                addJournalEntryItem.setAdapter(journalEntryAdapter);
                addJournalEntryItem.setOnItemClickListener(new OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
                            {
                                String item = journalEntryAdapter.getItem(position);
                                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                            }
                    });

                // Create the ListView Adapter
                adapter = new SeparatedListAdapter(this);
                ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, R.layout.list_item, notes);

                // Add Sections
                for (int i = 0; i < days.length; i++)
                    {
                        adapter.addSection(days[i], listadapter);
                    }

                // Get a reference to the ListView holder
                journalListView = (ListView) this.findViewById(R.id.list_journal);

                // Set the adapter on the ListView holder
                journalListView.setAdapter(adapter);

                // Listen for Click events
                journalListView.setOnItemClickListener(new OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
                            {
                                String item = (String) adapter.getItem(position);
                                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                            }
                    });
            }

    }
