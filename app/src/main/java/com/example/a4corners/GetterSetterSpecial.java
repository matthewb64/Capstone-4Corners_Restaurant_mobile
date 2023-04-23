package com.example.a4corners;

public class GetterSetterSpecial {
    String id;
    String name;
    String image;
    String price;
    String Description;

    public GetterSetterSpecial(String id, String name, String image, String price, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        Description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
