package model;

import java.util.Objects;

public class VariationOrder {
    private Variation variation;
    private OrderDetail orderDetail;

    public VariationOrder(Variation variation, OrderDetail orderDetail) {
        this.variation = variation;
        this.orderDetail = orderDetail;
    }

    public Variation getVariation() {
        return variation;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    @Override
    public String toString() {
        return "VariationOrder{" +
                "variation=" + variation +
                ", orderDetail=" + orderDetail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VariationOrder)) return false;
        VariationOrder that = (VariationOrder) o;
        return Objects.equals(variation, that.variation) &&
                Objects.equals(orderDetail, that.orderDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variation, orderDetail);
    }
}
