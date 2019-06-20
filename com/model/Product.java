package model;

public class Product {


    private String name;

    private Double price;

    private Integer type;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
        this.type=1;
    }

    public Product(String name, Double price, Integer type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
