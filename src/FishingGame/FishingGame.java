package FishingGame;

import FishingGame.Character.Character;
import FishingGame.FishingPlaces.FishingPlace;
import FishingGame.FishingPlaces.Reservoir;
import FishingGame.FishingPlaces.River;
import FishingGame.FishingPlaces.Sea;
import FishingGame.Item.Bait;
import FishingGame.Item.Fishes.Fish;
import FishingGame.Item.Food;
import FishingGame.Item.Item;
import FishingGame.Item.ItemBox;
import FishingGame.Shop.BoatShop;
import FishingGame.Shop.EquipmentShop;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class FishingGame {
    private static EquipmentShop equipmentShop;
    private static BoatShop boatShop;
    private Reservoir reservoir;
    private River river;
    private Sea sea;

    public FishingGame() {
        equipmentShop = new EquipmentShop();
        boatShop = new BoatShop();
        reservoir = new Reservoir();
        river = new River();
        sea = new Sea();
    }

    public void run() {
        playMusic();
        Character me = begin();
        me.earnMoney(100000000);

        UtilClass.printPercent(UtilClass.getRandomInt(3) + 1);
        int choice;
        boolean keepPlay = false;
        do {
            me.getBelongings().refresh();
            choice = UtilClass.selectMenu("낚시", "상점", "인벤토리", "상태창", "게임 그만하기");
            switch(choice) {
                case 1:
                    if(me.getSatiety() != 0) {
                        if(me.getFishingRod() != null) {
                            goToFishing(me);
                        } else {
                            UtilClass.say("낚시대를 착용하고 오세요");
                        }
                    } else {
                        UtilClass.say("포만감이 0입니다. 밥 먹고 오세요");
                    }
                    break;
                case 2:
                    shop(me);
                    break;
                case 3:
                    me.showInventory();
                    break;
                case 4:
                    me.toStatus();
                    me.equipmentStatus();
                    break;
                case 5:
                    UtilClass.say("안녕히 가세용~");
                    break;
                default:
                    System.out.println("잘못된 숫자를 입력했습니다. 다시 입력해 주세요.");
                    break;
            }
            if(keepPlay || (me.getMoney() == 500000000 && me.getBelongings().isExist(Items.VEHICLES[ItemIndex.VEHICLE_럭셔리요트]))) {
                space(3);
                success(me.getId());
                if(UtilClass.selectMenu("계속 함께하며 낚시를 한다.", "너는 이제 나를 떠나 행복하게 살렴") == 1) {
                    keepPlay = true;
                }
            }
        } while(choice != 5 || (keepPlay || (me.getMoney() == 500000000 && me.getBelongings().isExist(Items.VEHICLES[ItemIndex.VEHICLE_럭셔리요트]))));
        System.exit(0);
    }

    public void goToFishing(Character character) {
        UtilClass.printPercent(UtilClass.getRandomInt(3) + 1);
        FishingPlace fishingPlace = null;
        if(character.haveBait()) {
            UtilClass.say("어디로 갈까요?");
            int choice = UtilClass.select("바다", "저수지", "강") + 1;
            switch(choice) {
                case 1:
                    fishingPlace = sea;
                    break;
                case 2:
                    fishingPlace = reservoir;
                    break;
                case 3:
                    fishingPlace = river;
                    break;
                case 4:
                default:
                    break;
            }
            if(fishingPlace != null) {
                boolean flag = false;
                for(int i = 0; i < Items.VEHICLES.length && !flag; i++) {
                    if(character.getBelongings().isExist(Items.VEHICLES[i])) {
                        flag = true;
                    }
                }
                if(flag) {
                    UtilClass.say("배를 타고 물고기를 잡을 까요?\n배를 타면 물고기가 더 잘 잡힙니다.");
                    choice = UtilClass.select("배타고 낚시한다.", "물가에서 낚시한다.") + 1;
                    if(choice == 1) {
                        character.setVehicle(character.boardVehicle());
                        if((character.getVehicle() != null)) {
                            UtilClass.say(character.getVehicle().getName() + "에 탑승");
                        } else {
                            System.out.println("배에 탑승 하지 않았습니다.");
                        }
                    }
                }
                do {
                    if(fishingPlace == reservoir || character.getVehicle() != null) {
                        choice = UtilClass.select("자동 낚시", "자동 거치 낚시", "조작 낚시") + 1;
                    } else {
                        choice = UtilClass.select("낚시하기") + 1 + 2; // 자동 낚시 2종류를 건너 뛰기
                        // case : 낚시하기 == 1(3번), 뒤로가기 == 2(4번)
                    }
                    if(choice == 1) {
                        character.autoFishing(fishingPlace);
                    } else if(choice == 2) {
                        character.multiRodsFishing(fishingPlace);
                    } else if(choice == 3) {
                        choice = 1;
                        while(choice == 1 && character.getSatiety() != 0) {
                            Bait bait = character.useBait();
                            if(bait != null) {  // 낚시
                                Fish f = character.fishing(bait, fishingPlace);
                                if(f != null) {
                                    character.storeItem(f);
                                }
                                System.out.println((f == null) ? "" : f);
                            }
                            if(character.getSatiety() != 0) {
                                if(character.haveBait()) {  // 낚시를 할 것인지 선택 과정
                                    System.out.println("낚시를 계속 하시나요? 현재 포만감 : " + character.getSatiety());
                                    System.out.print(">");
                                    choice = UtilClass.selectMenu("계속 낚시 한다.", "그만한다.");
                                    System.out.println();
                                }
                            }
                        }
                    }
                    character.getBelongings().refresh();
                } while(choice != 4 && character.getSatiety() != 0 && character.haveBait());

            }
        }
        if(character.getVehicle() != null) {
            character.unMount(character.getVehicle());
            character.setVehicle(null);
        }
        if(!character.haveBait()) {
            UtilClass.say("미끼가 없습니다.");
        }
        if(character.getSatiety() == 0) {
            UtilClass.say("배고파서 낚시를 할 수 없습니다.");
        }

    }

    public Character begin() {
        UtilClass.say("안녕하세요");
        UtilClass.say("낚시 게임에 오신 것을 환영합니다");
        System.out.print("캐릭터 이름을 정해주세요 > ");
        String cName = UtilClass.scStr();
        Character me = new Character(cName);

        UtilClass.say("");
        me.toStatus();
        UtilClass.say("");

        space(3);

        UtilClass.say("환영합니다." + me.getId() + "님");
        UtilClass.say("원활한 게임을 위해 스타트 패키지를 지급합니다.");
        giveItem(me, Items.ITEMS[ItemIndex.ITEMS_FOOD][1]);
        giveItem(me, Items.ITEMS[ItemIndex.ITEMS_FOOD][3]);
        giveItem(me, Items.ITEMS[ItemIndex.ITEMS_FOOD][4], 100);
        giveItem(me, Items.ITEMS[ItemIndex.ITEMS_BAIT][1], 1000);
        giveItem(me, Items.ITEMS[ItemIndex.ITEMS_FISHING_ROD][0], 10);
        UtilClass.say("스타트 패키지가 지급이 되었습니다.");
        UtilClass.say("이제 게임을 시작하겠습니다. 좋은 시간 되세요~");

        space(5);
        UtilClass.say("나의 꿈은 럭셔리 요트를 사고 5억을 모으기!");
        UtilClass.say("내가 좋아하는 낚시로 돈을 많이 모을 거야!");
        UtilClass.say("아자아자! 나는 할 수 있어!");
        return me;
    }

    public static void shop(Character character) {
        UtilClass.say("어떤 가게로 갈까요?");
        int choice = UtilClass.select("장비 가게", "보트 가게") + 1;
        if(choice == 1) {
            UtilClass.say("장비 가게");
            choice = UtilClass.select("구매", "판매") + 1;
            if(choice == 1) {
                equipmentPurchase(character);
            } else if(choice == 2) {
                sell(character);
            }
            shop(character);
        } else if(choice == 2) {
            UtilClass.say("보트 가게");
            choice = UtilClass.select("구매", "판매") + 1;
            if(choice == 1) {
                vehicleShop(character);
            } else if(choice == 2) {
                sell(character);
            }
            shop(character);
        }
    }

    public static void vehicleShop(Character character) {
        ItemBox box = boatShop.chooseItems(boatShop.getItems()[0]);
        if(box != null && character.useMoney((int) box.getItem().getPrice() * box.getNumberOfItem())) {
            character.storeItem(box);
            vehicleShop(character);
        }
    }

    public static void equipmentPurchase(Character character) {
        int choice = equipmentShop.printCategory();
        if(equipmentShop.getItems().length != choice) {
            ItemBox box = equipmentShop.chooseItems(equipmentShop.getItems()[choice]);
            if(box != null && (box.getItem() instanceof Bait || box.getItem() instanceof Food)) {
                int max = (int)(character.getMoney() / box.getItem().getPrice());
                int amount = UtilClass.chooseAmount(max);
                if(amount < max + 1) {
                    box.setNumberOfItem(amount);
                }
            }
            if(box != null && box.getNumberOfItem() > 0 && character.useMoney((int) box.getItem().getPrice() * box.getNumberOfItem())) {
                character.storeItem(box);
            }
            equipmentPurchase(character);
        }
    }

    public void giveItem(Character character, Item item) {
        giveItem(character, item, 1);
    }

    public void giveItem(Character character, Item item, int numberOfItem) {
        character.storeItem(item, numberOfItem);
        UtilClass.say(character.getId() + "님에게 " + item.getName() + " " + numberOfItem + "개 지급되었습니다.");
    }

    public void space(int number) {
        for(int i = 0; i < number; i++) {
            System.out.println();
        }
    }

    public static void sell(Character character) {
        ItemBox[] boxes = character.getBelongings().getItemList().toArray(new ItemBox[0]);
        System.out.println("판매 창");
        int choice = UtilClass.select(boxes);
        if(choice < boxes.length) {
            ItemBox box = character.getBelongings().getItem(choice);
            int amount = UtilClass.chooseAmount(box.getNumberOfItem());
            if(amount <= box.getNumberOfItem()) {
                character.useItem(box.getItem(), amount);
                character.earnMoney((int) (box.getItem().getPrice() * amount));
            }
            sell(character);
        }
    }

    public void success(String id) {
        UtilClass.say("성공했다!");
        UtilClass.say("나도 이제 부자가 되었어!");
        UtilClass.say("너무 행복하다!");
        UtilClass.say("이제 내가 집을 살 수 있게 되었어!");
        UtilClass.say(id + "야 나를 도와줘서 고마워!");
        UtilClass.say("우리 계속 함께할까?");
    }

    private void playMusic() {
        File f = new File("BGM.wav");
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(f);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
