package FishingGame.Item.Equipment.WearableEquipment;

public class Vest extends WearableEquipment {
    public Vest(String name, long price, int statValue) {
        super(name, price, statValue);
    }

    @Override
    public String toString() {
        return super.toString() + ", 행운 : +" + getStatValue();
    }
}
