package model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import exception.NegativeSeatException;

public class Order {
    private int id;
    private Timestamp time;
    private Table table;
    private Seat seat;
    private int seatNumber;
    private List<OrderDetail> orderDetails;

    public Order(int id, Timestamp time, Table table, Seat seat, int seatNumber, List<OrderDetail> orderDetails) throws NegativeSeatException {
        this.id = id;
        this.time = time;
        this.table = table;
        this.seat = seat;
        if(seatNumber <= 0) {
            this.seatNumber = seatNumber;
        } else throw new NegativeSeatException("Inserire un numero di coperti valido!");
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

    public Seat getSeat() {
        return seat;
    }

    public int getSeatNumber() {
        return seatNumber;
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
