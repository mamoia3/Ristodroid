package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controllers.Utility;

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

    public static int getTotalQuantity (List<OrderDetail> list){
        int sum = 0;
        for(int i =0; i < list.size(); i++){
            sum = sum + list.get(i).getQuantity();
        }
        return sum;
    }


    public static double getTotalPriceVariation (OrderDetail detail){
        double total = 0;
        for(int i=0; i<detail.getVariationPlusList().size();i++){
            total = total +detail.getVariationPlusList().get(i).getPrice();
        }
        return total;
    }
}
