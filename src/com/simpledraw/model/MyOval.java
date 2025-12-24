package com.simpledraw.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Oval şeklini temsil eden sınıf.
 * Dikdörtgen mantığıyla sınırları hesaplar.
 */
public class MyOval extends MyShape {

    public MyOval() {
        super();
    }

    public MyOval(int x1, int y1, int x2, int y2, Color color) {
        super(x1, y1, x2, y2, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        
        int upperLeftX = Math.min(getX1(), getX2());
        int upperLeftY = Math.min(getY1(), getY2());
        int width = Math.abs(getX1() - getX2());
        int height = Math.abs(getY1() - getY2());

        g.fillOval(upperLeftX, upperLeftY, width, height);
    }
}
