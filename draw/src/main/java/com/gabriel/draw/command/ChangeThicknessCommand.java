package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class ChangeThicknessCommand implements Command {
    private AppService appService;
    private int newThickness;
    private int oldThickness;
    private Shape targetShape;
    private boolean isDefaultChange;

    public ChangeThicknessCommand(AppService appService, int newThickness) {
        this.appService = appService;
        this.newThickness = newThickness;
        this.targetShape = appService.getSelectedShape();

        if (targetShape == null) {
            this.isDefaultChange = true;
            this.oldThickness = appService.getDrawing().getThickness();
        } else {
            this.isDefaultChange = false;
            this.oldThickness = targetShape.getThickness();
        }
    }

    @Override
    public void execute() {
        // DIRECT modification - don't call appService setters
        if (isDefaultChange) {
            appService.getDrawing().setThickness(newThickness);
        } else {
            targetShape.setThickness(newThickness);
        }
    }

    @Override
    public void undo() {
        if (isDefaultChange) {
            appService.getDrawing().setThickness(oldThickness);
        } else {
            targetShape.setThickness(oldThickness);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
