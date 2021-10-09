package FishingGame;

import FishingGame.Item.Fishes.FreshwaterFish;
import FishingGame.Item.Fishes.SaltwaterFish;
import FishingGame.Item.Bait;
import FishingGame.Item.Equipment.FishingRod;
import FishingGame.Item.Equipment.WearableEquipment.*;
import FishingGame.Item.Food;
import FishingGame.Item.Item;
import FishingGame.Item.Vehicle;

public interface Items {
    FreshwaterFish[] FRESHWATER_FISH = {
        new FreshwaterFish("잉어", 60000, 8),
        new FreshwaterFish("향어", 15000, 7),
        new FreshwaterFish("붕어", 20000, 3),
        new FreshwaterFish("배스", 2500, 5),
        new FreshwaterFish("블루길", 2500, 2),
        new FreshwaterFish("가물치", 60000, 10),
        new FreshwaterFish("쏘가리", 50000, 9),
        new FreshwaterFish("메기", 10000, 8)
    };
    SaltwaterFish[] SALTWATER_FISH = {
        new SaltwaterFish("복어", 8000, 5),
        new SaltwaterFish("쥐치", 10000, 6),
        new SaltwaterFish("참돔", 100000, 8),
        new SaltwaterFish("은갈치", 50000, 7),
        new SaltwaterFish("고등어", 20000, 2),
        new SaltwaterFish("우럭", 30000, 6),
        new SaltwaterFish("돌돔", 100000, 6),
        new SaltwaterFish("광어", 30000, 7),
        new SaltwaterFish("능성어", 70000, 7),
        new SaltwaterFish("농어", 60000, 6),
        new SaltwaterFish("조기", 1200, 3),
        new SaltwaterFish("한치", 5000, 7),
        new SaltwaterFish("붕장어", 50000, 4),
        new SaltwaterFish("다금바리", 150000, 11),
        new SaltwaterFish("참치", 2000000, 18),
        new SaltwaterFish("삼치", 95000, 13),
        new SaltwaterFish("방어", 180000, 15)
    };
    Vehicle[] VEHICLES = {
        new Vehicle("카약", 300000, 2),
        new Vehicle("고무보트", 500000, 4),
        new Vehicle("미니보트", 700000, 7),
        new Vehicle("트로울러 요트(대형어 낚시 가능)", 30000000, 20),
        new Vehicle("럭셔리 요트(대형어 낚시 가능)", 450000000, 30)
    };
    Item[][] ITEMS = {
        {   // 모자
            new Cap("검정 모자", 10000, 1),
            new Cap("파란 모자", 50000, 2),
            new Cap("빨강 모자", 100000, 3),
            new Cap("하얀 모자", 200000, 4)
        },
        {   // 낚시 마스크
            new FishingMask("흰색 마스크", 20000, 1),
            new FishingMask("파란 마스크", 50000, 2),
            new FishingMask("회색 마스크", 100000, 3),
            new FishingMask("밀리터리 마스크", 200000, 4),
            new FishingMask("검정 마스크", 500000, 5)
        },
        {   // 장갑
            new Gloves("목장갑", 100, 1),
            new Gloves("목장갑(반코팅)", 150, 2),
            new Gloves("3M 장갑", 1000, 3),
            new Gloves("입문용 장갑", 15000, 5),
            new Gloves("초보용 장갑", 50000, 10),
            new Gloves("중급용 장갑", 100000, 15),
            new Gloves("고급용 장갑", 200000, 20),
            new Gloves("프로용 장갑", 500000, 30)
        },
        {   // 선글라스
            new Sunglasses("초급용 선글라스", 15000, 1),
            new Sunglasses("중급용 선글라스", 35000, 3),
            new Sunglasses("고급용 선글라스", 230000, 5)
        },
        {   // 상의
            new Top("검정 티", 10000, 1),
            new Top("하얀 티", 30000, 2),
            new Top("밀리터리 티", 50000, 3),
            new Top("파란 티", 100000, 4),
            new Top("빨간 티", 300000, 5),
            new Top("주황 티", 700000, 7)
        },
        {   // 조끼
            new Vest("구명 조끼", 10000, 1),
            new Vest("회색 조끼", 30000, 2),
            new Vest("검정 조끼", 50000, 3),
            new Vest("밀리터리 조끼", 100000, 4)
        },
        {   // 낚시대
            new FishingRod("입문용 낚시대", 30000, 1),
            new FishingRod("초심자용 낚시대", 70000, 2),
            new FishingRod("초보자용 낚시대", 130000, 3),
            new FishingRod("초급용 낚시대", 300000, 4),
            new FishingRod("중급용 낚시대", 500000, 5),
            new FishingRod("고급용 낚시대", 1000000, 6),
            new FishingRod("프로용 낚시대", 3000000, 7)
        },
        {   // 미끼
            new Bait("청갯지렁이", 100, 40),
            new Bait("참갯지렁이", 500, 60),
            new Bait("백크릴", 50, 20),
            new Bait("각크릴", 150, 40),
            new Bait("꽁치(대형어 전용)", 1000, 60)
        },
        {   // 음식
            new Food("김밥", 3000, 75),
            new Food("뽀글이", 2500, 70),
            new Food("과일", 5000, 60),
            new Food("컵라면", 1500, 50),
            new Food("영양 만점 도시락", 10000, 90),
            new Food("유부초밥", 3000, 60),
            new Food("초콜릿", 1500, 25),
            new Food("사탕", 500, 10)
        }
    };
}
