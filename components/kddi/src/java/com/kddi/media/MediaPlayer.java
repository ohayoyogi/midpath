package com.kddi.media;

import java.util.Vector;

public abstract class MediaPlayer implements MediaPlayerInterface {
    protected static final int INVALID_PLAYER = -1;

    protected int _id;
    protected int _pitch;
    protected int _tempo;
    protected int _volume;

    protected MediaPlayerBox _box;
    protected MediaEventListener _listener;
    protected MediaResource _resource;
 
    protected MediaPlayer(MediaResource resource, MediaPlayerBox box) {
        this._resource = resource;
        this._box = box;
    }

    
    // @Override
    public void setResource(MediaResource resource) {
        this._resource = resource;
    }

    // @Override
    public void unsetResource(MediaResource resource) {
        if (resource != null) {
            // playerの紐づけ解除
        }
        this._resource = null;
    }

    // @Override
    public MediaResource getResource() {
        return this._resource;
    }

    // @Override
    public void addMediaEventListener(MediaEventListener l) {
        this._listener = l;
    }

    // @Override
    public void removeMediaEventListener(MediaEventListener l) {
    }
    
    // @Override
    public void play() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'play'");
    }

    // @Override
    public void play(int count) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'play'");
    }

    // @Override
    public void stop() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'stop'");
    }

    // @Override
    public void pause() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'pause'");
    }

    // @Override
    public void resume() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'resume'");
    }

    // @Override
    public void setAttribute(int attr, int value) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'setAttribute'");
    }

    // @Override
    public int getAttribute(int attr) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'getAttribute'");
        return 0;
    }

    // @Override
    public void setTempo(int tempo) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'setTemp'");
    }

    // @Override
    public int getTempo() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'getTempo'");
        return 0;
    }

    // @Override
    public void setPitch(int pitch) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'setPitch'");
    }

    // @Override
    public int getPitch() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'getPitch'");
        return 0;
    }

    // @Override
    public void setVolume(int volume) {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'setVolume'");
    }

    // @Override
    public int getVolume() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'getVolume'");
        return 0;
    }

    // @Override
    public void show() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'show'");
    }

    // @Override
    public void hide() {
        // TODO Auto-generated method stub
        System.out.println("Unimplemented method 'hide'");
    }

    /**
     * Check the media type can be played with this class.
     * @param dataType media type
     * @return capability
     */
    public static boolean canPlay(java.lang.String dataType) {
        return false;
    }

    protected final void validateId() {
        System.out.println("Unimplemented method 'validateId'");
    }

    protected final void notifyEvent(MediaPlayer player, int type, int option) {
        if (this._listener != null) {
                this._listener.stateChanged(_box, type, option);
        }
    }

    protected void paint(javax.microedition.lcdui.Graphics g) {

    }

    protected abstract boolean disposePlayer();

    protected abstract void dispose();
     
    protected abstract class EventWatcher implements Runnable {
        protected Vector _players;

        protected EventWatcher() {
            this._players = new Vector();
        }

        public void run() {
        }

        protected void add(MediaPlayer player) {
            this._players.addElement(player);
        }

        protected int remove(MediaPlayer player) {
            this._players.removeElement(player);
            return this._players.size();
        }

        protected abstract void deliverEvent(MediaPlayer.MediaPlayerEvent event);

        protected abstract boolean fetchEvent(MediaPlayer.MediaPlayerEvent event);

        protected void dispose() {

        }

        protected void isDisposing() {
        }

        protected Vector getPlayers() {
            return this._players;
        }
    } 

    protected class MediaPlayerEvent {
        protected MediaPlayerEvent() {

        }
    }
}
