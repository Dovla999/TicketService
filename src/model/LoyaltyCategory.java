package model;

public class LoyaltyCategory {
    private String name;
    private Integer points;
    private Double discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LoyaltyCategory(String name, Integer points, Double discount) {
        this.name = name;
        this.points = points;
        this.discount = discount;
    }

    public LoyaltyCategory() {
    }
}
