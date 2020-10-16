package model;

import java.util.List;
import java.util.Objects;

public class Variation {
    private int id;
    private String name;
    private double price;
    private List<CategoryVariation> categoryVariations;
    private List<VariationOrder> variationOrders;

    public Variation(int id, String name, double price, List<CategoryVariation> categoryVariations, List<VariationOrder> variationOrders) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryVariations = categoryVariations;
        this.variationOrders = variationOrders;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<CategoryVariation> getCategoryVariations() {
        return categoryVariations;
    }

    public List<VariationOrder> getVariationOrders() {
        return variationOrders;
    }

    @Override
    public String toString() {
        return "Variation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variation)) return false;
        Variation variation = (Variation) o;
        return id == variation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
