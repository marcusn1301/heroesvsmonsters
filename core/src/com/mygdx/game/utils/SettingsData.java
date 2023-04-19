package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SettingsData {
    public int AUDIO;
    public int SFX;
    private static final SettingsData settingsData = initSettingsData();

    private SettingsData() {

    }

    private static SettingsData initSettingsData() {
        FileHandle localFileHandle = Gdx.files.local("files/settings.json");
        if (localFileHandle.exists()) {
            String fileString = localFileHandle.readString();
            Json json = new Json();
            return json.fromJson(SettingsData.class, fileString);
        } else {
            SettingsData newSettingsdata = new SettingsData();
            newSettingsdata.AUDIO = 100;
            newSettingsdata.SFX = 100;
            return newSettingsdata;
        }
    }

    public static SettingsData loadSettings () {
        return settingsData;
    }
    public void saveSettings () {
        Json json = new Json();
        String saveString = json.toJson(this);
        FileHandle localFileHandle = Gdx.files.local("files/settings.json");
        localFileHandle.writeString(saveString, false);
    }
}
