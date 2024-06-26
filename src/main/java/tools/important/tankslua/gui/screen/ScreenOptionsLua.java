package tools.important.tankslua.gui.screen;

import tanks.Drawing;
import tanks.Game;
import tanks.Panel;
import tanks.gui.Button;
import tanks.gui.screen.Screen;
import tanks.gui.screen.ScreenOptions;
import tools.important.tankslua.TanksLua;
import tools.important.tankslua.gui.ToggleOptionButton;

public class ScreenOptionsLua extends Screen {
    public Button backButton;
    public Button extensionListButton;

    public Button enableLevelScriptsButton;
    public Button enableEvalBoxButton;

    public ScreenOptionsLua() {
        this.music = "menu_options.ogg";
        this.musicID = "menu";
        Panel.forceRefreshMusic = true;

        backButton = new Button(centerX, centerY + objYSpace * 3.5, objWidth, objHeight, "Back", () -> Game.screen = new ScreenOptions());

        enableLevelScriptsButton = new ToggleOptionButton(centerX, centerY - objHeight * 3, objWidth, objHeight, "Level scripts",
                () -> TanksLua.tanksLua.setOptionValue("enableLevelScripts", true),
                () -> TanksLua.tanksLua.setOptionValue("enableLevelScripts", false),
                (boolean) TanksLua.tanksLua.getOptionValue("enableLevelScripts"),
                "Whether or not the extension should run scripts for levels that have them---" +
                        "in the .tanks/scripts directory.---" +
                        "---" +
                        "Please note that turning this off may cause problems with some levels---" +
                        "that depend on scripts to function properly!");
        enableEvalBoxButton = new ToggleOptionButton(centerX, centerY - objHeight * 1.7, objWidth, objHeight, "Evaluation Box",
                () -> TanksLua.tanksLua.setOptionValue("enableEvalBox", true),
                () -> TanksLua.tanksLua.setOptionValue("enableEvalBox", false),
                (boolean) TanksLua.tanksLua.getOptionValue("enableEvalBox"),
                "If the extension should show a box at the top of the screen---" +
                        "that lets you evaluate Lua code at will.---" +
                        "---" +
                        "Note that it may block off certain UI elements sometimes," +
                        "---" +
                        "so you shouldn't leave it on all the time!");


        extensionListButton = new Button(centerX, centerY + objHeight * 4, objWidth, objHeight, "Loaded extension list", () -> Game.screen = new ScreenOptionsLuaExtensionList());
    }

    @Override
    public void update() {
        backButton.update();

        // ordering doesn't matter too much here
        enableLevelScriptsButton.update();
        enableEvalBoxButton.update();

        extensionListButton.update();
    }

    @Override
    public void draw() {
        drawDefaultBackground();
        backButton.draw();

        // make sure this ordering goes from lowest in ui to highest in ui
        enableEvalBoxButton.draw();
        enableLevelScriptsButton.draw();
        extensionListButton.draw();

        Drawing.drawing.setColor(0, 0, 0);
        Drawing.drawing.setInterfaceFontSize(titleSize);
        Drawing.drawing.displayInterfaceText(centerX, centerY - objYSpace * 3.5, "Lua Options");
        Drawing.drawing.setInterfaceFontSize(titleSize/2);
        Drawing.drawing.displayInterfaceText(centerX, centerY - objYSpace * 3, TanksLua.VERSION);
    }
}
