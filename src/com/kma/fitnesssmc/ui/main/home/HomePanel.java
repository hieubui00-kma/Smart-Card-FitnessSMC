package com.kma.fitnesssmc.ui.main.home;

import com.kma.fitnesssmc.ui.main.component.ImagePanel;

import javax.swing.*;

import java.awt.*;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;

public class HomePanel extends JPanel {
    private final JLabel labelTitle = new JLabel("MEMBER PROFILE");

    private final ImagePanel panelAvatar = new ImagePanel();

    private final JLabel labelMemberID = new JLabel();

    private final JLabel labelFullName = new JLabel();

    private final JLabel labelDateOfBirth = new JLabel();

    private final JLabel labelPhoneNumber = new JLabel();

    private final JLabel labelExpirationDate = new JLabel();

    private final JButton btnExit = new JButton("Exit");

    public HomePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupAvatarPanel();
        setupMemberIDLabel();
        setupFullNameLabel();
        setupDateOfBirthLabel();
        setupPhoneNumberLabel();
        setupExpirationDateLabel();
        setupExitButton();
    }

    private void setupTitleLabel() {
        labelTitle.setBounds(0, 24, WIDTH_FRAME, 40);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setVerticalAlignment(SwingConstants.CENTER);

        add(labelTitle);
    }

    private void setupAvatarPanel() {
        final int y = labelTitle.getY() + labelTitle.getHeight() + 48;

        panelAvatar.setBounds(24, y, 256, 341);
        panelAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(panelAvatar);
    }

    private void setupMemberIDLabel() {
        JLabel label = new JLabel("Member ID: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = panelAvatar.getY() + 16;

        label.setBounds(labelX, labelY, 128, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = labelX + label.getWidth() + 4;
        final int width = WIDTH_FRAME - 24 - x;

        labelMemberID.setBounds(x, labelY, width, 24);
        labelMemberID.setFont(new Font("Arial", Font.BOLD, 16));
        labelMemberID.setHorizontalAlignment(SwingConstants.LEFT);
        labelMemberID.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelMemberID);
    }

    private void setupFullNameLabel() {
        JLabel label = new JLabel("Full name: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = labelMemberID.getY() + labelMemberID.getHeight() + 24;

        label.setBounds(labelX, labelY, 128, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = labelX + label.getWidth() + 4;
        final int width = WIDTH_FRAME - 24 - x;

        labelFullName.setBounds(x, labelY, width, 24);
        labelFullName.setFont(new Font("Arial", Font.BOLD, 16));
        labelFullName.setHorizontalAlignment(SwingConstants.LEFT);
        labelFullName.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelFullName);
    }

    private void setupDateOfBirthLabel() {
        JLabel label = new JLabel("Date of birth: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = labelFullName.getY() + labelFullName.getHeight() + 24;

        label.setBounds(labelX, labelY, 128, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = label.getX() + label.getWidth() + 4;
        final int y = label.getY();
        final int width = WIDTH_FRAME - 24 - x;

        labelDateOfBirth.setBounds(x, y, width, 24);
        labelDateOfBirth.setFont(new Font("Arial", Font.BOLD, 16));
        labelDateOfBirth.setHorizontalAlignment(SwingConstants.LEFT);
        labelDateOfBirth.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelDateOfBirth);
    }

    private void setupPhoneNumberLabel() {
        JLabel label = new JLabel("Phone number: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = labelDateOfBirth.getY() + labelDateOfBirth.getHeight() + 24;

        label.setBounds(labelX, labelY, 128, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = label.getX() + label.getWidth() + 4;
        final int y = label.getY();
        final int width = WIDTH_FRAME - 24 - x;

        labelPhoneNumber.setBounds(x, y, width, 24);
        labelPhoneNumber.setFont(new Font("Arial", Font.BOLD, 16));
        labelPhoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
        labelPhoneNumber.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelPhoneNumber);
    }

    private void setupExpirationDateLabel() {
        JLabel label = new JLabel("Expiration Date: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = labelPhoneNumber.getY() + labelPhoneNumber.getHeight() + 24;

        label.setBounds(labelX, labelY, 128, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = label.getX() + label.getWidth() + 4;
        final int y = label.getY();
        final int width = WIDTH_FRAME - 24 - x;

        labelExpirationDate.setBounds(x, y, width, 24);
        labelExpirationDate.setFont(new Font("Arial", Font.BOLD, 16));
        labelExpirationDate.setHorizontalAlignment(SwingConstants.LEFT);
        labelExpirationDate.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelExpirationDate);
    }

    private void setupExitButton() {
        final int width = 128;
        final int height = 40;
        final int x = (WIDTH_FRAME - width) / 2;
        final int y = HEIGHT_FRAME - 32 - height;

        btnExit.setBounds(x, y, width, height);
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setHorizontalAlignment(SwingConstants.CENTER);
        btnExit.setVerticalAlignment(SwingConstants.CENTER);
        btnExit.setFocusPainted(false);

        add(btnExit);
    }
}
