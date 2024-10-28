package kh.edu.rupp.ite.mad_project.model;
public class HomeProducts {
    private String name;
    private String title;
    private String description;
    private double price;
    private String image;
    private boolean isFavorite;

    // Constructors, Getters, and Setters
    public HomeProducts(String name, String title, String description, double price, String image,Boolean isFavorite) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        boolean isFavorite1 = this.isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImageUrl(String image) {
        this.image = image;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
