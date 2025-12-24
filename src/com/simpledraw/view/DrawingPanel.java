package com.simpledraw.view;

import com.simpledraw.model.MyLine;
import com.simpledraw.model.MyOval;
import com.simpledraw.model.MyRectangle;
import com.simpledraw.model.MyShape;
import com.simpledraw.model.ShapeType;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Çizim işlemlerinin yapıldığı ana panel.
 * Mouse olaylarını dinler ve şekilleri çizer.
 */
public class DrawingPanel extends JPanel {

    // Çizilen tüm şekillerin tutulduğu liste (Persistent drawing için)
    private final ArrayList<MyShape> shapes;

    // O an seçili olan ayarlar
    private ShapeType currentShapeType;
    private Color currentColor;

    // Şu an çizilmekte olan (sürüklenen) şekil
    private MyShape currentShapeObject;

    public DrawingPanel() {
        this.shapes = new ArrayList<>();
        this.currentShapeType = ShapeType.LINE; // Varsayılan şekil
        this.currentColor = Color.BLACK; // Varsayılan renk

        setBackground(Color.WHITE);

        // Mouse olaylarını dinlemek için handler'ı oluştur ve ekle
        MouseHandler handler = new MouseHandler();
        // MouseListener: Tıklama, basma, bırakma (click, press, release)
        this.addMouseListener(handler);
        // MouseMotionListener: Sürükleme, hareket (drag, move)
        this.addMouseMotionListener(handler);
    }

    /**
     * Panelin çizimini yapan metod.
     * Her repaint() çağrısında tetiklenir.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Paneli temizle ve arka planı boya

        // 1. Kaydedilmiş tüm şekilleri tekrar çiz (Persistent Drawing)
        for (MyShape shape : shapes) {
            shape.draw(g);
        }

        // 2. Eğer şu an bir çizim yapılıyorsa (preview), onu da çiz
        if (currentShapeObject != null) {
            currentShapeObject.draw(g);
        }
    }

    /**
     * Seçili şekil tipini değiştirir.
     */
    public void setCurrentShapeType(ShapeType type) {
        this.currentShapeType = type;
    }

    /**
     * Seçili rengi değiştirir.
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    /**
     * Tuvali temizler.
     */
    public void clear() {
        shapes.clear();
        repaint();
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    /**
     * Inner Class: Mouse olaylarını yönetir.
     * Hem MouseListener hem MouseMotionListener yerine MouseAdapter kullanarak
     * sadece ihtiyacımız olan metodları override ediyoruz.
     */
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("DEBUG: Mouse Pressed at " + e.getX() + ", " + e.getY());

            // Mouse basıldığında başlangıç koordinatlarını al
            int x1 = e.getX();
            int y1 = e.getY();
            int x2 = x1; // Başlangıçta bitiş noktası aynıdır
            int y2 = y1;

            // Seçili tipe göre ilgili nesneyi oluştur
            switch (currentShapeType) {
                case LINE:
                    currentShapeObject = new MyLine(x1, y1, x2, y2, currentColor);
                    break;
                case RECTANGLE:
                    currentShapeObject = new MyRectangle(x1, y1, x2, y2, currentColor);
                    break;
                case OVAL:
                    currentShapeObject = new MyOval(x1, y1, x2, y2, currentColor);
                    break;
            }
            repaint(); // Force repaint
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // Sürükleme sırasında şeklin bitiş noktasını sürekli güncelle
            if (currentShapeObject != null) {
                currentShapeObject.setX2(e.getX());
                currentShapeObject.setY2(e.getY());
                repaint(); // Değişikliği anlık olarak ekrana yansıt (Preview)
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("DEBUG: Mouse Released at " + e.getX() + ", " + e.getY());
            // Mouse bırakıldığında şekli son kez güncelle ve listeye ekle
            if (currentShapeObject != null) {
                currentShapeObject.setX2(e.getX());
                currentShapeObject.setY2(e.getY());

                shapes.add(currentShapeObject); // Kalıcı listeye al
                System.out.println("DEBUG: Shape added. Total shapes: " + shapes.size());

                currentShapeObject = null; // Geçici şekli sıfırla
                repaint();
            }
        }
    }
}
