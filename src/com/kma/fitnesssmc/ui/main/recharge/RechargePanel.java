package com.kma.fitnesssmc.ui.main.recharge;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class RechargePanel extends JPanel {
    private final JLabel labelTitle = new JLabel("RECHARGE");

    private final JLabel labelEnterRecharge = new JLabel("Enter your recharge:");

    private final TextField fieldRecharge = new TextField();

    private final JButton btnRecharge = new JButton("Recharge");

    private final JButton btnBack = new JButton("Back");

    private RechargeViewModel viewModel;

    public RechargePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupEnterRechargeLabel();
        setupRechargeField();
        setupRechargeButton();
        setupBackButton();

        setEvents();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        MemberRepository memberRepository = new MemberRepository(sessionManager);
        viewModel = new RechargeViewModel(memberRepository);
    }

    private void setupTitleLabel() {
        labelTitle.setBounds(0, 24, WIDTH_FRAME, 40);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setVerticalAlignment(SwingConstants.CENTER);

        add(labelTitle);
    }

    private void setupEnterRechargeLabel() {
        labelEnterRecharge.setBounds(182, 256, 160, 24);
        labelEnterRecharge.setFont(new Font("Arial", Font.BOLD, 16));
        labelEnterRecharge.setHorizontalAlignment(SwingConstants.LEFT);
        labelEnterRecharge.setVerticalAlignment(SwingConstants.CENTER);

        add(labelEnterRecharge);
    }

    private void setupRechargeField() {
        final int x = labelEnterRecharge.getX() + labelEnterRecharge.getWidth() + 16;

        fieldRecharge.setBounds(x, 248, 288, 40);
        fieldRecharge.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldRecharge.setMaxLength(16);
        fieldRecharge.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldRecharge.setBorder(BorderFactory.createCompoundBorder(
            fieldRecharge.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        JLabel labelCurrency = new JLabel("VND");

        labelCurrency.setBounds(fieldRecharge.getX() + fieldRecharge.getWidth() + 16, labelEnterRecharge.getY(), 36, 24);
        labelCurrency.setFont(new Font("Arial", Font.BOLD, 16));
        labelCurrency.setHorizontalAlignment(SwingConstants.RIGHT);
        labelCurrency.setVerticalAlignment(SwingConstants.CENTER);

        add(fieldRecharge);
        add(labelCurrency);
    }

    private void setupRechargeButton() {
        final int y = labelEnterRecharge.getY() + labelEnterRecharge.getHeight() + 56;

        btnRecharge.setBounds(268, y, 156, 40);
        btnRecharge.setFont(new Font("Arial", Font.BOLD, 16));
        btnRecharge.setHorizontalAlignment(SwingConstants.CENTER);
        btnRecharge.setVerticalAlignment(SwingConstants.CENTER);
        btnRecharge.setFocusPainted(false);

        add(btnRecharge);
    }

    private void setupBackButton() {
        final int x = btnRecharge.getX() + btnRecharge.getWidth() + 32;

        btnBack.setBounds(x, btnRecharge.getY(), 156, 40);
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setHorizontalAlignment(SwingConstants.CENTER);
        btnBack.setVerticalAlignment(SwingConstants.CENTER);
        btnBack.setFocusPainted(false);

        add(btnBack);
    }

    private void setEvents() {
        fieldRecharge.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
                formatRecharge();
            }
        });

        btnRecharge.addActionListener(event -> recharge());

        btnBack.addActionListener(event -> navigateToHome());
    }

    private void formatRecharge() {
        String remainingBalance = fieldRecharge.getText().replace(",", "");

        if (!remainingBalance.isBlank()) {
            fieldRecharge.setText(String.format("%,d", Long.parseLong(remainingBalance)));
        }
    }

    private void recharge() {
        String remainingBalanceRaw = fieldRecharge.getText().replace(",", "");
        long remainingBalance = Long.parseLong(remainingBalanceRaw.isBlank() ? "0" : remainingBalanceRaw);
        String errorMessage = viewModel.recharge(remainingBalance);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Recharge successfully!", "Error", JOptionPane.INFORMATION_MESSAGE);
        fieldRecharge.setText(null);
    }

    private void navigateToHome() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToHome();
    }
}
