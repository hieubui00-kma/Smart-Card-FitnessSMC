package com.kma.fitnesssmc.ui.main;

import javax.swing.*;
import java.awt.*;

import static com.kma.fitnesssmc.util.Constants.*;

public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        super();
        initComponents();
    }

    private void initComponents() {
        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setTitle(APPLICATION_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        Insets insets = getInsets();
        int width = getWidth() + insets.left + insets.right;
        int height = getHeight() + insets.top + insets.bottom;
        setSize(width, height);
    }

    private void replace(JPanel panel) {
        Component component = getContentPane().getComponent(0);

        if (component != null) {
            remove(component);
        }
        add(panel);
        repaint();
    }
}