package FishingGame.Character;

import FishingGame.FishingPlaces.FishingPlace;
import FishingGame.Item.Bait;
import FishingGame.Item.Equipment.FishingRod;
import FishingGame.Item.Fishes.Fish;
import FishingGame.Item.Food;
import FishingGame.Item.ItemBox;
import FishingGame.UtilClass;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class FishingRunnable implements Runnable {
    private static final int ESC = 27;
    private static final int SPACE = 32;
    private static int CHOICE;
    private FishingRod fishingRod;
    private static ItemBox baitBox; // 미끼 박스
    private static ItemBox foodBox;
    private Vector<Fish> fishes;
    private FishingPlace fishingPlace;
    private Character character;
    private static JFrame frame;
    private static KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e);
            if(e.getKeyCode() == ESC) {
                CHOICE = ESC;
            } else if(e.getKeyCode() == SPACE) {
                CHOICE = SPACE;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {}
    };

    public FishingRunnable(FishingRod fishingRod, FishingPlace fishingPlace, Character character, Vector<Fish> fishes) {
        this.fishingRod = fishingRod;
        this.fishingPlace = fishingPlace;
        this.character = character;
        this.fishes = fishes;
    }

    @Override
    public void run() {
        int amount = 0;
        while(character.getSatiety() > 0 && CHOICE != ESC && baitBox.getNumberOfItem() > 0) {
            if(CHOICE != SPACE) {
                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Fish f = null;
            synchronized(baitBox) {
                if(baitBox.getNumberOfItem() > 0) {
                    baitBox.setNumberOfItem(baitBox.getNumberOfItem() - 1);
                }
                amount = baitBox.getNumberOfItem();
            }
            f = fishingRod.waitFish((Bait) baitBox.getItem(), fishingPlace, character);
            synchronized(foodBox) {
                if(character.getSatiety() <= 0 && foodBox.getNumberOfItem() > 0) {
                    character.eatFood((Food) foodBox.getItem());
                    foodBox.setNumberOfItem(foodBox.getNumberOfItem() - 1);
                }
            }
            if(f != null) {
                fishes.add(f);
            }
        }
    }

    public static void setBox(ItemBox baitBox, ItemBox foodBox) {
        CHOICE = 0;
        FishingRunnable.baitBox = baitBox;
        FishingRunnable.foodBox = foodBox;
    }

    public static void readKey() {
        if(frame == null) {
            frame = new JFrame();
            frame.setLayout(null);
            frame.setSize(100, 100);
        }
        frame.addKeyListener(kl);
        frame.setFocusable(true);
        frame.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.requestFocus();
    }
    
    public static void stopRead() {
        frame.setVisible(false);
    }
    public static void quit() {
        CHOICE = ESC;
    }
    public static int getBaitAmount() {
        synchronized (baitBox) {
            return baitBox.getNumberOfItem();
        }
    }
    public static int getFoodAmount() {
        synchronized (foodBox) {
            return foodBox.getNumberOfItem();
        }
    }
}