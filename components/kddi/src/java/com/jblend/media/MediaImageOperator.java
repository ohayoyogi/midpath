package com.jblend.media;

public interface MediaImageOperator {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void setBounds(int x, int y, int width, int height);
    int getOriginX();
    int getOriginY();
    void setOrigin(int offset_x, int offset_y);
    int getMediaWidth();
    int getMediaHeight();
}
