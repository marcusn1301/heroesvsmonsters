package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class SoundManager implements Disposable {
    private Map<String, Sound> soundMap;
    private Map<String, Music> musicMap;

    public SoundManager() {
        soundMap = new HashMap<>();
        musicMap = new HashMap<>();

        loadSounds();
        loadMusic();
    }

    private void loadSounds() {
        soundMap.put("collectPoint", Gdx.audio.newSound(Gdx.files.internal("sfx/Collect_Point_01.mp3")));
        soundMap.put("heroDeath", Gdx.audio.newSound(Gdx.files.internal("sfx/Hero_Death_00.mp3")));
        soundMap.put("hit1", Gdx.audio.newSound(Gdx.files.internal("sfx/Hit_01.mp3")));
        soundMap.put("hit2", Gdx.audio.newSound(Gdx.files.internal("sfx/Hit_02.mp3")));
        soundMap.put("hit3", Gdx.audio.newSound(Gdx.files.internal("sfx/Hit_03.mp3")));
        soundMap.put("jingleLose", Gdx.audio.newSound(Gdx.files.internal("sfx/Jingle_Lose.mp3")));
        soundMap.put("jingleWin", Gdx.audio.newSound(Gdx.files.internal("sfx/Jingle_Win.mp3")));
        soundMap.put("menuNavigate", Gdx.audio.newSound(Gdx.files.internal("sfx/Menu_Navigate.mp3")));
        soundMap.put("shoot0", Gdx.audio.newSound(Gdx.files.internal("sfx/Shoot_00.mp3")));
        soundMap.put("shoot1", Gdx.audio.newSound(Gdx.files.internal("sfx/Shoot_01.mp3")));
        soundMap.put("shoot2", Gdx.audio.newSound(Gdx.files.internal("sfx/Shoot_02.mp3")));
        soundMap.put("shoot3", Gdx.audio.newSound(Gdx.files.internal("sfx/Shoot_03.mp3")));
    }

    private void loadMusic() {
        musicMap.put("actionTheme", Gdx.audio.newMusic(Gdx.files.internal("music/actiontheme.mp3")));
        musicMap.put("avengersHype", Gdx.audio.newMusic(Gdx.files.internal("music/Avengers_Hype.mp3")));
        musicMap.put("avengers", Gdx.audio.newMusic(Gdx.files.internal("music/Avengers.mp3")));
        musicMap.put("portalsHype", Gdx.audio.newMusic(Gdx.files.internal("music/Portals_Hype.mp3")));
    }

    public void playSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            soundMap.get(soundName).play();
        }
    }

    public void playMusic(String musicName, boolean loop) {
        if (musicMap.containsKey(musicName)) {
            Music music = musicMap.get(musicName);
            music.setLooping(loop);
            music.play();
        }
    }

    public void stopMusic(String musicName) {
        if (musicMap.containsKey(musicName)) {
            musicMap.get(musicName).stop();
        }
    }

    @Override
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }

        for (Music music : musicMap.values()) {
            music.dispose();
        }
    }
}
