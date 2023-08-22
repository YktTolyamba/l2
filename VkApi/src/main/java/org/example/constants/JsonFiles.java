package org.example.constants;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public final class JsonFiles {
    public static final ISettingsFile CONFIG = new JsonSettingsFile("config.json");
    public static final ISettingsFile TESTDATA = new JsonSettingsFile("testdata.json");
}
