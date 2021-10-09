package FishingGame.Item.Equipment;

import FishingGame.Character.Character;
import FishingGame.FishingPlaces.FishingPlace;
import FishingGame.Item.Bait;
import FishingGame.Item.Fishes.Fish;
import FishingGame.Item.ItemBox;
import FishingGame.UtilClass;

public class FishingRod extends Equipment {
    public FishingRod(String name, long price, int statValue) {
        super(name, price, statValue);
    }

    public Fish waitFish(Bait bait, FishingPlace fishingPlace, Character character) {
        // null 추적 - 객체 할당 후 다시 null을 할당하는 부분을 확인하면 된다.
        Fish f = null;
        if(character.useEnergy()) {
            f = fishingPlace.getRandomFish(character, bait);

            // 캐릭터가 물가가 아니라 수중에 있다면 물고기가 바늘에 걸릴 확률을 높여주는 설정
            int onWater = (character.getVehicle() == null) ? 7 : 0;

            // 행운 + 민첩 vs 물고기 난이도
            if(UtilClass.getRandomInt((int) (character.getProficiency().getMultiRods() / 10) + onWater + bait.getTemptationFigures() + (character.getLucky() - character.getFishingRod().getStatValue() + getStatValue()) + f.getLevel()) + 1 <= f.getLevel()) {
                f = null;
            }
            character.getProficiency().bittenMultiRods();
            if(character.useEnergy()) {
                if(f != null) {
                    // 힘 vs 물고기 난이도
                    if(UtilClass.getRandomInt((int) (character.getProficiency().getFishing() / 10) + (character.getStrength() - character.getFishingRod().getStatValue() + getStatValue()) + f.getLevel()) + 1 <= f.getLevel()) {
                        f = null;
                    }
                    if(f != null) {
                        character.getProficiency().sucFishing();
                    }
                }
            }
        }

        return f;
    }

    @Override
    public String toString() {
        return super.toString() + ", 올스탯 : +" + getStatValue();
    }
}
