package org.example.utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.logging.LocalizedLoggerUtility;
import aquality.selenium.logging.LogLevel;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static org.example.utils.MyConstants.*;

public class RobotUtils {
    private ISettingsFile config = new JsonSettingsFile("config.json");
    private String imageName = config.getValue(IMAGE_NAME_KEY).toString();
    private int delayTime = (int) config.getValue(ROBOT_DELAY_TIME_KEY);

    public void upload() {
        StringSelection s = new StringSelection(FilePathUtils.getStringPath(imageName));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s,null);

        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            LocalizedLoggerUtility.logByLevel(LogLevel.ERROR, "Error occurred while creating Robot instance!");
            throw new RuntimeException(e);
        }
        r.delay(delayTime);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);

    }
}
