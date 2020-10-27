package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetail {
    private static int COUNT = 0;
    private int id;
    private Order order;
    private Dish dish;
    private int quantity;
    private List<Variation> variationPlusList;
    private List<Variation> variationMinusList;


    public OrderDetail(Order order, Dish dish, int quantity) {
        this.id = ++COUNT;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
        this.variationPlusList = new ArrayList<>();
        this.variationMinusList = new ArrayList<>();
    }

    public void setVariationPlusList(List<Variation> variationPlusList) {
        this.variationPlusList = variationPlusList;
    }

    public void setVariationMinusList(List<Variation> variationMinusList) {
        this.variationMinusList = variationMinusList;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Variation> getVariationPlusList() {
        return variationPlusList;
    }

    public List<Variation> getVariationMinusList() {
        return variationMinusList;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order=" + order +
                ", dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail that = (OrderDetail) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
