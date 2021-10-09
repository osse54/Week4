package FishingGame.Item;

public class Food extends Item {
    private int satietyFigure;
    public Food(String name, long price, int satietyFigure) {
        super(name, price);
        this.satietyFigure = satietyFigure;
    }

    public int getSatietyFigure() {
        return satietyFigure;
    }

    public void setSatietyFigure(int satietyFigure) {
        this.satietyFigure = satietyFigure;
    }

    @Override
    public String toString() {
        return super.toString() + ", 포만감 : " + satietyFigure;
    }
}
