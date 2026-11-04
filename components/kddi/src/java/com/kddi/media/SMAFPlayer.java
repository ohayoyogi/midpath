package com.kddi.media;

import org.ohayoyogi.media.BaseMediaPlayer;
import org.ohayoyogi.media.StubMediaPlayer;

public class SMAFPlayer extends MediaPlayer {
    BaseMediaPlayer _player;

    protected SMAFPlayer(MediaResource resource, MediaPlayerBox box) {
        super(resource, box);
        // SMAF is not synthesized: play silently so the MIDlet keeps running.
        this._player = new StubMediaPlayer();
    }

    // @Override
    public void play() {
        _player.play(0);
    }

    // @Override
    public void play(int count) {
        System.out.println("SMAFPlayer: play");
        _player.play(count);
    }

    public void stop() {
        System.out.println("SMAFPlayer: stop");
        _player.stop();
    }

    // @Override
    public void setResource(MediaResource resource) {
        _player.setResource(resource.getInternalId());
    }

    // @Override
    public void unsetResource(MediaResource resource) {
        System.out.println("SMAFPlayer#unsetResource " + resource);
        if (resource != null) {
            _player.unsetResource(resource.getInternalId());
        }
    }

    // @Override
    protected boolean disposePlayer() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'disposePlayer'");
        return true;
    }

    // @Override
    protected void dispose() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'dispose'");
    }

    protected class SMAFEventWatcher extends MediaPlayer.EventWatcher {

        // @Override
        protected void deliverEvent(MediaPlayerEvent event) {
            // TODO Auto-generated method stub
            System.out.println("Unimplemented method 'deliverEvent'");
        }

        // @Override
        protected boolean fetchEvent(MediaPlayerEvent event) {
            // TODO Auto-generated method stub
            System.out.println("Unimplemented method 'fetchEvent'");
            return true;
        }

    }
}
