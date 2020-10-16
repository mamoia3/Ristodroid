package model;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OrderDetail {
    private int id;
    private Order order;
    private Dish dish;
    private int quantity;
    private List<VariationOrder> variationOrderList;

    public OrderDetail(int id, Order order, Dish dish, int quantity, List<VariationOrder> variationOrderList) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
        this.variationOrderList = variationOrderList;
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

    public List<VariationOrder> getVariationOrderList() {
        return variationOrderList;
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
