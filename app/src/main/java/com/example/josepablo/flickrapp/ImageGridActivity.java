package com.example.josepablo.flickrapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageGridActivity extends AppCompatActivity {

    private static String tags = "";
    private final int RESULTS_PER_PAGE = 20;
    public static final String IMG_ID = "com.example.josepablo.flickrapp.IMG_ID";
    public static final String IMG_URL = "com.example.josepablo.flickrapp.IMG_URL";
    public static final String OWNER_ID = "com.example.josepablo.flickrapp.OWNER_ID";

    private GridView gvImages;
    private GridViewAdapter gAdapterImages;
    private ArrayList<ImageInfo> lImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        Intent intent = getIntent();

        /*
         * Simple workaround to protect tag override if user goes back to this activity from an
         * ImageDetailActivity instance using the back arrow inside the UI.
         */
        String tags_temp = intent.getStringExtra(CategoriesActivity.INTENT_TAGS);
        if(tags_temp != null){
            tags = tags_temp;
        }

        gvImages = (GridView) findViewById(R.id.gvImages);

        lImages = new ArrayList<>();

        gAdapterImages = new GridViewAdapter(this, R.layout.grid_image, lImages);

        gvImages.setAdapter(gAdapterImages);

        getImageParam(tags);
    }

    private void getImageParam(String tags){

        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + CategoriesActivity.FLICKR_API_KEY + "&tags=" + tags + "&sort=relevance&per_page=" + RESULTS_PER_PAGE + "&format=json&nojsoncallback=1";
        Log.i("UrlGet", "Get url: " + url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonImgArray = response.getJSONObject("photos").getJSONArray("photo");

                            for (int i = 0; i < jsonImgArray.length(); i++) {
                                JSONObject jsonObj = jsonImgArray.getJSONObject(i);

                                String id = jsonObj.getString("id");
                                String owner = jsonObj.getString("owner");
                                String title = jsonObj.getString("title");
                                String server = jsonObj.getString("server");
                                String secret = jsonObj.getString("secret");
                                int farm = jsonObj.getInt("farm");

                                ImageInfo item = new ImageInfo(id, owner, title, secret, server, farm);
                                item.setImgUrl(item.makeString(ImageInfo.SIZES.LARGE_SQUARE));

                                lImages.add(item);

                                if(i == jsonImgArray.length() - 1){
                                    // Update adapter with newly added image resources
                                    gvImages.setAdapter(gAdapterImages);

                                    gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                                            ImageInfo item = (ImageInfo) parent.getItemAtPosition(position);

                                            Intent intent = new Intent(ImageGridActivity.this, ImageDetailActivity.class);
                                            intent.putExtra(IMG_ID, item.getId());
                                            intent.putExtra(IMG_URL, item.makeString(ImageInfo.SIZES.MEDIUM));
                                            intent.putExtra(OWNER_ID, item.getOwner());

                                            setIntent(intent);
                                            startActivity(intent);
                                        }

                                    });
                                }
                            }
                        } catch (JSONException jse){
                            Toast.makeText(ImageGridActivity.this, "Error Parsing data.\nPlease go back and try again.", Toast.LENGTH_SHORT).show();
                            Log.e("JSONGet-Grid", "JSONObjectError", jse);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ImageGridActivity.this, "Error Fetching results.\nPlease go back and try again.", Toast.LENGTH_SHORT).show();
                        Log.e("JSONGet-IDs", "JSONObjectRequestError", error);
                    }
                });

        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
    }

}
