package com.simpledraw.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Dikdörtgen şeklini temsil eden sınıf.
 * Koordinatları sol-üst köşe, genişlik ve yükseklik mantığına çevirir.
 */
public class MyRectangle extends MyShape {

    public MyRectangle() {
        super();
    }

    public MyRectangle(int x1, int y1, int x2, int y2, Color color) {
        super(x1, y1, x2, y2, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        
        // Başlangıç ve bitiş noktalarına göre sol-üst köşeyi ve boyutları hesapla
        int upperLeftX = Math.min(getX1(), getX2());
        int upperLeftY = Math.min(getY1(), getY2());
        int width = Math.abs(getX1() - getX2());
        int height = Math.abs(getY1() - getY2());

        // change to drawRect for outline, or fillRect for filled. Requirement implies drawing tool, usually outlines or filled.
        // Usually, drawing apps might have a fill option. I will stick to drawRect (outline) or fillRect based on common simple drawing apps. 
        // "Simple Drawing Application" typically implies outlines unless specified. Or full shapes.
        // I will use fillRect for a "solid" look or drawRect. Let's start with fillRect as it looks cleaner, 
        // OR better yet, drawRect usually matches "MyShape" better for line tools unless a "Fill" checkbox exists.
        // Given I only have 'Color', I'll use fillRect so the color is visible easily, or drawRect with thickness?
        // Let's use fillRect because "drawing application" often implies painting. 
        // BUT wait, "Simple Drawing" often mimics MS Paint line tool, rectangle tool which are outlines.
        // I will use fillRect because it makes the Color choice more obvious. 
        // Actually, most academic assignments expect outlines or filled. I'll stick to `fillRect` for visual impact or `drawRect`.
        // Let's use `fillRect` for now as it's definitive.
        // Re-reading: "Shape selection: Line, Rectangle, Oval".
        // If I draw a line, it's thin. If I draw a rectangle as outline, it's thin.
        // Let's stick to `fill` for Rectangle/Oval so the Color pop is meaningful.
        
        g.fillRect(upperLeftX, upperLeftY, width, height);
    }
}
