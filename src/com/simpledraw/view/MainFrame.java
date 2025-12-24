package com.simpledraw.view;

import com.simpledraw.model.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Uygulamanın ana penceresi (JFrame).
 * Arayüz elemanlarını yerleştirir ve DrawingPanel'i yönetir.
 */
public class MainFrame extends JFrame {

    private final DrawingPanel drawingPanel;

    public MainFrame() {
        super("Java Swing OOP Simple Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Pencereyi ortala
        setLayout(new BorderLayout());

        // 1. Drawing Panel'i oluştur ve merkeze ekle
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // 2. Kontrol Panelini oluştur (Üst kısım)
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
    }

    /**
     * Kontrol panelini ve içindeki bileşenleri oluşturur.
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        // --- Şekil Seçimi ---
        JLabel shapeLabel = new JLabel("Shape:");

        // Enum'dan şekil isimlerini alarak ComboBox'a doldur
        JComboBox<ShapeType> shapeComboBox = new JComboBox<>(ShapeType.values());
        shapeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShapeType selected = (ShapeType) shapeComboBox.getSelectedItem();
                drawingPanel.setCurrentShapeType(selected);
            }
        });

        // --- Renk Seçimi ---
        JButton colorButton = new JButton("Select Color");
        // Başlangıçta butonun yanında küçük bir renk kutusu veya yazısı olabilir mi?
        // Basit tutalım, butona basınca JColorChooser açılır.
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JColorChooser diyaloğunu aç
                Color newColor = JColorChooser.showDialog(MainFrame.this, "Choose a Color", Color.BLACK);
                if (newColor != null) {
                    drawingPanel.setCurrentColor(newColor);
                    colorButton.setForeground(newColor); // Buton yazısını da o renk yapalım ki belli olsun
                }
            }
        });

        // --- Temizle (Clear) Butonu ---
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clear();
            }
        });

        // --- Geri Al (Undo) Butonu ---
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.undo();
            }
        });

        // Bileşenleri panele ekle
        panel.add(shapeLabel);
        panel.add(shapeComboBox);
        panel.add(new JSeparator(SwingConstants.VERTICAL)); // Görsel ayırıcı
        panel.add(colorButton);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(undoButton);
        panel.add(clearButton);

        return panel;
    }
}
