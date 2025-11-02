package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeFillCommand implements Command {
    private AppService appService;
    private Color newFill;
    private Color oldDefaultFill;
    private Map<Shape, Color> oldShapeFills;
    private List<Shape> affectedShapes;

    public ChangeFillCommand(AppService appService, Color newFill) {
        this.appService = appService;
        this.newFill = newFill;
        this.oldDefaultFill = appService.getDrawing().getFill();
        this.oldShapeFills = new HashMap<>();

        // Store old fills of selected shapes
        this.affectedShapes = new ArrayList<>(appService.getSelectedShapes());
        for (Shape shape : affectedShapes) {
            oldShapeFills.put(shape, shape.getFill());
        }
    }

    @Override
    public void execute() {
        // DIRECT modification - don't call appService setters
        if (affectedShapes.isEmpty()) {
            appService.getDrawing().setFill(newFill);
        } else {
            for (Shape shape : affectedShapes) {
                shape.setFill(newFill);
            }
        }
    }

    @Override
    public void undo() {
        if (affectedShapes.isEmpty()) {
            appService.getDrawing().setFill(oldDefaultFill);
        } else {
            for (Shape shape : affectedShapes) {
                Color oldFill = oldShapeFills.get(shape);
                shape.setFill(oldFill);
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}