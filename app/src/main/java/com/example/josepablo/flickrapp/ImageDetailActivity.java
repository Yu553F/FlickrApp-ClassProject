package com.example.josepablo.flickrapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageDetailActivity extends AppCompatActivity {

    private static final String DEFAULT_AUTHOR_ICON = "https://www.flickr.com/images/buddyicon.gif";

    private ImageView ivImageMed;   //ImageViewer used to display main Image
    private ImageView ivAuthorIcon; //ImageViewer used to display author icon
    private TextView tvTitle;       //TextView to display the image's title
    private TextView tvAuthorName;  //TextView to display the image's author name
    private TextView tvDescription; //TextView to display the image's description (if any is found)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        getImageDetails();

    }

    private void getImageDetails(){
        Intent intent = getIntent();

        String id = intent.getStringExtra(ImageGridActivity.IMG_ID);
        final String img_url = intent.getStringExtra(ImageGridActivity.IMG_URL);

        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=" + CategoriesActivity.FLICKR_API_KEY + "&photo_id=" + id + "&format=json&nojsoncallback=1";
        Log.i("UrlGet", "Get url: " + url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject jsonObj = response.getJSONObject("photo");

                            // Image Text Attributes to Display
                            String title = jsonObj.getJSONObject("title").getString("_content");
                            String description = jsonObj.getJSONObject("description").getString("_content");

                            // Image Author Attributes, for displaying profile picture and username in Activity
                            String author_name = jsonObj.getJSONObject("owner").getString("username");
                            String nsid = jsonObj.getJSONObject("owner").getString("nsid");
                            String iconserver = jsonObj.getJSONObject("owner").getString("iconserver");
                            int iconfarm = jsonObj.getJSONObject("owner").getInt("iconfarm");

                            tvTitle = (TextView) findViewById(R.id.tvTitle);
                            tvTitle.setText(title);

                            tvAuthorName = (TextView) findViewById(R.id.tvAuthorName);
                            tvAuthorName.setText(author_name);

                            tvDescription = (TextView) findViewById(R.id.tvDescription);

                            // Style description from HTML string.
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                tvDescription.setText(Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY));
                            } else {
                                tvDescription.setText(Html.fromHtml(description));
                            }

                            ivImageMed = (ImageView) findViewById(R.id.ivImageMed);
                            Picasso.with(getApplicationContext()).load(img_url).error(getDrawable(R.drawable.image_load_error)).into(ivImageMed);

                            ivAuthorIcon = (ImageView) findViewById(R.id.ivAuthorIcon);
                            // Display default author icon if no custom avatar is found on flickr (iconfarm = 0)
                            if(iconfarm == 0){
                                Picasso.with(getApplicationContext()).load(DEFAULT_AUTHOR_ICON).error(getDrawable(R.drawable.image_load_error)).into(ivAuthorIcon);
                            } else {
                                String author_icon = "http://farm" + iconfarm + ".staticflickr.com/" + iconserver + "/buddyicons/" + nsid + ".jpg";
                                Picasso.with(getApplicationContext()).load(author_icon).error(getDrawable(R.drawable.image_load_error)).into(ivAuthorIcon);
                            }

                        } catch (JSONException jse){
                            Toast.makeText(ImageDetailActivity.this, "Error Parsing data.\nPlease go back and try again.", Toast.LENGTH_SHORT).show();
                            Log.e("JSONGet-Grid", "JSONObjectError", jse);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ImageDetailActivity.this, "Error Fetching contents.\nPlease go back and try again.", Toast.LENGTH_SHORT).show();
                        Log.e("JSONGet-IDs", "JSONObjectRequestError", error);
                    }
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
