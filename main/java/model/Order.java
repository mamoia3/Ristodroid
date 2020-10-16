package model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private Timestamp time;
    private Table table;
    private List<OrderDetail> orderDetails;

    public Order(int id, Timestamp time, Table table, List<OrderDetail> orderDetails) {
        this.id = id;
        this.time = time;
        this.table = table;
        this.orderDetails = orderDetails;
    }

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", time=" + time +
                ", table=" + table +
                ", orderDetails=" + orderDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
