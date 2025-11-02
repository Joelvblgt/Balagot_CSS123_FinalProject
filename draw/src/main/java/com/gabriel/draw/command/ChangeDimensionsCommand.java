package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class ChangeDimensionsCommand implements Command {
    private AppService appService;
    private Shape targetShape;
    private int oldWidth;
    private int oldHeight;
    private Integer newWidth;
    private Integer newHeight;

    // Constructor for width change only
    public static ChangeDimensionsCommand forWidth(AppService appService, int newWidth) {
        return new ChangeDimensionsCommand(appService, newWidth, null);
    }

    // Constructor for height change only
    public static ChangeDimensionsCommand forHeight(AppService appService, int newHeight) {
        return new ChangeDimensionsCommand(appService, null, newHeight);
    }

    // Constructor for both width and height
    public static ChangeDimensionsCommand forBoth(AppService appService, int newWidth, int newHeight) {
        return new ChangeDimensionsCommand(appService, newWidth, newHeight);
    }

    private ChangeDimensionsCommand(AppService appService, Integer newWidth, Integer newHeight) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newWidth = newWidth;
        this.newHeight = newHeight;

        if (targetShape == null) {
            this.oldWidth = appService.getDrawing().getWidth();
            this.oldHeight = appService.getDrawing().getHeight();
        } else {
            this.oldWidth = targetShape.getWidth();
            this.oldHeight = targetShape.getHeight();
        }
    }

    @Override
    public void execute() {
        if (targetShape == null) {
            if (newWidth != null) appService.getDrawing().setWidth(newWidth);
            if (newHeight != null) appService.getDrawing().setHeight(newHeight);
        } else {
            if (newWidth != null) targetShape.setWidth(newWidth);
            if (newHeight != null) targetShape.setHeight(newHeight);
        }
    }

    @Override
    public void undo() {
        if (targetShape == null) {
            appService.getDrawing().setWidth(oldWidth);
            appService.getDrawing().setHeight(oldHeight);
        } else {
            targetShape.setWidth(oldWidth);
            targetShape.setHeight(oldHeight);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}