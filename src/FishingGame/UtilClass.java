package FishingGame;

import FishingGame.Item.ItemBox;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import java.applet.AudioClip;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class UtilClass {

    private static Scanner sc = new Scanner(System.in);
    private static Random r = new Random();

    public static int getRandomInt(int number) {
        r.setSeed(System.currentTimeMillis() + r.nextInt(Integer.MAX_VALUE));
        return r.nextInt(number);
    }

    public static int scInt() {
        int i = 0;
        try {
            i = Integer.parseInt(sc.nextLine());
        } catch(NumberFormatException e) {
            System.out.println("지금은 숫자를 입력해 주세요.");
            i = scInt();
        }
        return i;
    }

    public static String scStr() {
        return sc.nextLine();
    }

    public static int select(Object... options) {
        int select = options.length;
        boolean flag = false;
        do {
            if(flag) {
                System.out.println("올바른 값을 입력해주세요.");
            } else {
                flag = true;
            }
            for(int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + "번 " + options[i].toString());
            }
            System.out.println(options.length + 1 + "번 뒤로가기");
            select = scInt();
            System.out.println();
        } while(select < 0 || select > options.length + 1);
        return --select;
    }

    public static int selectMenu(Object... options) {
        int select = options.length;
        boolean flag = false;
        do {
            if(flag) {
                System.out.println("올바른 값을 입력해주세요.");
            } else {
                flag = true;
            }
            for(int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + "번 " + options[i].toString());
            }
            select = scInt();
            System.out.println();
        } while(select < 1 || select > options.length);
        return select;
    }

    public static int chooseAmount(int amount) {
        int select = 0;
        boolean flag = false;
        while(select < 0 && select > amount + 1 || flag != true) {
            if(flag) {
                System.out.println("올바른 값을 입력해주세요.");
            } else {
                flag = true;
            }

            System.out.println("갯수를 선택하세요. 최대 선택가능 갯수 : " + amount);
            System.out.println("뒤로가기 : " + (amount + 1));
            System.out.print(">");
            select = scInt();
            System.out.println();
        }
        return select;
    }

    public synchronized static void say(String str) {
        System.out.println(str);
        System.out.println();
    }

    public static void printPercent(long time) {
        try {
            String str = "0%";
            System.out.print(str);
            for(int i = 0; i < 100; i++) {
                System.out.print("\r");
                Thread.sleep(time * 1000 / 100);
                System.out.print((i + 1) + "%");
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
