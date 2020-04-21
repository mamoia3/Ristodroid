package restaurantItems;

import exception.NegativeValueException;

public class Dish {

    private int id;
    private static int count = 0;
    private int type;
    private String name;
    private String description;
    private double price;


    public Dish(int type, String name, String description, double price) throws NegativeValueException {
        this.id = count++;
        this.type = type;
        this.name = name;
        this.description = description;
        if (this.price >= 0) {
            this.price = price;
        } else
            throw new NegativeValueException("Attenzione! Il Prezzo non può essere negativo!");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
