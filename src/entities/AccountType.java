package entities;

public enum AccountType {
    SAVINGS_ACCOUNT (1),
    CHEKING_ACCOUNT (2);

    private final int type;

    AccountType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
