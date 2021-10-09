package FishingGame.Item;

public class Vehicle extends Item {
    private int maxMountRod; // 최대 낚시대 거치 갯수

    // trawler
    public Vehicle(String name, long price, int maxMountRod) {
        super(name, price);
        this.maxMountRod = maxMountRod;
    }

    public int getMaxMountRod() {
        return maxMountRod;
    }

    public void setMaxMountRod(int maxMountRod) {
        this.maxMountRod = maxMountRod;
    }

    @Override
    public String toString() {
        return super.getName() + ", 낚시대 최대 거치 가능 대수 : " + maxMountRod + ", 구매가격 : " + getPrice();
    }
}
