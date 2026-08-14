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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import org.ohayoyogi.media.BaseMediaResource;
import org.ohayoyogi.media.SMAFResource;
import org.ohayoyogi.media.StubMediaResource;

// import org.ohayoyogi.media.MMFConverter;

// 13KQcelp (.qcp)
// SMAF(.mmf)
public class MediaResource {

    private static final boolean stub = false;

    public static final String SMAF_YAMAHA_MA1 = "dev4anm";
    public static final String SMAF_YAMAHA_MA2 = "devmfan";
    public static final String SMAF_YAMAHA_MA3 = "devm39z";
    public static final String SMAF_YAMAHA_MA5 = "devm53z";

    private Player player;
    private String type;

    private BaseMediaResource _resource;

    public MediaResource(String url) {
        this.player = null;
        this.type = "devm39z";

        // String path = url.replace("resource://", "");
        InputStream stream = getClass().getResourceAsStream(url);
        ByteArrayOutputStream baos = null;
        try {
            byte[] buff = new byte[1024 * 8];
            int len;
            baos = new ByteArrayOutputStream();
            while ((len = stream.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            if (stub) {
                _resource = new StubMediaResource();
            } else {
                _resource = new SMAFResource(baos.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MediaResource(byte[] resource, String disposition) {
        System.out.println("MediaResource(byte, String)");

        this.player = null;
        this.type = disposition;
        if (stub) {
            this._resource = new StubMediaResource();
        } else {
            if (disposition.equals("dev4anm")
                    || disposition.equals("devmfan")
                    || disposition.equals("devm39z")
                    || disposition.equals("devm53z")) {
                this._resource = new SMAFResource(resource);
            }
        }
        System.out.println("MediaResource(byte, String) end");
    }

    public MediaPlayerBox[] getPlayer() {
        return new MediaPlayerBox[] {};
    }

    public String getType() {
        return this.type;
    }

    public void dispose() {
        if (this.player != null) {
            this.player.close();
            this.player = null;
        }
        if (this._resource != null) {
            _resource.dispose();
        }
    }

    // @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Original code
     */
    public int getInternalId() {
        return this._resource.getId();
    }
}
