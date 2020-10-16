package model;


import java.util.List;
import java.util.Objects;

public class Category {

    private int id;
    private String name;
    private List<Dish> dishes;
    private List<CategoryVariation> categoryVariations;


    public Category(int id, String name, List<Dish> dishes, List<CategoryVariation> categoryVariations) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
        this.categoryVariations = categoryVariations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<CategoryVariation> getCategoryVariations() {
        return categoryVariations;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
