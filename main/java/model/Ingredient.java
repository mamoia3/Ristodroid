package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private int id;
    private String name;
    private List<IngredientDish> ingredientDishes;


    public Ingredient(int id, String name, List<IngredientDish> ingredientDishes) {
        this.id = id;
        this.name = name;
        this.ingredientDishes = ingredientDishes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<IngredientDish> getIngredientDishes() {
        return ingredientDishes;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
