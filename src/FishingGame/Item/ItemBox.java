package FishingGame.Item;

import java.util.Objects;

public class ItemBox {
    private Item item;
    private int numberOfItem;

    public ItemBox(Item item, int numberOfItem) {
        this.item = item;
        this.numberOfItem = numberOfItem;
    }

    public ItemBox(Item item) {
        this.item = item;
        numberOfItem = 1;
    }

    public void add(int number) {
        numberOfItem += number;
    }

    public void minus(int number) {
        if(number > numberOfItem) {
            numberOfItem = 0;
        } else {
            numberOfItem -= number;
        }
    }

    public Item useItem() {
        numberOfItem--;
        return getItem();
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(int numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ItemBox itemBox = (ItemBox) o;
        return Objects.equals(item, itemBox.item);
    }

    @Override
    public String toString() {
        return item + ", 수량 " + numberOfItem + "개";
    }
}
