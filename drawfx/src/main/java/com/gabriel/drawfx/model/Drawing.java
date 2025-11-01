package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Drawing {
    Point location;
    private String filename;
    private String imageFilename;
    private Color color;
    private Color fill;
    int thickness  = 1;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    private int SearchRadius = 5;
    private Font font;
    int width;
    int height;
    List<Shape> shapes;
    String Filename;
    Shape selectedShape;
    String text = "Default test";
    public Drawing(){
        location  = new Point(0,0);
        color = Color.RED;
        fill = Color.WHITE;
        font = new Font("Serif", Font.BOLD, 24);
        shapes = new ArrayList<>();
        imageFilename = null;
    }
}
