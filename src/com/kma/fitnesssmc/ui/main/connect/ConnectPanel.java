package com.kma.fitnesssmc.ui.main.connect;

import com.kma.fitnesssmc.data.manager.FileManager;
import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.PasswordField;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class ConnectPanel extends JPanel {
    private final JLabel labelTitle = new JLabel("FITNESS SMART CARD");

    private final JLabel labelEnterPin = new JLabel("Enter your PIN:");

    private final PasswordField fieldPin = new PasswordField();

    private final JButton btnConnect = new JButton("Connect");

    private ConnectViewModel viewModel;

    public ConnectPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupEnterPinLabel();
        setupPinField();
        setupConnectButton();

        setEvents();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        FileManager fileManager = FileManager.getInstance();
        File dataStorage = fileManager.getDataStorage();
        MemberRepository memberRepository = new MemberRepository(sessionManager, dataStorage);

        viewModel = new ConnectViewModel(sessionManager, memberRepository);
    }

    private void setupTitleLabel() {
        labelTitle.setBounds(0, 24, WIDTH_FRAME, 40);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setVerticalAlignment(SwingConstants.CENTER);

        add(labelTitle);
    }

    private void setupEnterPinLabel() {
        final int width = 174;
        final int x = (WIDTH_FRAME - width) / 2;
        final int y = labelTitle.getY() + labelTitle.getHeight() + 128;

        labelEnterPin.setBounds(x, y, width, 24);
        labelEnterPin.setFont(new Font("Arial", Font.BOLD, 24));
        labelEnterPin.setHorizontalAlignment(SwingConstants.CENTER);
        labelEnterPin.setVerticalAlignment(SwingConstants.CENTER);

        add(labelEnterPin);
    }

    private void setupPinField() {
        final int width = 200;
        final int x = (WIDTH_FRAME - width) / 2;
        final int y = labelEnterPin.getY() + labelEnterPin.getHeight() + 28;

        fieldPin.setBounds(x, y, width, 48);
        fieldPin.setFont(new Font("Arial", Font.PLAIN, 24));
        fieldPin.setHorizontalAlignment(SwingConstants.CENTER);
        fieldPin.setMaxLength(6);
        fieldPin.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldPin.setBorder(BorderFactory.createCompoundBorder(
            fieldPin.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(fieldPin);
    }

    private void setupConnectButton() {
        final int width = 156;
        final int x = (WIDTH_FRAME - width) / 2;
        final int y = fieldPin.getY() + fieldPin.getHeight() + 32;

        btnConnect.setBounds(x, y, width, 40);
        btnConnect.setFont(new Font("Arial", Font.BOLD, 20));
        btnConnect.setHorizontalAlignment(SwingConstants.CENTER);
        btnConnect.setVerticalAlignment(SwingConstants.CENTER);
        btnConnect.setFocusPainted(false);

        add(btnConnect);
    }

    private void setEvents() {
        btnConnect.addActionListener(event -> connect());
    }

    private void connect() {
        String pin = new String(fieldPin.getPassword());
        String errorMessage = viewModel.connect(pin);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);

        if (viewModel.getMember() == null) {    // Member isn't initialized
            mainFrame.navigateToCreateMember();
            return;
        }

        mainFrame.navigateToHome();
    }
}
