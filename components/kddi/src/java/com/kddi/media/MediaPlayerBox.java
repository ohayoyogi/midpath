/*
 * Copyright 2021 ohayoyogi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kddi.media;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class MediaPlayerBox extends Canvas implements MediaPlayerInterface {

    public static final int FOREGROUND = 0;
    public static final int BACKGROUND = 1;

    public static final int RESOURCE_DISPOSED = 0;
    public static final int PLAY = 1;
    public static final int STOP = 2;
    public static final int PAUSE = 3;
    public static final int RESUME = 4;

    private int state = STOP;
    private int mode = BACKGROUND;

    private MediaResource _resource;
    private MediaPlayer _player;

    protected MediaEventListener _listener;

    public MediaPlayerBox() {
        this(null, FOREGROUND);
    }

    public MediaPlayerBox(int flag) {
        this(null, flag);
    }

    public MediaPlayerBox(MediaResource resource, int flag) {
        // super();
        System.out.println("MediaPlayerBox: flag=" + flag );
        if (flag != FOREGROUND && flag != BACKGROUND) {
            throw new IllegalArgumentException("illegal flag[" + flag + "].");
        }

        this._resource = null;
        this._player = null;

        if (resource != null) {
            _resource = resource;
            _player = instantiatePlayer(resource);    
        }
    }

    // @Override
    public void setResource(MediaResource resource) {
        System.out.println("MediaPlayerBox#setResource");
        if (state != STOP) {
            System.out.println("State must be STOP");
            throw new IllegalStateException("state must be STOP.");
        }
        if (this._resource != null)
            throw new IllegalStateException("resource must be unset before set.");
        if (resource == null)
            throw new NullPointerException();

        // Fixme: check the resource can be played.
        this._resource = resource;
        if (_player == null) {
            this._player = instantiatePlayer(resource);
        }
        this._player.setResource(_resource);
    }

    // @Override
    public void unsetResource(MediaResource resource) {
        System.out.println("MediaPlayerBox#unsetResource");
        if (this._player == null) return;
        if (state == STOP) {
            this._resource = null;
            this._player.unsetResource(_resource);
        }
    }

    // @Override
    public MediaResource getResource() {
        System.out.println("MediaPlayerBox#getResource");
        return this._resource;
    }

    // @Override
    public void addMediaEventListener(MediaEventListener l) {
        if (l == null) {
            removeMediaEventListener(this._listener);
        } else {
            this._listener = l;
        }
    }

    // @Override
    public void removeMediaEventListener(MediaEventListener l) {
        if (l == this._listener) {
            this._listener = null;
        }
    }

    // @Override
    public void play() {
        System.out.println("MediaPlayerBox#play");
        this._player.play();
        this.state = PLAY;
    }

    // @Override
    public void play(int count) {
        System.out.println("MediaPlayerBox#play");
        this._player.play(count);
        this.state = PLAY;
    }

    // @Override
    public void stop() {
        System.out.println("MediaPlayerBox#stop");
        if (this._player != null) {
            this._player.stop();
            this.state = STOP;    
        }
        System.out.println("MediaPlayerBox#stop end");
    }

    // @Override
    public void pause() {
        this._player.pause();
        this.state = PAUSE;
    }

    // @Override
    public void resume() {
        this._player.resume();
        this.state = RESUME;
    }

    // @Override
    public void setAttribute(int attr, int value) {
        this._player.setAttribute(attr, value);
    }

    // @Override
    public int getAttribute(int attr) {
        return this._player.getAttribute(attr);
    }

    // @Override
    public int getTempo() {
        return this._player.getTempo();
    }

    // @Override
    public void setTempo(int tempo) {
        this._player.setTempo(tempo);
    }

    // @Override
    public void setPitch(int pitch) {
        this._player.setPitch(pitch);
    }

    // @Override
    public int getPitch() {
        return this._player.getPitch();
    }

    // @Override
    public void setVolume(int volume) {
        this._player.setVolume(volume);
    }

    // @Override
    public int getVolume() {
        return this._player.getVolume();
    }

    // @Override
    public void show() {
        this._player.show();
    }

    // @Override
    public void hide() {
        this._player.hide();
    }

    // @Override
    protected void showNotify() {
        // TODO Auto-generated method stub
        super.showNotify();
    }

    // @Override
    protected void hideNotify() {
        // TODO Auto-generated method stub
        super.hideNotify();
    }

    protected MediaPlayer instantiatePlayer(MediaResource resource) {
        System.out.println("instantiatePlayer");
        if (resource.getType() == MediaResource.SMAF_YAMAHA_MA1
                || resource.getType() == MediaResource.SMAF_YAMAHA_MA2
                || resource.getType() == MediaResource.SMAF_YAMAHA_MA3
                || resource.getType() == MediaResource.SMAF_YAMAHA_MA5) {
            return new SMAFPlayer(resource, this);
        } else {
            throw new IllegalArgumentException("Resource type [" + resource.getType() + "] is not supported");
        }
    }

    protected MediaPlayer getPlayer() {
        return this._player;
    }

    protected int getMode() {
        return this.mode;
    }

    protected void setPlayerAttributes() {
        // set all player attributes on instantiate
    }

    // @Override
    protected void paint(Graphics g) {
    }
}