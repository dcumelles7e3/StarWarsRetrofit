package cat.itb.starwarsretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Starship {
    private String name, manufacturer;
    @SerializedName("max_atmosphering_speed")
    private String speed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
