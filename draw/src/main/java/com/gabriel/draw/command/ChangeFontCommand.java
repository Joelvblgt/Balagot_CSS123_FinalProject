package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ChangeFontCommand implements Command {
    private AppService appService;
    private Shape targetShape;
    private Font oldFont;
    private Font newFont;
    private boolean isDefaultChange;

    public ChangeFontCommand(AppService appService, Font newFont) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newFont = newFont;

        if (targetShape == null) {
            this.isDefaultChange = true;
            this.oldFont = appService.getDrawing().getFont();
        } else {
            this.isDefaultChange = false;
            this.oldFont = targetShape.getFont();
        }
    }

    @Override
    public void execute() {
        // DIRECT modification - don't call appService setters
        if (isDefaultChange) {
            appService.getDrawing().setFont(newFont);
        } else {
            targetShape.setFont(newFont);
        }
    }

    @Override
    public void undo() {
        if (isDefaultChange) {
            appService.getDrawing().setFont(oldFont);
        } else {
            targetShape.setFont(oldFont);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}