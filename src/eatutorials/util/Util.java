package eatutorials.util;

import ea.Game;
import ea.Scene;

import java.awt.event.KeyEvent;

public class Util {
    public static void makeScreenshot(String code) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.writeScreenshot("Tutorial_" + code + ".png");
    }

    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Util.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName().substring(ste.getClassName().lastIndexOf(".")+1);
            }
        }
        return null;
    }

    public static void addScreenshotKey(String code) {
        Game.getActiveScene().addKeyListener(keyEvent -> {
            if(keyEvent.getKeyCode() == KeyEvent.VK_1) {
                makeScreenshot(code);
            }
        });
    }
}
