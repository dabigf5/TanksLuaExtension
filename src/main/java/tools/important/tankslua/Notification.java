package tools.important.tankslua;

import tanks.Drawing;
import tanks.Game;
import tanks.Panel;
import tanks.gui.screen.Screen;

import java.awt.Color;
import java.util.HashMap;

/**
 * A class used to send the player a notification.
 */
public class Notification {
    private int notifIndex;
    public double totalSeconds;
    public double secondsLeft;
    public String text;
    enum NotificationType {
        INFO,
        WARN,
    }
    private enum NotificationState {
        FOCUSED,
        IDLE,
    }
    public NotificationType type;

    /**
     * Constructing a Notification will immediately cause that notification to pop up on the player's screen.
     * @param notificationType The type of notification. This changes the color of the notification as well as the sound played.
     * @param seconds The amount of seconds the notification will stay active.
     * @param text The text of the notification
     */
    public Notification(NotificationType notificationType, double seconds, String text) {
        this.type = notificationType;
        this.secondsLeft = seconds;
        this.totalSeconds = secondsLeft;
        this.text = text;

        TanksLua.tanksLua.activeNotifications.add(this);
        this.notifIndex = TanksLua.tanksLua.activeNotifications.size();

        if (Game.game.window == null) return;
        switch(this.type) { // I FUCKING DESPISE JAVA 8. WHY DO WE CARE SO MUCH ABOUT MOBILE USERS, THAT WE FORCE OURSELVES TO WRITE CODE FOR THIS WRETCHED JAVA VERSION THAT IS COMPOSED OF 50% FECES 49% URINE AND 1% C CODE
            case INFO: {
                Drawing.drawing.playSound("join.ogg", 1.5f);
                break;
            }
            case WARN: {
                Drawing.drawing.playSound("leave.ogg", 1.25f);
                break;
            }
        }
    }
    private boolean mousing;

    private double alpha = 255;
    private static final HashMap<NotificationType, Color> typeBackgroundColors = new HashMap<>();
    static {
        typeBackgroundColors.put(NotificationType.INFO, new Color(255,255,255));
        typeBackgroundColors.put(NotificationType.WARN, new Color(255, 215, 118));
    }
    public void update() {
        if (removing) return;
        notifIndex = TanksLua.tanksLua.activeNotifications.indexOf(this)+1;
        if (mousing) return;
        secondsLeft -= Math.min(0.01, Panel.frameFrequency/125);
        if (secondsLeft <= 0) removing = true;
    }

    public boolean removing;
    public void draw() {
        if (removing) return;
        double notifRemovalProgress = (secondsLeft/totalSeconds);
        alpha = (secondsLeft/totalSeconds)*255;
        Screen screen = Game.screen;

        Color color = typeBackgroundColors.get(this.type);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        Drawing.drawing.setColor(r,g,b,alpha);

        double width = screen.objWidth*1.3;
        double height = screen.objHeight*3;

        double x = screen.centerX*2.3-width;
        double y = screen.centerY*2.1-(height*(notifIndex *1.1));

        Drawing.drawing.fillInterfaceRect(x, y, width, height);
        Drawing.drawing.setColor(0,0,0, alpha);

        Drawing.drawing.setInterfaceFontSize(screen.titleSize/(this.text.length()/23d));
        Drawing.drawing.displayInterfaceText(x, y, this.text);

        double mx = Drawing.drawing.getInterfaceMouseX();
        double my = Drawing.drawing.getInterfaceMouseY();
        double mouseBoxSize = 10;
        double x2 = x-width/2;
        double y2 = y-height/2;

        this.mousing = mx < x2+width && x2 < mx+mouseBoxSize && my < y2+height && y2 < my+mouseBoxSize;

        Drawing.drawing.setColor(r*0.6,g*0.9,b*0.6, Math.min(alpha*1.3, 255));

        double progressBarHeight = height/20;
        Drawing.drawing.fillInterfaceProgressRect(x, y-(height/2)+(progressBarHeight/2), width, progressBarHeight, notifRemovalProgress);
    }
}
