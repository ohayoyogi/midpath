package org.ohayoyogi.media;

import org.ohayoyogi.libretro.LibRetroBridge;

public class SMAFPlayer implements BaseMediaPlayer {
    static LibRetroBridge libretro;
    private int _id;

    static {
        libretro = LibRetroBridge.getInstance();
    }

    public SMAFPlayer() {
        _id = libretro.createSMAFPlayer();
    }

    public void play(int count) {
        System.out.println("SMAFPlayer: play " + count + ", " + _id);
        libretro.startSMAFPlayer(_id);
    }

    public void stop() {
        libretro.stopSMAFPlayer(_id);
    }

    public void pause() {
        libretro.pauseSMAFPlayer(_id);
    }

    public void resume() {
        libretro.resumeSMAFPlayer(_id);
    }

    public void setResource(int resourceId) {
        System.out.println("SMAFPlayer: setResource " + _id + ", " + resourceId);
        libretro.setSMAFPlayerContent(_id, resourceId);
    }

    public void unsetResource(int resourceId) {
        System.out.println("SMAFPlayer: unsetResource " + _id + ", " + resourceId);
        // FixMe
    }
}
