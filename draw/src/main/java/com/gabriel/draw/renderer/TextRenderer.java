package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Text;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class TextRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        Text text = (Text) shape;

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if (xor) {
            g2.setXORMode(shape.getColor());
            g2.drawRect(x, y, width, height);
        } else {
            // Set font before calculating metrics
            g2.setFont(shape.getFont());

            // Calculate proper text position (accounting for ascent)
            FontMetrics fm = g2.getFontMetrics();
            int textY = y + fm.getAscent(); // This is crucial for proper text positioning

            if (shape.isGradient()) {
                GradientPaint gp = new GradientPaint(x, y, shape.getStartColor(),
                        x + width, y + height, shape.getEndColor());
                g2.setPaint(gp);
            } else {
                g2.setColor(shape.getColor());
            }

            g2.drawString(shape.getText(), x, textY);
        }
        super.render(g, shape, xor);
    }
}