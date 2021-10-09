package FishingGame.Item.Equipment;

import FishingGame.Item.Item;

public class Equipment extends Item {
    private int statValue;
    public Equipment(String name, long price, int statValue) {
        super(name, price);
        this.statValue = statValue;
    }

    public int getStatValue() {
        return statValue;
    }

    public void setStatValue(int statValue) {
        this.statValue = statValue;
    }

    @Override
    public String toString() {
        return getName() + ", 가격 : " + getPrice();
    }
}
