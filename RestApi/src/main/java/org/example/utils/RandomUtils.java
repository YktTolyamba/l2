package org.example.utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.apache.commons.lang3.RandomStringUtils;

import static org.example.utils.MyConstants.*;

public class RandomUtils {
    private static ISettingsFile config = new JsonSettingsFile("config.json");
    private static int start = Integer.parseInt(config.getValue(RANDOM_START_KEY).toString());
    private static int end = Integer.parseInt(config.getValue(RANDOM_END_KEY).toString());

    public static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(start, end);
    }

}
