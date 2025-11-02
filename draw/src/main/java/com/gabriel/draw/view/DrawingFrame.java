package com.gabriel.draw.view;

import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.service.DrawingCommandAppService;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;
import com.gabriel.draw.command.*;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.command.CommandService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DrawingFrame extends JFrame {

    Drawing drawing;
    DrawingAppService drawingAppService;
    AppService appService;
    DrawingFrame drawingFrame;
    Container pane;
    private PropertySheet propertySheet;
    ActionController actionListener;
    DrawingMenuBar drawingMenuBar;
    DrawingToolBar drawingToolBar;
    DrawingView drawingView;
    DrawingController drawingController;
    JScrollPane jScrollPane;
    DrawingStatusPanel drawingStatusPanel;
    DrawingWindowController drawingWindowController;
    public DrawingFrame() {

        drawing = new Drawing();
        drawingAppService = new DrawingAppService();
        appService = DrawingCommandAppService.getInstance(drawingAppService);

        pane = getContentPane();
        setLayout(new BorderLayout());

        actionListener = new ActionController(appService);
        actionListener.setFrame(this);
        drawingMenuBar = new DrawingMenuBar( actionListener);

        setJMenuBar(drawingMenuBar);

        drawingMenuBar.setVisible(true);


        drawingToolBar = new DrawingToolBar(actionListener);
        drawingToolBar.setVisible(true);

        drawingView = new DrawingView(appService);
        actionListener.setComponent(drawingView);


        drawingController = new DrawingController(appService, drawingView);
        drawingController.setDrawingView(drawingView);

        drawingView.addMouseMotionListener(drawingController);
        drawingView.addMouseListener(drawingController);
        drawingView.setPreferredSize(new Dimension(4095, 8192));

        jScrollPane = new JScrollPane(drawingView);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        drawingStatusPanel = new DrawingStatusPanel();
        drawingController.setDrawingStatusPanel(drawingStatusPanel);

        pane.add(drawingToolBar, BorderLayout.PAGE_START);
        pane.add(jScrollPane, BorderLayout.CENTER );
        pane.add(drawingStatusPanel, BorderLayout.PAGE_END);

        drawingAppService.setDrawingView(drawingView);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);

        drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);
        buildGUI(pane);
        drawingController.setPropertySheet(propertySheet);
    }

    public void buildGUI(Container pane){
        buildPropertyTable(pane);
        JScrollPane scrollPane = new JScrollPane(propertySheet);
        pane.add(scrollPane, BorderLayout.LINE_END);
        pack();
    }


    void buildPropertyTable(Container pane) {
        String[] headers = new String[]{"Property", "value"};
        Color backgroundColor = new Color(255, 255, 255);
        Color invalidColor = new Color(255, 179, 176);
        int rowHeight = 30;
        PropertyOptions options = new PropertyOptions(headers, backgroundColor, invalidColor, rowHeight);

        propertySheet = new PropertySheet(new PropertyOptions.Builder().build());
        propertySheet.addEventListener(new EventListener());
        propertySheet.populateTable(appService);

        repaint();
    }

    // Replace the EventListener class in DrawingFrame with this:
// This uses your EXISTING command classes

    class EventListener extends PropertyEventAdapter {
        @Override
        public void onPropertyUpdated(Property property) {
            Shape shape = appService.getSelectedShape();

            if(property.getName().equals("Current Shape")){
                if(shape == null) {
                    appService.setShapeMode((ShapeMode) property.getValue());
                }
            }
            else if(property.getName().equals("Fore color")){
                if(shape == null) {
                    // Use existing ChangeColorCommand through appService
                    appService.setColor((Color) property.getValue());
                } else {
                    // Direct command for selected shape
                    Command command = new ChangeColorCommand(appService, (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Fill color")){
                if(shape == null) {
                    appService.setFill((Color) property.getValue());
                } else {
                    // Use existing ChangeFillCommand
                    Command command = new ChangeFillCommand(appService, (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Line Thickness")){
                if(shape == null) {
                    appService.setThickness((int) property.getValue());
                } else {
                    // Use existing ChangeThicknessCommand
                    Command command = new ChangeThicknessCommand(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("X Location")){
                if(shape != null) {
                    // Use existing ChangeLocationCommand
                    Command command = ChangeLocationCommand.forX(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Y Location")){
                if(shape != null) {
                    // Use existing ChangeLocationCommand
                    Command command = ChangeLocationCommand.forY(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Width")){
                if(shape != null) {
                    // Use existing ChangeDimensionsCommand
                    Command command = ChangeDimensionsCommand.forWidth(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Height")){
                if(shape != null) {
                    // Use existing ChangeDimensionsCommand
                    Command command = ChangeDimensionsCommand.forHeight(appService, (int) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Text")){
                if(shape != null) {
                    // Use existing ChangeTextCommand
                    Command command = new ChangeTextCommand(appService, (String) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("Font size")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font(font.getFamily(), font.getStyle(), (int) property.getValue());
                        // Use existing ChangeFontCommand
                        Command command = new ChangeFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Font family")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font((String) property.getValue(), font.getStyle(), font.getSize());
                        // Use existing ChangeFontCommand
                        Command command = new ChangeFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Font style")){
                if(shape != null) {
                    Font font = shape.getFont();
                    if(font != null) {
                        Font newFont = new Font(font.getFamily(), (int) property.getValue(), font.getSize());
                        // Use existing ChangeFontCommand
                        Command command = new ChangeFontCommand(appService, newFont);
                        CommandService.ExecuteCommand(command);
                    }
                }
            }
            else if(property.getName().equals("Start color")){
                if(shape != null) {
                    // Use existing ChangeGradientCommand
                    Command command = new ChangeGradientCommand(appService, shape.isGradient(),
                            (Color) property.getValue(), shape.getEndColor());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("End color")){
                if(shape != null) {
                    // Use existing ChangeGradientCommand
                    Command command = new ChangeGradientCommand(appService, shape.isGradient(),
                            shape.getStartColor(), (Color) property.getValue());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("IsGradient")){
                if(shape != null) {
                    // Use existing ChangeGradientCommand
                    Command command = new ChangeGradientCommand(appService, (Boolean) property.getValue(),
                            shape.getStartColor(), shape.getEndColor());
                    CommandService.ExecuteCommand(command);
                }
            }
            else if(property.getName().equals("IsVisible")){
                if(shape != null) {
                    // Direct modification - no command exists for visibility
                    // You can create one if needed
                    shape.setVisible((Boolean) property.getValue());
                }
            }

            drawingView.repaint();
        }
    }

}

