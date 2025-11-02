package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {

    protected JTextArea textArea;
    ActionListener actionListener;

    public DrawingToolBar(ActionListener actionListener) {
        setFloatable(false);
        setRollover(true);
        this.actionListener = actionListener;
        addButtons();

        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        //Lay out the main panel.
        //setPreferredSize(new Dimension(200, 30));
        //setBackground(Color.BLUE);
    }

    protected void addButtons() {
        JButton button = null;

        // Undo and Redo buttons
        button = makeNavigationButton("Undo", ActionCommand.UNDO, "Undo last action", "Undo");
        add(button);

        button = makeNavigationButton("Redo", ActionCommand.REDO, "Redo last action", "Redo");
        add(button);

        addSeparator();

        // Drawing tools
        button = makeNavigationButton("rect", ActionCommand.RECT, "Draw a rectangle", ActionCommand.RECT);
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line", ActionCommand.LINE);
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE, "Draw an ellipse", ActionCommand.ELLIPSE);
        add(button);

        button = makeNavigationButton("text", ActionCommand.TEXT, "Add a text", ActionCommand.TEXT);
        add(button);

        button = makeNavigationButton("image", ActionCommand.IMAGE, "Add an image", ActionCommand.IMAGE);
        add(button);

        button = makeNavigationButton("select", ActionCommand.SELECT, "Switch to select", ActionCommand.SELECT);
        add(button);

        addSeparator();

        // Property buttons
        button = makeNavigationButton("Color", ActionCommand.COLOR, "Choose color", "Color");
        add(button);

        button = makeNavigationButton("Fill", ActionCommand.FILL, "Toggle fill", "Fill");
        add(button);

        addSeparator();

        button = makeNavigationButton("imagefile", ActionCommand.IMAGEFILE, "Select another image", ActionCommand.IMAGEFILE);
        add(button);

        button = makeNavigationButton("font", ActionCommand.FONT, "Select another font", ActionCommand.FONT);
        add(button);

        //separator
        addSeparator();

        //fourth button
       /* button = new JButton("Another button");
        button.setActionCommand("SOMETHING_ELSE");
        button.setToolTipText("Something else");
        button.addActionListener(actionListener);
        add(button);*/

        //fifth component is NOT a button!
        /*JTextField textField = new JTextField("");
        textField.setColumns(10);
        textField.addActionListener(actionListener);
        textField.setActionCommand("TEXT_ENTERED");
        add(textField);*/

        //delete button
        button = new JButton("Delete");
        button.setActionCommand("DELETE");
        button.setToolTipText("Delete Selected Shape");
        button.addActionListener(actionListener);
        add(button);

    }

    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        //Look for the image.
        String imgLocation = "images/"
                + imageName
                + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(actionListener);

        if (imageURL != null) {                      //image found
            ImageIcon icon = new ImageIcon(imageURL, altText);
            // Scale the icon to a reasonable size (24x24 pixels)
            Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }
        return button;
    }
}