package FishingGame.Character;
import FishingGame.Item.Item;
import FishingGame.Item.ItemBox;
import FishingGame.Items;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<ItemBox> itemList;

    public Inventory() {
        itemList = new ArrayList<ItemBox>();
    }

    public void refresh() {
        for(int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).getNumberOfItem() <= 0) {
                itemList.remove(i);
            }
        }
    }

    public void addItem(Item item) {
        addItem(item, 1);
    }

    public void addItem(Item item, int numberOfItem) {
        if(isExist(item)) {
            int index = itemList.indexOf(new ItemBox(item));
            ItemBox box = itemList.get(itemList.indexOf(new ItemBox(item)));
            box.add(numberOfItem);
        } else {
            itemList.add(new ItemBox(item, numberOfItem));
        }
    }

    public void addItem(ItemBox box) {
        addItem(box.getItem(), box.getNumberOfItem());
    }

    public boolean isExist(Item item) {
        return itemList.contains(new ItemBox(item));
    }

    public void removeItem(Item item) {
        removeItem(item, 1);
    }

    public void removeItem(Item item, int numberOfItem) {
        if(isExist(item)) {
            int index = itemList.indexOf(new ItemBox(item));
            ItemBox box = itemList.get(itemList.indexOf(new ItemBox(item)));
            box.minus(numberOfItem);
            if(box.getNumberOfItem() <= 0) {
                itemList.remove(index);
            }
        }
    }

    public void removeItem(ItemBox box) {
        removeItem(box.getItem(), box.getNumberOfItem());
    }

    public ItemBox getItem(Item item) {
        return itemList.get(itemList.indexOf(new ItemBox(item)));
    }

    public ItemBox getItem(int index) {
        return itemList.get(index);
    }

    public ArrayList<ItemBox> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<ItemBox> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        String str = "인벤토리\n";
        for(ItemBox temp : itemList) {
            str += "\n" + temp.toString();
        }
        return str;
    }
}
