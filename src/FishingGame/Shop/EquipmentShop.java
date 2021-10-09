package FishingGame.Shop;

import FishingGame.Item.Bait;
import FishingGame.Item.Equipment.FishingRod;
import FishingGame.Item.Equipment.WearableEquipment.*;
import FishingGame.Item.Food;
import FishingGame.Item.Item;
import FishingGame.Items;
import FishingGame.UtilClass;

public class EquipmentShop extends Shop {
    public EquipmentShop() {
        super(Items.ITEMS);
    }

    public int printCategory() {
        Item[][] items = getItems();
        for(int i = 0; i < items.length; i++) {
            String str = "번 ";
            if(items[i][0] instanceof Cap) {
                str += "모자";
            } else if(items[i][0] instanceof FishingMask) {
                str += "낚시 마스크";
            } else if(items[i][0] instanceof Gloves) {
                str += "장갑";
            } else if(items[i][0] instanceof Sunglasses) {
                str += "선글라스";
            } else if(items[i][0] instanceof Top) {
                str += "상의";
            } else if(items[i][0] instanceof Vest) {
                str += "조끼";
            } else if(items[i][0] instanceof FishingRod) {
                str += "낚시대";
            } else if(items[i][0] instanceof Bait) {
                str += "미끼";
            } else if(items[i][0] instanceof Food) {
                str += "음식";
            }
            System.out.println((i + 1) + str);
        }
        System.out.print((items.length + 1) + "번 뒤로가기\n>");
        int choice = UtilClass.scInt();
        System.out.println();
        while(!(choice > 0 && choice <= items.length + 1)) {
            System.out.print("다시 입력하세요>");
            choice = UtilClass.scInt();
            System.out.println();
        }
        return choice - 1;
    }
}
