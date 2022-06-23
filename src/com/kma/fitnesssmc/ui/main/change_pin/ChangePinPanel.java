package com.kma.fitnesssmc.ui.main.change_pin;

import com.kma.fitnesssmc.data.local.FitnessDatabase;
import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.PasswordField;

import javax.swing.*;
import java.awt.*;

import static com.kma.fitnesssmc.util.Constants.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class ChangePinPanel extends JPanel {
    private final JLabel labelTitle = new JLabel("CHANGE PIN");

    private final JLabel labelCurrentPIN = new JLabel("Current PIN: ");

    private final PasswordField fieldCurrentPIN = new PasswordField();

    private final JLabel labelNewPIN = new JLabel("New PIN: ");

    private final PasswordField fieldNewPIN = new PasswordField();

    private final JLabel labelConfirmNewPIN = new JLabel("Confirm New PIN: ");

    private final PasswordField fieldConfirmNewPIN = new PasswordField();

    private final JButton btnChange = new JButton("Change");

    private final JButton btnBack = new JButton("Back");

    private ChangePinViewModel viewModel;

    public ChangePinPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupCurrentPINField();
        setupNewPINField();
        setupConfirmNewPINField();
        setupChangeButton();
        setupBackButton();

        setEvents();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        FitnessDatabase database = FitnessDatabase.getInstance();
        MemberRepository memberRepository = new MemberRepository(sessionManager, database);

        viewModel = new ChangePinViewModel(memberRepository, sessionManager);
    }

    private void setupTitleLabel() {
        labelTitle.setBounds(0, 24, WIDTH_FRAME, 40);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setVerticalAlignment(SwingConstants.CENTER);

        add(labelTitle);
    }

    private void setupCurrentPINField() {
        final int y = labelTitle.getY() + labelTitle.getHeight() + 128;

        labelCurrentPIN.setBounds(214, y, 100, 24);
        labelCurrentPIN.setFont(new Font("Arial", Font.BOLD, 16));
        labelCurrentPIN.setHorizontalAlignment(SwingConstants.LEFT);
        labelCurrentPIN.setVerticalAlignment(SwingConstants.CENTER);

        final int x = labelCurrentPIN.getX() + labelCurrentPIN.getWidth() + 64;

        fieldCurrentPIN.setBounds(x, y - 4, 288, 32);
        fieldCurrentPIN.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldCurrentPIN.setMaxLength(6);
        fieldCurrentPIN.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldCurrentPIN.setBorder(BorderFactory.createCompoundBorder(
            fieldCurrentPIN.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelCurrentPIN);
        add(fieldCurrentPIN);
    }

    private void setupNewPINField() {
        final int y = fieldCurrentPIN.getY() + fieldCurrentPIN.getHeight() + 24;

        fieldNewPIN.setBounds(fieldCurrentPIN.getX(), y, 288, 32);
        fieldNewPIN.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldNewPIN.setMaxLength(6);
        fieldNewPIN.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldNewPIN.setBorder(BorderFactory.createCompoundBorder(
            fieldNewPIN.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        labelNewPIN.setBounds(labelCurrentPIN.getX(), fieldNewPIN.getY() + 4, 100, 24);
        labelNewPIN.setFont(new Font("Arial", Font.BOLD, 16));
        labelNewPIN.setHorizontalAlignment(SwingConstants.LEFT);
        labelNewPIN.setVerticalAlignment(SwingConstants.CENTER);

        add(labelNewPIN);
        add(fieldNewPIN);
    }

    private void setupConfirmNewPINField() {
        final int y = fieldNewPIN.getY() + fieldNewPIN.getHeight() + 24;

        fieldConfirmNewPIN.setBounds(fieldNewPIN.getX(), y, 288, 32);
        fieldConfirmNewPIN.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldConfirmNewPIN.setMaxLength(6);
        fieldConfirmNewPIN.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldConfirmNewPIN.setBorder(BorderFactory.createCompoundBorder(
            fieldConfirmNewPIN.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        labelConfirmNewPIN.setBounds(labelNewPIN.getX(), fieldConfirmNewPIN.getY() + 4, 140, 24);
        labelConfirmNewPIN.setFont(new Font("Arial", Font.BOLD, 16));
        labelConfirmNewPIN.setHorizontalAlignment(SwingConstants.LEFT);
        labelConfirmNewPIN.setVerticalAlignment(SwingConstants.CENTER);

        add(labelConfirmNewPIN);
        add(fieldConfirmNewPIN);
    }

    private void setupChangeButton() {
        final int y = fieldConfirmNewPIN.getY() + fieldConfirmNewPIN.getHeight() + 56;

        btnChange.setBounds(268, y, 156, 40);
        btnChange.setFont(new Font("Arial", Font.BOLD, 16));
        btnChange.setHorizontalAlignment(SwingConstants.CENTER);
        btnChange.setVerticalAlignment(SwingConstants.CENTER);
        btnChange.setFocusPainted(false);

        add(btnChange);
    }

    private void setupBackButton() {
        final int x = btnChange.getX() + btnChange.getWidth() + 32;

        btnBack.setBounds(x, btnChange.getY(), 156, 40);
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setHorizontalAlignment(SwingConstants.CENTER);
        btnBack.setVerticalAlignment(SwingConstants.CENTER);
        btnBack.setFocusPainted(false);

        add(btnBack);
    }

    private void setEvents() {
        btnChange.addActionListener(event -> changePIN());

        btnBack.addActionListener(event -> navigateToHome());
    }

    private void changePIN() {
        String currentPin = new String(fieldCurrentPIN.getPassword());
        String newPin = new String(fieldNewPIN.getPassword());
        String confirmNewPin = new String(fieldConfirmNewPIN.getPassword());
        String errorMessage = viewModel.changePIN(currentPin, newPin, confirmNewPin);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            if (errorMessage.equals(ERROR_MESSAGE_CARD_HAS_BLOCKED)) {
                navigateToConnect();
            }
            return;
        }

        JOptionPane.showMessageDialog(this, "Update PIN successful!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        fieldCurrentPIN.setText(null);
        fieldNewPIN.setText(null);
        fieldConfirmNewPIN.setText(null);
    }

    private void navigateToConnect() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToConnect();
    }

    private void navigateToHome() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToHome();
    }
}
