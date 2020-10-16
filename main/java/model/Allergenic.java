package model;

import java.util.List;
import java.util.Objects;

public class Allergenic {
    private int id;
    private String name;
    private List<AllergenicDish> allergenicDishes;

    public Allergenic(int id, String name, List<AllergenicDish> allergenicDishes) {
        this.id = id;
        this.name = name;
        this.allergenicDishes = allergenicDishes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AllergenicDish> getAllergenicDishes() {
        return allergenicDishes;
    }

    @Override
    public String toString() {
        return "Allergenic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allergenic)) return false;
        Allergenic that = (Allergenic) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
