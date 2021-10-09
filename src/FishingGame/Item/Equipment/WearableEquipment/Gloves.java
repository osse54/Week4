package FishingGame.Item.Equipment.WearableEquipment;

public class Gloves extends WearableEquipment {
    public Gloves(String name, long price, int statValue) {
        super(name, price, statValue);
    }

    @Override
    public String toString() {
        return super.toString() + ", 힘 : +" + getStatValue();
    }
}
