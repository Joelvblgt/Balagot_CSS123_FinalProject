package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveShapeCommand implements Command {
    private AppService appService;
    private Point startPoint;
    private Point endPoint;
    private Map<Shape, Point> originalLocations;
    private List<Shape> movedShapes;
    private int deltaX;
    private int deltaY;

    public MoveShapeCommand(AppService appService, Point startPoint, Point endPoint) {
        this.appService = appService;
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.originalLocations = new HashMap<>();

        // Calculate movement delta
        this.deltaX = endPoint.x - startPoint.x;
        this.deltaY = endPoint.y - startPoint.y;

        // CRITICAL: Store original locations BEFORE any movement
        this.movedShapes = new ArrayList<>(appService.getSelectedShapes());
        for (Shape shape : movedShapes) {
            originalLocations.put(shape, new Point(shape.getLocation()));
        }
    }

    @Override
    public void execute() {
        // Apply the movement to all selected shapes
        for (Shape shape : movedShapes) {
            Point loc = shape.getLocation();
            loc.x += deltaX;
            loc.y += deltaY;
        }
    }

    @Override
    public void undo() {
        // Restore ALL original locations
        for (Shape shape : movedShapes) {
            Point originalLocation = originalLocations.get(shape);
            if (originalLocation != null) {
                shape.setLocation(new Point(originalLocation));
            }
        }
    }

    @Override
    public void redo() {
        // Reapply the movement using the stored delta
        for (Shape shape : movedShapes) {
            Point loc = shape.getLocation();
            loc.x += deltaX;
            loc.y += deltaY;
        }
    }
}