package org.ohayoyogi.media;

public interface BaseMediaPlayer {

    void play(int count);
    void stop();
    void pause();
    void resume();
    void setResource(int resourceId);
    void unsetResource(int resourceId);
}
