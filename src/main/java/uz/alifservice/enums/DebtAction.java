package uz.alifservice.enums;

public enum DebtAction {

    GAVE("I gave"),  // Men berdim
    RECEIVED("I received"),  // Men oldim
    TOOK("I took"),  // Men oldim
    REPAID("I repaid");// Men qaytardim

    private final String description;

    DebtAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
