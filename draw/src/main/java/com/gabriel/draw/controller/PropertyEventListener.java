package com.gabriel.draw.controller;

import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class PropertyEventListener extends PropertyEventAdapter {
    private AppService appService;

    public PropertyEventListener(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void onPropertyUpdated(Property property) {
        if (property.getName().equals("Fill color")) {
            appService.setFill((Color) property.getValue());
        } else if (property.getName().equals("Fore color")) {
            appService.setColor ((Color) property.getValue());
        } else if (property.getName().equals("X Location")) {
            appService.setXLocation((int) property.getValue());
        } else if (property.getName().equals("Y Location")) {
            appService.setYLocation((int) property.getValue());
        } else if (property.getName().equals("Width")) {
            appService.setWidth((int) property.getValue());
        } else if (property.getName().equals("Height")) {
            appService.setHeight((int) property.getValue());
        } else if (property.getName().equals("Line Thickness")) {
            appService.setThickness((int) property.getValue());
        } else if (property.getName().equals("Text")) {
            appService.setText((String)property.getValue());
        } else if (property.getName().equals("Font size")) {
            appService.setFontSize((int)property.getValue());
        } else if (property.getName().equals("Font Family")) {
            appService.setFontSize((int)property.getValue());
        }
    }
}