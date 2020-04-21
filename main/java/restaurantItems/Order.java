package restaurantItems;

import java.util.TreeSet;
import java.time.LocalDate;

public class Order {

    private int id;
    private static int count = 0;
    private String dateTime;
    private TreeSet<Dish> order;
    private int table;

    public Order(int table) {
        this.id = count++;
        this.dateTime = LocalDate.now().toString();
        this.order = new TreeSet<>();
        if (this.table > 0) {
            this.table = table;
        }
    }

    public int getId() {
        return id;
    }

    public static int getCount() {
        return count;
    }


    public String getDateTime() {
        return dateTime;
    }


    public TreeSet<Dish> getOrder() {
        return order;
    }


    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateTime='" + dateTime + '\'' +
                ", order=" + order +
                ", table=" + table +
                '}';
    }
}
