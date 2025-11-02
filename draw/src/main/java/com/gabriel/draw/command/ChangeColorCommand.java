package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeColorCommand implements Command {
    private AppService appService;
    private Color newColor;
    private Color oldDefaultColor;
    private Map<Shape, Color> oldShapeColors;
    private List<Shape> affectedShapes;

    public ChangeColorCommand(AppService appService, Color newColor) {
        this.appService = appService;
        this.newColor = newColor;
        this.oldDefaultColor = appService.getDrawing().getColor();
        this.oldShapeColors = new HashMap<>();

        // Store old colors of selected shapes
        this.affectedShapes = new ArrayList<>(appService.getSelectedShapes());
        for (Shape shape : affectedShapes) {
            oldShapeColors.put(shape, shape.getColor());
        }
    }

    @Override
    public void execute() {
        // DIRECT modification - don't call appService setters
        if (affectedShapes.isEmpty()) {
            appService.getDrawing().setColor(newColor);
        } else {
            for (Shape shape : affectedShapes) {
                shape.setColor(newColor);
            }
        }
    }

    @Override
    public void undo() {
        if (affectedShapes.isEmpty()) {
            appService.getDrawing().setColor(oldDefaultColor);
        } else {
            for (Shape shape : affectedShapes) {
                Color oldColor = oldShapeColors.get(shape);
                if (oldColor != null) {
                    shape.setColor(oldColor);
                }
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}