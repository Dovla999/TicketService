package model;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public enum LoyaltyProgram {
    INSTANCE;




    private List<LoyaltyCategory> categories;

    LoyaltyProgram() {
    }

    LoyaltyProgram(List<LoyaltyCategory> categories) {
        this.categories = categories;
    }

    public List<LoyaltyCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<LoyaltyCategory> categories) {
        this.categories = categories;
    }

    public LoyaltyCategory getLoyaltyCategoryByPoints(Integer points) {

        var cats = categories.stream()
                .filter(loyaltyCategory -> loyaltyCategory.getPoints() <= points)
                .collect(Collectors.toList());
        return cats.stream().max(Comparator.comparing(LoyaltyCategory::getPoints))
                .get();


    }
}


