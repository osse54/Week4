package FishingGame.Item.Equipment.WearableEquipment;

public class Cap extends WearableEquipment {
    public Cap(String name, long price, int statValue) {
        super(name, price, statValue);
    }

    @Override
    public String toString() {
        return super.toString() + ", 민첩 : +" + getStatValue();
    }
}
