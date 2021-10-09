package FishingGame.Item;

public class Bait extends Item {
    private int temptationFigures;
    public Bait(String name, long price, int temptationFigures) {
        super(name, price);
        this.temptationFigures = temptationFigures;
    }

    public int getTemptationFigures() {
        return temptationFigures;
    }

    public void setTemptationFigures(int temptationFigures) {
        this.temptationFigures = temptationFigures;
    }
}
