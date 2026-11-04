package com.jblend.media.smaf;

import com.jblend.media.MediaData;

public class SmafData extends MediaData {
    public SmafData() {
    }
    public SmafData(byte[] data) {
    }
    public SmafData(String name) {
    }
    public int getContentType() {
        // please refer to SMAF specification
        return 0;
    }
    public int getTagStart(int tag) {
        return -1;
    }
    public int getTagEnd(int tag) {
        return -1;
    }
    public String getMediaType() {
        return "smaf";
    }
    public void setData(byte[] data) {
    }
    public int getWidth() {
        return 0;
    }
    public int getHeight() {
        return 0;
    }
}
