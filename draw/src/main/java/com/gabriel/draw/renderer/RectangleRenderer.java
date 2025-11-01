package com.gabriel.draw.renderer;

import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class RectangleRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if (xor) {
            g2.setXORMode(shape.getColor());
        } else {
            g2.setColor(shape.getColor());
            if(shape.getFill() != null){
                GradientPaint gp = new GradientPaint(0, 0, shape.getFill(), 0, height, Color.WHITE);
                g2.setPaint(gp);

                //g2.setColor(shape.getFill());
                g2.fillRect(x,y,width, height);
                g2.setColor(shape.getColor());
            }
        }
        g2.drawRect(x, y, width, height);
        super.render(g, shape, xor);

    }
}
