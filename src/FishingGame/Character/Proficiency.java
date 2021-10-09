package FishingGame.Character;

public class Proficiency {
    // 초기화하지 않았을 경우 기본 값 0.0이 할당되어 있음, 최대 100으로 설정
    private double fishing;         // 한 마리 낚을 때 마다 0.01씩 오름
    private double attractingFish;  // 한 번 입질에 0.001씩 오름
    private double multiRods;

    private double oneFishing = 0.01;
    private double oneBite = 0.001;
    private double multiRodsBitten = 0.00001;

    public double getFishing() {
        return fishing;
    }

    public void setFishing(double fishing) {
        this.fishing = fishing;
    }

    public double getAttractingFish() {
        return attractingFish;
    }

    public void setAttractingFish(double attractingFish) {
        this.attractingFish = attractingFish;
    }

    public double getMultiRods() {
        return multiRods;
    }

    public void setMultiRods(double multiRods) {
        this.multiRods = multiRods;
    }

    public void sucFishing() {
        if(fishing < 100) fishing += oneFishing;
    }

    public void bitten() {
        if(attractingFish < 100) attractingFish += oneBite;
    }

    public void bittenMultiRods() {
        if(multiRods < 100) multiRods += multiRodsBitten;
    }
}
