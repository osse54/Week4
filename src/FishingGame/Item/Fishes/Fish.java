package FishingGame.Item.Fishes;

import FishingGame.Item.Item;

public class Fish extends Item {
    private int level;

    public Fish(String name, int price, int level) {
        super(name, price);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return super.toString() + ", 난이도 : " + level;
    }
}
