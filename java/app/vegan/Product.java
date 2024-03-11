package app.vegan;

public class Product {
    private String id;
    private String name;
    private final String description;
    private String photo;
    private final int stock;
    private final String price;
    private final int img;

    public Product( String ident, String n, String d, int s, String p, int i , String phot){
        id = ident;
        name = n;
        photo = phot;
        description = d;
        stock = s;
        price = p;
        img = i;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
