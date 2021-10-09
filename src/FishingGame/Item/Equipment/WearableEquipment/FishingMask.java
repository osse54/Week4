package FishingGame.Item.Equipment.WearableEquipment;

public class FishingMask extends WearableEquipment {
    public FishingMask(String name, long price, int statValue) {
        super(name, price, statValue);
    }

    @Override
    public String toString() {
        return super.toString() + ", 민첩 : +" + getStatValue();
    }
}
