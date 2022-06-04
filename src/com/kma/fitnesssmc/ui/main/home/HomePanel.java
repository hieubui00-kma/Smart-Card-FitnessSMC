package com.kma.fitnesssmc.ui.main.home;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.ImagePanel;

import javax.swing.*;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class HomePanel extends JPanel {
    private final JLabel labelTitle = new JLabel("FITNESS SMART CARD");

    private final ImagePanel panelAvatar = new ImagePanel();

    private final JLabel labelMemberID = new JLabel();

    private final JLabel labelFullName = new JLabel();

    private final JLabel labelDateOfBirth = new JLabel();

    private final JLabel labelPhoneNumber = new JLabel();

    private final JLabel labelExpirationDate = new JLabel();

    private final JLabel labelRemainingBalance = new JLabel();

    private final JButton btnProfile = new JButton("Profile");

    private final JButton btnChanePIN = new JButton("Change PIN");

    private final JButton btnRecharge = new JButton("Recharge");

    private final JButton btnPayment = new JButton("Payment");

    private final JButton btnExit = new JButton("Exit");

    private HomeViewModel viewModel;

    public HomePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupAvatarPanel();
        setupMemberIDLabel();
        setupFullNameLabel();
        setupDateOfBirthLabel();
        setupPhoneNumberLabel();
        setupExpirationDateLabel();
        setupRemainingBalanceLabel();
        setupProfileButton();
        setupChangePINButton();
        setupRechargeButton();
        setupPaymentButton();
        setupExitButton();

        setEvents();

        getMember();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        MemberRepository memberRepository = new MemberRepository(sessionManager);
        viewModel = new HomeViewModel(sessionManager, memberRepository);
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

        final int x = labelX + label.getWidth() + 48;
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

        final int x = labelMemberID.getX();
        final int width = WIDTH_FRAME - 24 - x;

        labelFullName.setBounds(labelMemberID.getX(), labelY, width, 24);
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

        final int x = labelFullName.getX();
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

        final int x = labelDateOfBirth.getX();
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

        final int x = labelPhoneNumber.getX();
        final int y = label.getY();
        final int width = WIDTH_FRAME - 24 - x;

        labelExpirationDate.setBounds(x, y, width, 24);
        labelExpirationDate.setFont(new Font("Arial", Font.BOLD, 16));
        labelExpirationDate.setHorizontalAlignment(SwingConstants.LEFT);
        labelExpirationDate.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelExpirationDate);
    }

    private void setupRemainingBalanceLabel() {
        JLabel label = new JLabel("Remaining Balance: ");
        final int labelX = panelAvatar.getX() + panelAvatar.getWidth() + 64;
        final int labelY = labelExpirationDate.getY() + labelExpirationDate.getHeight() + 24;

        label.setBounds(labelX, labelY, 156, 24);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        final int x = labelExpirationDate.getX();
        final int y = label.getY();
        final int width = WIDTH_FRAME - 24 - x;

        labelRemainingBalance.setBounds(x, y, width, 24);
        labelRemainingBalance.setFont(new Font("Arial", Font.BOLD, 16));
        labelRemainingBalance.setHorizontalAlignment(SwingConstants.LEFT);
        labelRemainingBalance.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelRemainingBalance);
    }

    private void setupProfileButton() {
        final int height = 40;
        final int y = HEIGHT_FRAME - 32 - height;

        btnProfile.setBounds(72, y, 128, height);
        btnProfile.setFont(new Font("Arial", Font.BOLD, 16));
        btnProfile.setHorizontalAlignment(SwingConstants.CENTER);
        btnProfile.setVerticalAlignment(SwingConstants.CENTER);
        btnProfile.setFocusPainted(false);

        add(btnProfile);
    }

    private void setupChangePINButton() {
        final int height = 40;
        final int x = btnProfile.getX() + btnProfile.getWidth() + 24;
        final int y = HEIGHT_FRAME - 32 - height;

        btnChanePIN.setBounds(x, y, 128, height);
        btnChanePIN.setFont(new Font("Arial", Font.BOLD, 16));
        btnChanePIN.setHorizontalAlignment(SwingConstants.CENTER);
        btnChanePIN.setVerticalAlignment(SwingConstants.CENTER);
        btnChanePIN.setFocusPainted(false);

        add(btnChanePIN);
    }

    private void setupRechargeButton() {
        final int height = 40;
        final int x = btnChanePIN.getX() + btnChanePIN.getWidth() + 24;
        final int y = HEIGHT_FRAME - 32 - height;

        btnRecharge.setBounds(x, y, 128, height);
        btnRecharge.setFont(new Font("Arial", Font.BOLD, 16));
        btnRecharge.setHorizontalAlignment(SwingConstants.CENTER);
        btnRecharge.setVerticalAlignment(SwingConstants.CENTER);
        btnRecharge.setFocusPainted(false);

        add(btnRecharge);
    }

    private void setupPaymentButton() {
        final int height = 40;
        final int x = btnRecharge.getX() + btnRecharge.getWidth() + 24;
        final int y = HEIGHT_FRAME - 32 - height;

        btnPayment.setBounds(x, y, 128, height);
        btnPayment.setFont(new Font("Arial", Font.BOLD, 16));
        btnPayment.setHorizontalAlignment(SwingConstants.CENTER);
        btnPayment.setVerticalAlignment(SwingConstants.CENTER);
        btnPayment.setFocusPainted(false);

        add(btnPayment);
    }

    private void setupExitButton() {
        final int height = 40;
        final int x = btnPayment.getX() + btnPayment.getWidth() + 24;
        final int y = HEIGHT_FRAME - 32 - height;

        btnExit.setBounds(x, y, 128, height);
        btnExit.setFont(new Font("Arial", Font.BOLD, 16));
        btnExit.setHorizontalAlignment(SwingConstants.CENTER);
        btnExit.setVerticalAlignment(SwingConstants.CENTER);
        btnExit.setFocusPainted(false);

        add(btnExit);
    }

    private void setEvents() {
        btnProfile.addActionListener(event -> navigateToProfile());

        btnRecharge.addActionListener(event -> navigateToRecharge());

        btnExit.addActionListener(event -> exit());
    }

    private void navigateToProfile() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToProfile();
    }

    private void navigateToRecharge() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToRecharge();
    }

    private void exit() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);

        viewModel.disconnect();
        mainFrame.navigateToConnect();
    }

    private void getMember() {
        Member member = viewModel.getMember();

        if (member == null) {
            JOptionPane.showMessageDialog(this, "Error! An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        labelMemberID.setText(member.getID());
        labelFullName.setText(member.getFullName());
        labelDateOfBirth.setText(dateFormat.format(member.getDateOfBirth()));
        labelPhoneNumber.setText(member.getPhoneNumber());
        labelExpirationDate.setText(dateFormat.format(member.getExpirationDate()));
        labelRemainingBalance.setText(String.format("%,d", member.getRemainingBalance()) + " VND");
    }
}
