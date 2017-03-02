package com.toliga.ganjabots.core;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.AbstractScript;

public class Utilities {
    private static int standingStill;

    public static Image LoadImage(String url, int width, int height) {
        Image infoImg = null;
        try {
            infoImg = ImageIO.read(new URL(url));
        } catch (IOException e) {
            AbstractScript.log(e.getMessage());
        }
        return infoImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private static void write(byte[] input, String outputFileName){
        try (OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFileName))) {
            output.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GoToTile(AbstractScript context, Tile tile) {
        context.getWalking().walk(tile);
        AbstractScript.sleepUntil(() -> tile.distance(context.getLocalPlayer().getTile()) < 2, 3000);
    }

    public static void GoToArea(AbstractScript context, Area whichArea) {
        context.getWalking().walk(whichArea.getRandomTile());
        AbstractScript.sleepUntil(() -> whichArea.contains(context.getLocalPlayer()), 3000);
    }

    public static void BankAllItems(AbstractScript context) {
        OpenTab(context, Tab.INVENTORY);

        if (context.getBank().open()) {
            if (AbstractScript.sleepUntil(() -> context.getBank().isOpen(), 9000)) {
                context.getBank().depositAllExcept(item -> item.getName().contains("axe"));
                AbstractScript.sleepUntil(() -> !context.getInventory().contains("Logs"), 3000);
                if (context.getBank().close()) {
                    AbstractScript.sleepUntil(() -> !context.getBank().isOpen(), 8000);
                }
            }
        }
        AbstractScript.sleep(200);
    }

    public static void OpenTab(AbstractScript context, Tab tab) {
        if (!context.getTabs().isOpen(tab)) {
            context.getTabs().openWithMouse(tab);
            AbstractScript.sleepUntil(() -> context.getTabs().isOpen(tab), 2000);
        }
    }

    public static void setStandingStillToZero() {
        standingStill = 0;
    }

    public static boolean isStandingStill(AbstractScript context) {

        if (!context.getLocalPlayer().isAnimating()) {
            standingStill++;
        } else {
            standingStill = 0;
        }

        return standingStill > 35;
    }
}
