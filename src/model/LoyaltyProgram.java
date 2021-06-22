package model;


import java.util.List;


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


}


