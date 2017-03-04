package com.toliga.ganjabots.graphics;

import com.toliga.ganjabots.core.Utilities;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

import java.awt.*;

public class InGameGUIBuilder {
    protected AbstractScript context;
    private final int titleY = 330;
    private Image backgroundImage;
    private Image ganjaIcon;
    private String version;
    private String botName;
    private Timer timer;
    private boolean canDraw = false;
    private Drawable drawable;
    private Color color;

    public InGameGUIBuilder(AbstractScript context, String botName, String version, Color color, Drawable drawable) {
        this.context = context;
        this.version = version;
        this.drawable = drawable;
        this.botName = botName;
        this.color = color;
        timer = new Timer();

        backgroundImage = Utilities.LoadImage("http://i63.tinypic.com/2j48v94.png", 230, 111);
        ganjaIcon = Utilities.LoadImage("http://ai-i1.infcdn.net/icons_siandroid/png/200/1138/1138962.png", 20, 20);
    }

    public void draw(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setFont(new Font("Magneto", Font.BOLD, 15));
        graphics.setColor(color);
        graphics.drawString(botName, 440 - graphics.getFontMetrics().stringWidth(botName) - 10, titleY); // x = 284

        graphics.setFont(new Font("Consolas", Font.PLAIN, 15));
        graphics.setColor(Color.BLACK);

        graphics.drawString("v" + version, 440, titleY);
        graphics.drawImage(ganjaIcon, 490, 312, null);

        if (canDraw) {
            graphics.drawImage(backgroundImage, 264, 347, null);
            graphics.drawString("        Run Time:", 280, 365); // +17
            graphics.drawString(timer.formatTime(), 420, 365); // Runtime

            if (drawable != null) {
                drawable.draw(graphics);
            }
        }
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean getCanDraw() {
        return canDraw;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
