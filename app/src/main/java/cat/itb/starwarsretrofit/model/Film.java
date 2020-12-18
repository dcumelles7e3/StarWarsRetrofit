package cat.itb.starwarsretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Film {
    private String title;
    @SerializedName("opening_crawl")
    private String opening;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }
}
