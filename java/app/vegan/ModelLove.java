package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelLove {
    private String love, brand, name, foto, id, shopuid, useruid;

    public ModelLove() {

    }

    public ModelLove(String love) {
        this.love = love;
        this.name = name;
        this.brand = brand;
        this.id = id;
        this.shopuid = shopuid;
        this.useruid = useruid;
        this.foto = foto;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopuid() {
        return shopuid;
    }

    public void setShopuid(String shopuid) {
        this.shopuid = shopuid;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }
}
