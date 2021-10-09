package FishingGame.Shop;

import FishingGame.Item.Item;
import FishingGame.Item.ItemBox;
import FishingGame.UtilClass;

public class Shop {
    private final Item[][] items;

    public Shop(Item[]... items) {
        this.items = items;
    }

    public Item[][] getItems() {
        return items;
    }

    public ItemBox chooseItems(Item[] items) {
        String[] arr = new String[items.length];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = items[i].getName();
        }
        int select = UtilClass.select(arr);
        if(select == items.length) {
            return null;
        }
        return new ItemBox((items[select]));
//        for(int i = 0; i < items.length; i++) {
//            System.out.println((i + 1) + "번 " + items[i]);
//        }
//        System.out.print((items.length + 1) + "번 뒤로가기\n>");
//        int choice = UtilClass.scInt();
//        System.out.println();
//        while(!(choice > 0 && choice <= items.length + 1)) {
//            System.out.print("정확한 번호를 다시 입력하세요>");
//            choice = UtilClass.scInt();
//            System.out.println();
//        }
//        choice -= 1;
//        if(choice == items.length) {
//            return null;
//        } else {
//            return new ItemBox(items[choice]);
//        }
    }
}
