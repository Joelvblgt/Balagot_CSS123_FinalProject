package com.gabriel.fontchooser;

import java.awt.Font;


public interface FontContainer {

    String getSelectedStyle();

    float getSelectedSize();

    String getSelectedFamily();

    Font getSelectedFont();

    void setSelectedFont(Font font);

    void setPreviewFont(Font font);

    void setPreviewText(String text);

    String getPreviewText();

}
