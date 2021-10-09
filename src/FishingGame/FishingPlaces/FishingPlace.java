package FishingGame.FishingPlaces;

import FishingGame.Character.Character;
import FishingGame.Item.Fishes.Fish;
import FishingGame.Item.Bait;
import FishingGame.ItemIndex;
import FishingGame.Items;
import FishingGame.UtilClass;

import java.util.Arrays;

public class FishingPlace {
    private final Fish[] fishes;

    public FishingPlace(Fish[] fishes) {
        this.fishes = fishes;
    }

    public Fish[] getFishes() {
        return fishes;
    }

    public Fish getRandomFish(Character character, Bait bait) {
        Fish fish = null;

        // 물고기 정하기
        // if문 설명 - 해당 위치 어종이 해수어이고, 미끼가 꽁치이고, 배가 트로울러거나 럭셔리요트이면
        int randomInt;
        if(bait.equals(Items.ITEMS[ItemIndex.ITEMS_BAIT][ItemIndex.BAIT_꽁치]) && character.getVehicle() != null) {
            if(Arrays.equals(fishes, Items.SALTWATER_FISH) && (character.getVehicle().equals(Items.VEHICLES[ItemIndex.VEHICLE_트로울러]) || character.getVehicle().equals(Items.VEHICLES[ItemIndex.VEHICLE_럭셔리요트]))) {
                randomInt = UtilClass.getRandomInt(3) + ItemIndex.SALT_FISH_다금바리;    // 대형어종 갯수
                // 꽁치를 사용하면 대형어종만
            } else {
                return null;
                // 위 조건을 만족하지 않으면 꽁치를 미끼로 이용하여 잡을 수 있는 어종은 없다.
            }
        } else {
            int bound = fishes.length;
            if(Arrays.equals(fishes, Items.SALTWATER_FISH)) {
                bound -= fishes.length - ItemIndex.SALT_FISH_다금바리;
            }
            randomInt = UtilClass.getRandomInt(bound);
        }

        return fishes[randomInt];
    }
}
