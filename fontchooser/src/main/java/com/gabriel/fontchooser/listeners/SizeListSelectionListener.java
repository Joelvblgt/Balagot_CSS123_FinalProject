package com.gabriel.fontchooser.listeners;

import java.awt.Font;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.RequiredArgsConstructor;
import com.gabriel.fontchooser.FontContainer;


@RequiredArgsConstructor
public class SizeListSelectionListener implements ListSelectionListener {

    private final FontContainer fontContainer;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            float newSize = fontContainer.getSelectedSize();
            Font newFont = fontContainer.getSelectedFont().deriveFont(newSize);
            fontContainer.setSelectedFont(newFont);
            fontContainer.setPreviewFont(fontContainer.getSelectedFont());
        }
    }
}
