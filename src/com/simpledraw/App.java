package com.simpledraw;

import com.simpledraw.view.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Uygulamanın giriş noktası (Entry Point).
 */
public class App {
    public static void main(String[] args) {
        // Swing arayüz işlemleri Event Dispatch Thread (EDT) üzerinde
        // çalıştırılmalıdır.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
