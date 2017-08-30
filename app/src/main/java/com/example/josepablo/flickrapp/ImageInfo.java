package com.example.josepablo.flickrapp;

/**
 * Created by Jose Pablo on 8/28/2017.
 */

public class ImageInfo {

    /**
     * Quick way to specify the image size, can be modified to add new values. Check photo.getSizes
     * within the Flickr API documentation for more info.
     */
    public enum SIZES {
        SQUARE("_sq"),
        LARGE_SQUARE("_q"),
        MEDIUM(""),
        LARGE("_l");

        private final String text;

        private SIZES(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private String imgUrl;
    private String id;
    private String owner;
    private String title;
    private String secret;
    private String server;
    private int farm;

    public ImageInfo(String id, String owner, String title, String secret, String server, int farm) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.server = server;
        this.secret = secret;
        this.farm = farm;
        this.imgUrl = "";
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Enables, by using the parameters passed through the constructor, building of the URL in which
     * a specified SIZES enum value is located on Flickr. Tested Aug 2017.
     * @param imgSize Specifies the size of the image to use. The Default display size used by
     *                Flickr is SIZES.MEDIUM
     * @return String with the image's URL location
     */
    public String makeString(SIZES imgSize){
        return "https://farm" + this.farm + ".staticflickr.com/"+ this.server +"/"+ this.id +"_" + this.secret + imgSize.toString() + ".jpg";
    }
}
