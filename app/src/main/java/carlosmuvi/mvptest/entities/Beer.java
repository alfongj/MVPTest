package carlosmuvi.mvptest.entities;

import java.io.Serializable;

/**
 * Created by Carlos on 24/11/2014.
 */
public class Beer implements Serializable{

    private int beer_id;
    private String name;
    private String category;
    private String type;
    private String image_url;

    public Beer() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeer_id() {
        return beer_id;
    }

    public void setBeer_id(int beer_id) {
        this.beer_id = beer_id;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

