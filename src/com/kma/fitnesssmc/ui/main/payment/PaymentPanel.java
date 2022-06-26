package com.kma.fitnesssmc.ui.main.payment;

import com.kma.fitnesssmc.data.local.FitnessDatabase;
import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.EpisodePack;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.EpisodePackRepository;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.PasswordField;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.kma.fitnesssmc.util.Constants.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class PaymentPanel extends JPanel {
    private final JLabel labelTitle = new JLabel("PAYMENT");

    private final JLabel labelRemainingBalance = new JLabel();

    private final JLabel labelEpisodePacks = new JLabel("Episode Packs: ");

    private final JComboBox<EpisodePack> boxEpisodePacks = new JComboBox<>();

    private final PasswordField fieldPIN = new PasswordField();

    private final JButton btnPayment = new JButton("Payment");

    private final JButton btnBack = new JButton("Back");

    private PaymentViewModel viewModel;

    public PaymentPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupEpisodePacksComboBox();
        setupRemainingBalanceLabel();
        setupPINField();
        setupPaymentButton();
        setupBackButton();

        setEvents();

        getMember();
    }

    private void inject() {
        FitnessDatabase database = FitnessDatabase.getInstance();
        EpisodePackRepository episodePackRepository = new EpisodePackRepository(database);

        SessionManager sessionManager = SessionManager.getInstance();
        MemberRepository memberRepository = new MemberRepository(sessionManager, database);

        viewModel = new PaymentViewModel(episodePackRepository, memberRepository);
    }

    private void setupTitleLabel() {
        labelTitle.setBounds(0, 24, WIDTH_FRAME, 40);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 32));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setVerticalAlignment(SwingConstants.CENTER);

        add(labelTitle);
    }

    private void setupEpisodePacksComboBox() {
        labelEpisodePacks.setBounds(212, (HEIGHT_FRAME - 24) / 2 - 32, 128, 24);
        labelEpisodePacks.setFont(new Font("Arial", Font.BOLD, 16));
        labelEpisodePacks.setHorizontalAlignment(SwingConstants.LEFT);
        labelEpisodePacks.setVerticalAlignment(SwingConstants.CENTER);

        final int x = labelEpisodePacks.getX() + labelEpisodePacks.getWidth() + 48;
        DefaultComboBoxModel<EpisodePack> model = new DefaultComboBoxModel<>();
        List<EpisodePack> episodePacks = viewModel.getEpisodePacks();

        model.addAll(episodePacks);
        boxEpisodePacks.setBounds(x, (HEIGHT_FRAME - 32) / 2 - 32, 296, 32);
        boxEpisodePacks.setFont(new Font("Arial", Font.BOLD, 14));
        boxEpisodePacks.setModel(model);
        boxEpisodePacks.setBackground(Color.WHITE);

        add(labelEpisodePacks);
        add(boxEpisodePacks);
    }

    private void setupRemainingBalanceLabel() {
        JLabel label = new JLabel("Remaining Balance: ");
        final int labelY = boxEpisodePacks.getY() - 56;

        label.setBounds(labelEpisodePacks.getX(), labelY, 164, 24);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        labelRemainingBalance.setBounds(boxEpisodePacks.getX(), labelY, boxEpisodePacks.getWidth(), 24);
        labelRemainingBalance.setFont(new Font("Arial", Font.BOLD, 16));
        labelRemainingBalance.setHorizontalAlignment(SwingConstants.LEFT);
        labelRemainingBalance.setVerticalAlignment(SwingConstants.CENTER);

        add(label);
        add(labelRemainingBalance);
    }

    private void setupPINField() {
        JLabel label = new JLabel("Enter your PIN: ");
        final int y = boxEpisodePacks.getY() + boxEpisodePacks.getHeight() + 32;

        fieldPIN.setBounds(boxEpisodePacks.getX(), y, boxEpisodePacks.getWidth(), boxEpisodePacks.getHeight());
        fieldPIN.setFont(new Font("Arial", Font.PLAIN, 16));
        fieldPIN.setMaxLength(6);
        fieldPIN.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldPIN.setBorder(BorderFactory.createCompoundBorder(
            fieldPIN.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        label.setBounds(212, fieldPIN.getY() + 4, 164, 24);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        add(fieldPIN);
        add(label);
    }

    private void setupPaymentButton() {
        final int y = fieldPIN.getY() + fieldPIN.getHeight() + 56;

        btnPayment.setBounds(268, y, 156, 40);
        btnPayment.setFont(new Font("Arial", Font.BOLD, 16));
        btnPayment.setHorizontalAlignment(SwingConstants.CENTER);
        btnPayment.setVerticalAlignment(SwingConstants.CENTER);
        btnPayment.setFocusPainted(false);

        add(btnPayment);
    }

    private void setupBackButton() {
        final int x = btnPayment.getX() + btnPayment.getWidth() + 32;

        btnBack.setBounds(x, btnPayment.getY(), 156, 40);
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setHorizontalAlignment(SwingConstants.CENTER);
        btnBack.setVerticalAlignment(SwingConstants.CENTER);
        btnBack.setFocusPainted(false);

        add(btnBack);
    }

    private void setEvents() {
        btnPayment.addActionListener(event -> payment());

        btnBack.addActionListener(event -> navigateToHome());
    }

    private void payment() {
        EpisodePack episodePack = (EpisodePack) boxEpisodePacks.getSelectedItem();
        String PIN = new String(fieldPIN.getPassword());
        String errorMessage = viewModel.payment(episodePack, PIN);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            if (errorMessage.equals(ERROR_MESSAGE_CARD_HAS_BLOCKED)) {
                navigateToConnect();
            }
            return;
        }

        JOptionPane.showMessageDialog(this, "Payment successful!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        fieldPIN.setText(null);
        getMember();
    }

    private void navigateToConnect() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToConnect();
    }

    private void navigateToHome() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToHome();
    }

    private void getMember() {
        Member member = viewModel.getMember();

        if (member == null) {
            JOptionPane.showMessageDialog(this, "Error! An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        labelRemainingBalance.setText(String.format("%,d", member.getRemainingBalance()) + " VND");
    }
}
