package com.example.josepablo.flickrapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    public final static String INTENT_TAGS = "com.example.josepablo.flickrapp.TAGS";
    public final static String FLICKR_API_KEY = "5bc2e2b8b6e3663fa57ea170e6888c0a";
    //public final static String FLICKR_API_SECRET = "004f69dd090dbc2d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        showCategoryList();
    }

    private void showCategoryList(){
        ListView lvCategories = (ListView) findViewById(R.id.lvCategories);

        ArrayList<Category> categories = new ArrayList();
        categories.add(new Category("mariobros.png", "Mario Bros.", "mario+bros"));
        categories.add(new Category("minecraft.png", "Minecraft", "minecraft"));
        categories.add(new Category("overwatch.png", "Overwatch", "overwatch"));
        categories.add(new Category("pokemon.png", "Pokémon", "pokemon"));
        categories.add(new Category("starbucks.png", "Starbucks", "starbucks"));

        ArrayAdapter<Category> categoryAdapter = new CategoryAdapter(this, categories);

        addListListener(lvCategories, categoryAdapter);
    }

    private void addListListener(ListView lvCategories, ArrayAdapter<Category> categoryAdapter){
        lvCategories.setAdapter(categoryAdapter);

        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String token = ((Category) adapterView.getItemAtPosition(i)).getsTags();

                Intent intent = new Intent(CategoriesActivity.this, ImageGridActivity.class);
                intent.putExtra(INTENT_TAGS, token);

                startActivity(intent);
            }
        });
    }
}
