package com.kma.fitnesssmc.ui.main;

import com.kma.fitnesssmc.ui.main.connect.ConnectPanel;
import com.kma.fitnesssmc.ui.main.create_member.CreateMemberPanel;
import com.kma.fitnesssmc.ui.main.home.HomePanel;
import com.kma.fitnesssmc.ui.main.profile.ProfilePanel;

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

        add(new ConnectPanel());
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

    public void navigateToCreateMember() {
        replace(new CreateMemberPanel());
    }

    public void navigateToHome() {
        replace(new HomePanel());
    }

    public void navigateToProfile() {
        replace(new ProfilePanel());
    }

    public void navigateToConnect() {
        replace(new ConnectPanel());
    }
}