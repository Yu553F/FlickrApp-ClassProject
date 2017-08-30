package com.example.josepablo.flickrapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jose Pablo on 8/28/2017.
 */

public class CategoryAdapter extends ArrayAdapter {
    private ImageView ivIcon;
    private TextView tvText;

    public CategoryAdapter(@NonNull Context context, ArrayList<Category> list){
        super(context, R.layout.category, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        //Definir LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        //Asociar LayoutInflater a la vista custom
        View customView = layoutInflater.inflate(R.layout.category, parent, false);

        //Obtener el elemento del arreglo de la posicion
        Category item = (Category) getItem(position);

        //Definir TextView
        tvText = (TextView) customView.findViewById(R.id.tvText);

        ivIcon = (ImageView) customView.findViewById(R.id.ivIcon);

        tvText.setText(item.getsText());

        try{
            InputStream iStream = getContext().getAssets().open(item.getsImage());
            Drawable d = Drawable.createFromStream(iStream, null);
            ivIcon.setImageDrawable(d);
        } catch (IOException ioe){
            Toast.makeText(getContext(), "Failed to load resource "+ioe, Toast.LENGTH_SHORT).show();
        }
        //ivIcon.setText(item.getDescription());

        return customView;
    }
}
