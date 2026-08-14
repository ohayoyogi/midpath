package org.ohayoyogi.media;

import org.ohayoyogi.libretro.LibRetroBridge;

public class SMAFResource extends BaseMediaResource {
    private int _id;
    static LibRetroBridge libretro;

    static {
        libretro = LibRetroBridge.getInstance();
    }

    public SMAFResource(byte[] data) {
        _id = libretro.createSMAFData(data);
    }

    public void dispose() {
        libretro.disposeSMAFData(_id);
    }

    public int getId() {
        return this._id;
    }
}
