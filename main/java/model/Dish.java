package model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {

    private int id;
    private String name;
    private String description;
    private double price;
    private Blob photo;
    private Category category;
    private List<IngredientDish> ingredientDishes;
    private List<AllergenicDish> allergenicDishes;
    private List<OrderDetail> orderDetails;
    private List<Availability> availabilities;

    public Dish(int id, String name, String description, double price, Blob photo, Category category,
                List<IngredientDish> ingredientDishes, List<AllergenicDish> allergenicDishes,
                List<OrderDetail> orderDetails, List<Availability> availabilities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.category = category;
        this.ingredientDishes = ingredientDishes;
        this.allergenicDishes = allergenicDishes;
        this.orderDetails = orderDetails;
        this.availabilities = availabilities;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Blob getPhoto() {
        return photo;
    }

    public Category getCategory() {
        return category;
    }

    public List<IngredientDish> getIngredientDishes() {
        return ingredientDishes;
    }

    public List<AllergenicDish> getAllergenicDishes() {
        return allergenicDishes;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", photo=" + photo +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
