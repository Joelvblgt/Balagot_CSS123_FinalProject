package com.gabriel.fontchooser;

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import com.gabriel.fontchooser.FontChooser;
import com.gabriel.fontchooser.model.FontSelectionModel;

public class PanelExample implements Runnable {

    private final JLabel selection = new JLabel("Selected font will be displayed here");

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        com.gabriel.fontchooser.ExampleRunner.useLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        com.gabriel.fontchooser.ExampleRunner.invoke(new PanelExample());
    }

    @Override
    public void run() {
        selection.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        FontChooser fontChooser = new FontChooser();
        fontChooser.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fontChooser.addChangeListener(event -> {
            FontSelectionModel model = (FontSelectionModel) event.getSource();
            selection.setText(model.getSelectedFont().toString());
        });

        JFrame frame = new JFrame("Select Font");
        frame.setSize(600, 400);
        frame.add(fontChooser);
        frame.add(selection, BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
