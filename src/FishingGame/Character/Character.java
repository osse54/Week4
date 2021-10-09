package FishingGame.Character;

import FishingGame.FishingGame;
import FishingGame.FishingPlaces.Sea;
import FishingGame.Item.Fishes.Fish;
import FishingGame.FishingPlaces.FishingPlace;
import FishingGame.Item.*;
import FishingGame.Item.Equipment.Equipment;
import FishingGame.Item.Equipment.FishingRod;
import FishingGame.Item.Equipment.WearableEquipment.*;
import FishingGame.ItemIndex;
import FishingGame.Items;
import FishingGame.UtilClass;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class Character {

    private String id;
    private long money;
    private Inventory belongings;
    private Proficiency proficiency;
    private Vehicle vehicle;

    private int strength;
    private int agility;
    private int lucky;
    private int satiety;

    private Cap cap;
    private FishingMask fishingMask;
    private Gloves gloves;
    private Sunglasses sunglasses;
    private Top top;
    private Vest vest;

    private FishingRod fishingRod;

    public Character(String id) {
        this.id = id;
        money = 10000;
        belongings = new Inventory();
        proficiency = new Proficiency();
        strength = 1;
        agility = 1;
        lucky = 1;
        satiety = 100;
    }

    public Item mount(Equipment e) {
        Item item = null;
        if(e instanceof Cap) {
            item = cap;
            cap = (Cap) e;
        } else if(e instanceof FishingMask) {
            item = fishingMask;
            fishingMask = (FishingMask) e;
        } else if(e instanceof Gloves) {
            item = gloves;
            gloves = (Gloves) e;
        } else if(e instanceof Sunglasses) {
            item = sunglasses;
            sunglasses = (Sunglasses) e;
        } else if(e instanceof Top) {
            item = top;
            top = (Top) e;
        } else if(e instanceof Vest) {
            item = vest;
            vest = (Vest) e;
        } else if(e instanceof FishingRod) {
            item = fishingRod;
            fishingRod = (FishingRod) e;
        }
        return item;
    }

    public void toStatus() {
        String str = "상태창\n\n";
        str += "아이디 : " + id + "\n";
        str += "스탯\n";
        str += "힘 : " + getStrength() + "\n";
        str += "민첩 : " + getAgility() + "\n";
        str += "행운 : " + getLucky() + "\n";
        str += "포만감 : " + satiety + "\n\n";
        str += "숙련도\n\n";
        str += "물고기 유혹하기 : " + proficiency.getAttractingFish() + "\n";
        str += "물고기 낚아 올리기 : " + proficiency.getFishing() + "\n\n";
        str += "장비\n\n";
        str += "낚시대 : " + fishingRod + "\n";
        str += "모자 : " + cap + "\n";
        str += "마스크 : " + fishingMask + "\n";
        str += "장갑 : " + gloves + "\n";
        str += "선글라스 : " + sunglasses + "\n";
        str += "옷 : " + top + "\n";
        str += "조끼 : " + vest + "\n\n";
        str += "소지한 돈 : " + money + "\n\n";
        str = str.replace("null", "없음");
        UtilClass.say(str);
    }

    public void equipmentStatus() {
        ArrayList<Item> arr = new ArrayList<>();
        if(fishingRod != null) {
            arr.add(fishingRod);
        }
        if(cap != null) {
            arr.add(cap);
        }
        if(fishingMask != null) {
            arr.add(fishingMask);
        }
        if(gloves != null) {
            arr.add(gloves);
        }
        if(sunglasses != null) {
            arr.add(sunglasses);
        }
        if(top != null) {
            arr.add(top);
        }
        if(vest != null) {
            arr.add(vest);
        }
        int select;
        if(arr.size() != 0) {
            UtilClass.say("장착 해제할 장비를 선택 해주세요.");
            select = UtilClass.select(arr.toArray(new Item[0]));
            if(select != arr.size()) {
                if(arr.get(select) instanceof FishingRod) {
                    fishingRod = null;
                } else if(arr.get(select) instanceof Cap) {
                    cap = null;
                } else if(arr.get(select) instanceof FishingMask) {
                    fishingMask = null;
                } else if(arr.get(select) instanceof Gloves) {
                    gloves = null;
                } else if(arr.get(select) instanceof Sunglasses) {
                    sunglasses = null;
                } else if(arr.get(select) instanceof Top) {
                    top = null;
                } else if(arr.get(select) instanceof Vest) {
                    vest = null;
                }
                unMount(arr.get(select));
                toStatus();
                equipmentStatus();
            }
        } else {
            UtilClass.say("현재 해제할 장비가 없습니다.");
        }
    }

    public void unMount(Item item) {
        if(item != null) {
            UtilClass.say(item.getName() + "을(를) 장착 해제하였습니다.");
            belongings.addItem(item);
        }
    }

    public boolean eatFood(Food food) {
        boolean flag = false;
        if(satiety < 100) {
            satiety += food.getSatietyFigure();
            flag = true;
        }
        if(satiety > 100) {
            satiety = 100;
        }
        return flag;
    }

    public boolean useEnergy() {
        if(satiety != 0) {
            satiety--;
            return true;
        }
        return false;
    }

    // 낚시
    public Fish fishing(Bait bait, FishingPlace fishingPlace) {
        // 걸린 물고기
        Fish f = fishingPlace.getRandomFish(this, bait);
        System.out.println("물고기가 입질했다");
        if(satiety == 0) {
            UtilClass.say("배고파서 놓쳤다.");
            return null;
        }
        useEnergy();
        int onWater = (vehicle == null) ? 7 : 0;

        // 행운 + 민첩 vs 물고기 난이도
        if(UtilClass.getRandomInt((int)(proficiency.getAttractingFish() / 10) + onWater + bait.getTemptationFigures() + getLucky() + getAgility() + f.getLevel()) + 1 <= f.getLevel()) {
            f = null;
        }
        proficiency.bitten();
        if(satiety == 0) {
            UtilClass.say("배고파서 놓쳤다.");
            return null;
        }
        useEnergy();
        System.out.println(((f == null) ? "미끼만 먹고 도망쳤다." : "바늘에 걸렸다!"));
        if(f != null) {
            // 힘 vs 물고기 난이도
            if(UtilClass.getRandomInt((int)(proficiency.getFishing() / 10) + getStrength() + f.getLevel()) + 1 <= f.getLevel()) {
                f = null;
            }
            proficiency.sucFishing();
            if(satiety == 0) {
                UtilClass.say("배고파서 놓쳤다.");
                return null;
            }
            useEnergy();
            System.out.println((f == null) ? "싸움에서 졌다." : "싸움에서 이겼다!");
        }
        System.out.println("현재 포만감 수치 : " + satiety);
        return f;
    }

    public void multi(FishingRod fishingRod) {
        UtilClass.say("지금 선택할 미끼는 소진까지 사용할 예정입니다.");
        Bait bait = useBait();

    }

    public boolean useMoney(int money) {
        boolean flag = true;
        if(this.money < money) {
            flag = false;
            System.out.println(Math.abs(this.money - money) + "원만큼 돈이 부족합니다. 현재 소지한 돈 : " + this.money);
        } else {
            System.out.print(this.money + "원에서 ");
            this.money -= money;
            System.out.println(money + "원 사용해서 " + this.money + "원 남음");
            System.out.println();
        }
        return flag;
    }

    public void earnMoney(int money) {
        System.out.print(this.money + "원에서 ");
        this.money += money;
        UtilClass.say(money + "원 벌어서 " + this.money + "원 있음");
    }

    public boolean haveBait() {
        boolean flag = false;
        for(Item temp : Items.ITEMS[ItemIndex.ITEMS_BAIT]) {
            if(belongings.isExist(temp) && !flag) {
                flag = true;
            }
        }
        return flag;
    }

    public Bait chooseBait() {
        Bait bait = null;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        UtilClass.say("미끼를 선택해 주세요.");
        for(int i = 0; i < belongings.getItemList().size(); i++) {
            if(belongings.getItemList().get(i).getItem() instanceof Bait) {
                arr.add(i);
            }
        }
        if(arr.size() != 0) {

            // 미끼 선택
            for(int i = 0; i < arr.size(); i++) {
                System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
            }
            System.out.println((arr.size() + 1) + "번 뒤로가기");
            int choice = UtilClass.scInt() - 1;
            System.out.println();
            while(choice < 0 || choice > arr.size()) {
                System.out.println("입력하신 숫자는 잘못되었습니다. 다시 입력하세요");
                System.out.println();
                for(int i = 0; i < arr.size(); i++) {
                    System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
                }
                System.out.println((arr.size() + 1) + "번 뒤로가기");
                choice = UtilClass.scInt() - 1;
                System.out.println();
            }
            if(choice != arr.size()) {
                ItemBox box = belongings.getItemList().get(arr.get(choice));
                bait = (Bait)box.getItem();
            }
        }
        return bait;
    }

    public Food chooseFood() {
        Food food = null;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        UtilClass.say("음식을 선택해 주세요.");
        for(int i = 0; i < belongings.getItemList().size(); i++) {
            if(belongings.getItemList().get(i).getItem() instanceof Food) {
                arr.add(i);
            }
        }
        if(arr.size() != 0) {

            // 미끼 선택
            for(int i = 0; i < arr.size(); i++) {
                System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
            }
            System.out.println((arr.size() + 1) + "번 뒤로가기");
            int choice = UtilClass.scInt() - 1;
            System.out.println();
            while(choice < 0 || choice > arr.size()) {
                System.out.println("입력하신 숫자는 잘못되었습니다. 다시 입력하세요");
                System.out.println();
                for(int i = 0; i < arr.size(); i++) {
                    System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
                }
                System.out.println((arr.size() + 1) + "번 뒤로가기");
                choice = UtilClass.scInt() - 1;
                System.out.println();
            }
            if(choice != arr.size()) {
                ItemBox box = belongings.getItemList().get(arr.get(choice));
                food = (Food)box.getItem();
            }
        }
        return food;
    }

    public Bait useBait() {
        Bait bait = chooseBait();
        if(bait != null) {
            useItem(bait);
        }
        return bait;
    }

    public Vehicle boardVehicle() {
        Vehicle vehicle = null;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i = 0; i < belongings.getItemList().size(); i++) {
            if(belongings.getItemList().get(i).getItem() instanceof Vehicle) {
                arr.add(i);
            }
        }
        if(arr.size() != 0) {

            for(int i = 0; i < arr.size(); i++) {
                System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
            }
            System.out.println((arr.size() + 1) + "번 뒤로가기");
            int choice = UtilClass.scInt() - 1;
            System.out.println();
            while(choice < 0 || choice > arr.size()) {
                System.out.println("입력하신 숫자는 잘못되었습니다. 다시 입력하세요");
                System.out.println();
                for(int i = 0; i < arr.size(); i++) {
                    System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
                }
                System.out.println((arr.size() + 1) + "번 뒤로가기");
                choice = UtilClass.scInt() - 1;
                System.out.println();
            }
            if(choice < arr.size()) {
                ItemBox box = belongings.getItemList().get(arr.get(choice));
                vehicle = (Vehicle) box.getItem();
                useItem(vehicle);
            }
        }
        return vehicle;
    }

    public void storeItem(Item item) {
        storeItem(item, 1);
    }
    public void storeItem(Item item, int numberOfItem) {
        belongings.addItem(item, numberOfItem);
        UtilClass.say(item.getName() + "을(를) 획득했습니다.");
    }
    public void storeItem(ItemBox box) {
        storeItem(box.getItem(), box.getNumberOfItem());
    }

    public void useItem(Item item) {
        useItem(item, 1);
    }
    public void useItem(Item item, int numberOfItem) {
        belongings.removeItem(item, numberOfItem);
        UtilClass.say(item.getName() + "을(를) 사용했습니다.");
    }

    public void showInventory() {
        while(true) {
            toStatus();
            System.out.println(id + "의 인벤토리");
            for(int i = 0; i < belongings.getItemList().size(); i++) {
                System.out.println((i + 1) + "번 " + belongings.getItem(i));
            }
            System.out.println(belongings.getItemList().size() + 1 + "번 뒤로가기");
            int choice = UtilClass.scInt() - 1;

            while(choice < 0 || choice > belongings.getItemList().size()) {
                System.out.println("입력하신 숫자는 잘못되었습니다. 다시 입력해 주세요.");
                choice = UtilClass.scInt() - 1;
            }   //  주어진 보기 이외의 항목에 대한 재선택
            if(choice == belongings.getItemList().size()) {
                return;
            }   // 뒤로가기
            ItemBox box = belongings.getItem(choice);
            UtilClass.say("선택한 아이템 : " + box.getItem().getName());
            if(box.getItem() instanceof Food && satiety != 100) {   // 음식이고 포만감이 100이 아니면
                do {
                    System.out.print("1. 먹기, 2. 버리기, 3. 뒤로가기\n>");
                    choice = UtilClass.scInt();
                } while(choice != 1 && choice != 2 && choice != 3);    // 1, 2, 3제외한 나머지 숫자가 입려되면 다시
                if(choice == 1 || choice == 2) {
                    if(choice == 1) {
                        eatFood((Food)box.getItem());
                    }
                    useItem(box.getItem());
//                if(numberOfItem == 1) {
//                    // 하나 있습니다. 버릴(먹을)까요? or 취소
//                } else {
//                    // 갯수 선택
//                    String begin = "몇 개를";
//                    String end = "까요? > ";
//                    String mid = "";
//                    if(choice == 1) {
//                        mid = "먹을";
//                    } else if(choice == 2) {
//                        mid = "버릴";
//                    }
//                    System.out.println("현재 아이템 갯수 : " + numberOfItem);
//                    System.out.println("뒤로가기는 -1을 입력해주세요.");
//                    System.out.print(begin + mid + end);
//                    choice = UtilClass.scInt();
//                    while((choice < 1 && choice > numberOfItem) || choice != -1) {
//                        System.out.println("입력한 숫자가 맞지 않습니다. 올바른 숫자를 입력해주세요.");
//                        System.out.println("현재 아이템 갯수 : " + numberOfItem);
//                        System.out.println("뒤로가기는 -1을 입력해주세요.");
//                        System.out.print(begin + mid + end);
//                        choice = UtilClass.scInt();
//                    }
//                    //
//                    if(choice != -1) {
//                        if(mid.equals("먹을")) {
//                            for(int i = 0; i < choice; i++) {
//                                eatFood((Food)box.getItem());
//                            }
//                        }
//                        if(choice == numberOfItem) {
//                            belongings.removeItem(box);
//                        } else {
//                            belongings.removeItem(box.getItem(), choice);
//                        }
//                    }
//
//                }
                }
            } else if(box.getItem() instanceof Equipment) { // 장비이면
                do {
                    System.out.print("1. 장착, 2. 버리기, 3. 뒤로가기\n>");
                    choice = UtilClass.scInt();
                } while(choice != 1 && choice != 2 && choice != 3);
                if(choice == 1 || choice == 2) {
                    if(choice == 1) {
                        Item item = mount((Equipment)box.getItem());
                        if(item != null) {
                            storeItem(item);
                        }
                    }
                    useItem(box.getItem());
                }
            } else {
                do {
                    System.out.print("1. 버리기, 2. 뒤로가기\n>");
                    choice = UtilClass.scInt();
                } while(choice != 1 && choice != 2);
                if(choice == 1) {
                    int amount = UtilClass.chooseAmount(box.getNumberOfItem());
                    belongings.removeItem(box.getItem(), amount);
                    UtilClass.say(box.getItem().getName() + "를 " + amount + "개를 버렸습니다.");
                }
            }
        }
    }

    public FishingRod chooseRod() {
        FishingRod rod = null;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i = 0; i < belongings.getItemList().size(); i++) {
            if(belongings.getItemList().get(i).getItem() instanceof FishingRod) {
                arr.add(i);
            }
        }

        if(arr.size() != 0) {
            for(int i = 0; i < arr.size(); i++) {
                System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
            }
            System.out.println((arr.size() + 1) + "번 뒤로가기");
            int choice = UtilClass.scInt() - 1;
            System.out.println();
            while(choice < 0 || choice > arr.size()) {
                System.out.println("입력하신 숫자는 잘못되었습니다. 다시 입력하세요");
                System.out.println();
                for(int i = 0; i < arr.size(); i++) {
                    System.out.println((i + 1) + "번 " + belongings.getItemList().get(arr.get(i)));
                }
                System.out.println((arr.size() + 1) + "번 뒤로가기");
                choice = UtilClass.scInt() - 1;
                System.out.println();
            }
            if(choice != arr.size()) {
                ItemBox box = belongings.getItemList().get(arr.get(choice));
                rod = (FishingRod) box.getItem();
                useItem(rod);
            }
        }

        return rod;
    }

    public void autoFishing(FishingPlace fishingPlace) {
        Vector<Fish> fishes = new Vector<>();

        UtilClass.say("뒤로가기를 누르면 자동 낚시가 취소됩니다.");
        Bait bait = chooseBait();
        if(bait == null) return;
        ItemBox baitBox = (ItemBox) belongings.getItemList().get(belongings.getItemList().indexOf(new ItemBox(bait)));
        int baitAmount = UtilClass.chooseAmount(baitBox.getNumberOfItem());
        if(baitAmount > baitBox.getNumberOfItem()) return;

        UtilClass.say("뒤로가기를 누르면 자동 낚시가 취소됩니다.");
        Food food = chooseFood();
        if(food == null) return;
        ItemBox foodBox = (ItemBox) belongings.getItemList().get(belongings.getItemList().indexOf(new ItemBox(food)));
        int foodAmount = UtilClass.chooseAmount(foodBox.getNumberOfItem());
        if(foodAmount > foodBox.getNumberOfItem()) return;

        // 사용분 음식
        baitBox.setNumberOfItem(baitBox.getNumberOfItem() - baitAmount);
        foodBox.setNumberOfItem(foodBox.getNumberOfItem() - foodAmount);
        ItemBox forUse = new ItemBox(bait, baitAmount);
        ItemBox forEat = new ItemBox(food, foodAmount);
        belongings.refresh();
        belongings.refresh();
        belongings.refresh();
        belongings.refresh();
        belongings.refresh();

        if(forUse.getNumberOfItem() > 0) {
            Thread t = new Thread(new FishingRunnable(fishingRod, fishingPlace, this, fishes));
            FishingRunnable.setBox(forUse, forEat);
            FishingRunnable.readKey();
            t.start();
            try {
                t.join();
                FishingRunnable.stopRead();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("잡은 물고기 : " + fishes.size() + "개");
        for(Fish temp : fishes) {
            storeItem(temp);
        }
        System.out.println();
        System.out.println();
        if(forUse.getNumberOfItem() > 0) {
            storeItem(forUse);
        }
        if(forEat.getNumberOfItem() > 0) {
            storeItem(forEat);
        }
        belongings.refresh();
    }

    public void multiRodsFishing(FishingPlace fishingPlace) {
        Vector<Fish> fishes = new Vector<>();

        UtilClass.say("뒤로가기를 누르면 자동 낚시가 취소됩니다.");
        Bait bait = chooseBait();
        if(bait == null) return;
        if(bait.equals(Items.ITEMS[ItemIndex.ITEMS_BAIT][ItemIndex.BAIT_꽁치])) {
            // 트로울러나 럭셔리요트가 아닌 경우
            if(vehicle != null) {
                if(!(vehicle.equals(Items.VEHICLES[ItemIndex.VEHICLE_트로울러]) || vehicle.equals(Items.VEHICLES[ItemIndex.VEHICLE_럭셔리요트])) || !(fishingPlace instanceof Sea)) {
                    UtilClass.say("꽁치를 사용할 수 없습니다.");
                }
            }
        }
        ItemBox baitBox = (ItemBox) belongings.getItemList().get(belongings.getItemList().indexOf(new ItemBox(bait)));
        int baitAmount = UtilClass.chooseAmount(baitBox.getNumberOfItem());
        if(baitAmount > baitBox.getNumberOfItem()) return;


        UtilClass.say("뒤로가기를 누르면 자동 낚시가 취소됩니다.");
        Food food = chooseFood();
        if(food == null) return;
        ItemBox foodBox = (ItemBox) belongings.getItemList().get(belongings.getItemList().indexOf(new ItemBox(food)));
        int foodAmount = UtilClass.chooseAmount(foodBox.getNumberOfItem());
        if(foodAmount > foodBox.getNumberOfItem()) return;


        // 사용할 낚시대들
        ArrayList<FishingRod> rods = new ArrayList<FishingRod>();
        if(fishingRod != null) {
            rods.add(fishingRod);
        }
        FishingRod rod = null;
        UtilClass.say("뒤로가기를 누르면 선택이 끝납니다.");
        do {
            if(vehicle != null) {
                UtilClass.say("최대 낚시대 거치 개수 : " + vehicle.getMaxMountRod() + " / 현재 거치된 낚시대 개수 : " + rods.size());
            }
            rod = chooseRod();
            if(rod != null) {
                rods.add(rod);
            }
            if(vehicle != null) {
                if(vehicle.getMaxMountRod() == rods.size()) {
                    rod = null;
                }
            }
        } while(rod != null);

        // 사용분 미끼
        ItemBox forUse = new ItemBox(bait, baitAmount);
        // 사용분 음식
        ItemBox forEat = new ItemBox(food, foodAmount);
        baitBox.setNumberOfItem(baitBox.getNumberOfItem() - baitAmount);
        foodBox.setNumberOfItem(foodBox.getNumberOfItem() - foodAmount);
        belongings.refresh();
        belongings.refresh();
        belongings.refresh();
        belongings.refresh();

        if(forUse.getNumberOfItem() > 0 && rods.size() > 0) {
            FishingRunnable.setBox(forUse, forEat);
            ArrayList<Thread> threads = new ArrayList<>();
            for(FishingRod temp : rods) {
                threads.add(new Thread(new FishingRunnable(temp, fishingPlace, this, fishes)));
            }
            UtilClass.say("중지 : esc");
            for(Thread temp : threads) {
                temp.start();
            }
            final boolean[] flag = {false};
            new Thread() {
                @Override
                public void run() {
                    for(Thread temp : threads) {
                        try {
                            temp.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    flag[0] = true;
                }
            }.start();
            while(!flag[0]) {
                System.out.println("뒤로가기는 그만두기");
                System.out.println("현재 낚시중 입니다. 현재 남은 미끼 : " + FishingRunnable.getBaitAmount() + " / 현재 남은 음식 : " + FishingRunnable.getFoodAmount());
                int choice = UtilClass.select("상점", "인벤토리", "상태창") + 1;
                if(choice == 1){
                    FishingGame.shop(this);
                } else if(choice == 2) {
                    showInventory();
                } else if(choice == 3) {
                    toStatus();
                    equipmentStatus();
                } else if(choice == 4) {
                    FishingRunnable.quit();
                    flag[0] = true;
                }
            }
        }

        System.out.println("잡은 물고기 : " + fishes.size() + "개");
        synchronized(fishes) {
            for(Fish temp : fishes) {
                storeItem(temp);
            }
        }
        System.out.println();
        if(forUse.getNumberOfItem() > 0) {
            storeItem(forUse);
        }
        if(forEat.getNumberOfItem() > 0) {
            storeItem(forEat);
        }
        for(int i = 1; i < rods.size(); i++) {
            belongings.addItem(rods.get(i));
        }
        belongings.refresh();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Inventory getBelongings() {
        return belongings;
    }

    public void setBelongings(Inventory belongings) {
        this.belongings = belongings;
    }

    public Proficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(Proficiency proficiency) {
        this.proficiency = proficiency;
    }

    public int getStrength() {
        int additionalValue = 0;
        if(gloves != null) {
            additionalValue += gloves.getStatValue();
        }
        if(fishingRod != null) {
            additionalValue += fishingRod.getStatValue();
        }
        return strength + additionalValue;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        int additionalValue = 0;
        if(cap != null) {
            additionalValue += cap.getStatValue();
        }
        if(fishingMask != null) {
            additionalValue += fishingMask.getStatValue();
        }
        if(sunglasses != null) {
            additionalValue += sunglasses.getStatValue();
        }
        if(fishingRod != null) {
            additionalValue += fishingRod.getStatValue();
        }
        return agility + additionalValue;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getLucky() {
        int additionalValue = 0;
        if(top != null) {
            additionalValue += top.getStatValue();
        }
        if(vest != null) {
            additionalValue += vest.getStatValue();
        }
        if(fishingRod != null) {
            additionalValue += fishingRod.getStatValue();
        }
        return lucky + additionalValue;
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public Cap getCap() {
        return cap;
    }

    public void setCap(Cap cap) {
        this.cap = cap;
    }

    public FishingMask getFishingMask() {
        return fishingMask;
    }

    public void setFishingMask(FishingMask fishingMask) {
        this.fishingMask = fishingMask;
    }

    public Gloves getGloves() {
        return gloves;
    }

    public void setGloves(Gloves gloves) {
        this.gloves = gloves;
    }

    public Sunglasses getSunglasses() {
        return sunglasses;
    }

    public void setSunglasses(Sunglasses sunglasses) {
        this.sunglasses = sunglasses;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public Vest getVest() {
        return vest;
    }

    public void setVest(Vest vest) {
        this.vest = vest;
    }

    public FishingRod getFishingRod() {
        return fishingRod;
    }

    public void setFishingRod(FishingRod fishingRod) {
        this.fishingRod = fishingRod;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;
        return Objects.equals(id, character.id);
    }

    @Override
    public String toString() {
        return "id : " + id;
    }
}
