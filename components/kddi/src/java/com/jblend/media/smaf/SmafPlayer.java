package com.jblend.media.smaf;

import com.jblend.media.MediaData;
import com.jblend.media.MediaImageOperator;
import com.jblend.media.MediaPlayer;

public class SmafPlayer extends MediaPlayer implements MediaImageOperator {

    public SmafPlayer() {
    }

    public SmafPlayer(SmafData data) {
    }

    public SmafPlayer(byte[] data) {
    }

    public void setData(SmafData data) {
        if (this.getState() == MediaPlayer.PLAYING ||
        this.getState() == MediaPlayer.PAUSED) {
            throw new IllegalStateException("player state is [" + this.getState() + "]");
        }
    }

    // @Override
    public void setData(MediaData data) {
        if (!(data instanceof SmafData)) {
            throw new IllegalArgumentException("data is not SmafData");
        }
        this.setData((SmafData)data);
    }

    public int getCurrent() {
        return 0;
    }

    public void seek(int time) {
        // milliseconds

    }
    
    public void setTranspose(int shift) {
        if (shift < -12 || shift > 12) {
            throw new IllegalArgumentException("shift must be less than 12 and greater than -12");
        }
    }

    public int getTranspose() {
        return 0;
    }

    public int getVolume() {
        return 0;
    }

    public void setVolume(int volume) {
        if (volume < 0 || volume > 127) {
            throw new IllegalArgumentException("volume must be less than 127 and greater than 0");
        }
    }

    public void setPlayEnd(int pos) {
        // milliseconds
    }

    public int getPlayEnd() {
        return -1;
    }

    // @Override
    public void setBounds(int x, int y, int width, int height) {
        System.out.println("Stub method 'setBounds'");
    }

    // public void addSmafPlayerListener(SmafPlayerListener l) {
    // }

    // public void removeSmafPlayerListener(SmafPlayerListener l) {
    // }

    // @Override
    public int getX() {
        System.out.println("Stub method 'getX'");
        return 0;
    }

    // @Override
    public int getY() {
        System.out.println("Stub method 'getY'");
        return 0;
    }

    // @Override
    public int getWidth() {
        System.out.println("Stub method 'getWidth'");
        return 0;
    }

    // @Override
    public int getHeight() {
        System.out.println("Stub method 'getHeight'");
        return 0;
    }

    // @Override
    public int getOriginX() {
        System.out.println("Stub method 'getOriginX'");
        return 0;
    }

    // @Override
    public int getOriginY() {
        System.out.println("Stub method 'getOriginY'");
        return 0;
    }

    // @Override
    public void setOrigin(int offset_x, int offset_y) {
        System.out.println("Stub method 'setOrigin'");
    }

    // @Override
    public int getMediaWidth() {
        System.out.println("Stub method 'getMediaWidth'");
        return 0;
    }

    // @Override
    public int getMediaHeight() {
        System.out.println("Stub method 'getMediaHeight'");
        return 0;
    }

    // @Override
    public void play() {
        if (this.getState() != MediaPlayer.READY) {
            throw new IllegalStateException("state is not READY");
        }
        System.out.println("Stub method 'play'");
    }

    // @Override
    public void play(boolean isRepeat) {
        if (this.getState() != MediaPlayer.READY) {
            throw new IllegalStateException("state is not READY");
        }
        System.out.println("Stub method 'play'");
    }

    // @Override
    public void play(int count) {
        if (this.getState() != MediaPlayer.READY) {
            throw new IllegalStateException("state is not READY");
        }
        System.out.println("Stub method 'play'");
    }

    // @Override
    public void stop() {
        System.out.println("Stub method 'stop'");
    }

    // @Override
    public void pause() {
        System.out.println("Stub method 'pause'");
    }

    // @Override
    public void resume() {
        System.out.println("Stub method 'resume'");
    }

    // @Override
    public int getState() {
        System.out.println("Stub method 'getState'");
        return 0;
    }

    
}
