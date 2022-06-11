package com.kma.fitnesssmc.ui.main.create_member;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.ImagePanel;
import com.kma.fitnesssmc.ui.main.component.TextField;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class CreateMemberPanel extends JPanel {
    private final JLabel labelTitle = new JLabel("CREATE NEW MEMBER");

    private final ImagePanel panelAvatar = new ImagePanel();

    private final JButton btnChooseAvatar = new JButton("Choose avatar");

    private final TextField fieldFullName = new TextField();

    private JDatePickerImpl datePicker;

    private final TextField fieldPhoneNumber = new TextField();

    private final JTextField fieldExpirationDate = new JTextField();

    private final JTextField fieldRemainingBalance = new JTextField();

    private final JButton btnSubmit = new JButton("Submit");

    private CreateMemberViewModel viewModel;

    public CreateMemberPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        inject();

        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setLayout(null);

        setupTitleLabel();
        setupAvatarPanel();
        setupChooseAvatarButton();
        setupFullNameField();
        setupDateOfBirthPicker();
        setupPhoneNumberField();
        setupExpirationDateField();
        setupConfirmNewPinField();
        setupSubmitButton();

        setEvents();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        MemberRepository memberRepository = new MemberRepository(sessionManager);
        viewModel = new CreateMemberViewModel(memberRepository);
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

        panelAvatar.setBounds(24, y, 280, 373);
        panelAvatar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(panelAvatar);
    }

    private void setupChooseAvatarButton() {
        final int width = 145;
        final int x = panelAvatar.getX() + (panelAvatar.getWidth() - width) / 2;
        final int y = panelAvatar.getY() + panelAvatar.getHeight() + 16;

        btnChooseAvatar.setBounds(x, y, width, 32);
        btnChooseAvatar.setFont(new Font("Arial", Font.BOLD, 16));
        btnChooseAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        btnChooseAvatar.setVerticalAlignment(SwingConstants.CENTER);
        btnChooseAvatar.setFocusPainted(false);

        add(btnChooseAvatar);
    }

    private void setupFullNameField() {
        JLabel labelFullName = new JLabel("Full name:");
        final int x = panelAvatar.getX() + panelAvatar.getWidth() + 56;
        final int y = panelAvatar.getY() - 2;

        // Setup Full name label
        labelFullName.setBounds(x, y, 72, 22);
        labelFullName.setFont(new Font("Arial", Font.BOLD, 14));
        labelFullName.setHorizontalAlignment(SwingConstants.CENTER);
        labelFullName.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Full name field
        final int width = WIDTH_FRAME - 24 - x;

        fieldFullName.setBounds(x, y + labelFullName.getHeight() + 8, width, 32);
        fieldFullName.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldFullName.setMaxLength(32);
        fieldFullName.setBorder(BorderFactory.createCompoundBorder(
            fieldFullName.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelFullName);
        add(fieldFullName);
    }

    private void setupDateOfBirthPicker() {
        JLabel labelDateOfBirth = new JLabel("Date of birth:");
        final int y = fieldFullName.getY() + fieldFullName.getHeight() + 16;

        // Setup Date of birth label
        labelDateOfBirth.setBounds(fieldFullName.getX(), y, 91, 22);
        labelDateOfBirth.setFont(new Font("Arial", Font.BOLD, 14));
        labelDateOfBirth.setHorizontalAlignment(SwingConstants.CENTER);
        labelDateOfBirth.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Date of birth picker
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        datePicker.setBounds(labelDateOfBirth.getX(), y + labelDateOfBirth.getHeight() + 8, 202, 32);
        datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        datePicker.getJFormattedTextField().setBackground(Color.WHITE);
        datePicker.getJFormattedTextField().setBorder(BorderFactory.createCompoundBorder(
            datePicker.getJFormattedTextField().getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelDateOfBirth);
        add(datePicker);
    }

    private void setupPhoneNumberField() {
        JLabel labelPhoneNumber = new JLabel("Phone number:");
        final int y = datePicker.getY() + datePicker.getHeight() + 8;

        // Setup Phone number label
        labelPhoneNumber.setBounds(datePicker.getX(), y, 108, 24);
        labelPhoneNumber.setFont(new Font("Arial", Font.BOLD, 14));
        labelPhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
        labelPhoneNumber.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Phone number field
        final int width = WIDTH_FRAME - 24 - datePicker.getX();

        fieldPhoneNumber.setBounds(datePicker.getX(), y + labelPhoneNumber.getHeight() + 8, width, 32);
        fieldPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldPhoneNumber.setMaxLength(10);
        fieldPhoneNumber.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldPhoneNumber.setBorder(BorderFactory.createCompoundBorder(
            fieldPhoneNumber.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelPhoneNumber);
        add(fieldPhoneNumber);
    }

    private void setupExpirationDateField() {
        JLabel labelExpirationDate = new JLabel("Expiration Date:");
        final int y = fieldPhoneNumber.getY() + fieldPhoneNumber.getHeight() + 16;

        // Setup New Pin label
        labelExpirationDate.setBounds(fieldPhoneNumber.getX(), y, 128, 24);
        labelExpirationDate.setFont(new Font("Arial", Font.BOLD, 14));
        labelExpirationDate.setHorizontalAlignment(SwingConstants.LEFT);
        labelExpirationDate.setVerticalAlignment(SwingConstants.CENTER);

        // Setup New Pin field
        final int width = WIDTH_FRAME - 32 - fieldPhoneNumber.getX();
        final Date now = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String nowFormatted = dateFormat.format(now);

        fieldExpirationDate.setBounds(fieldPhoneNumber.getX(), y + labelExpirationDate.getHeight() + 8, width, 32);
        fieldExpirationDate.setFont(new Font("Arial", Font.BOLD, 14));
        fieldExpirationDate.setText(nowFormatted);
        fieldExpirationDate.setEditable(false);
        fieldExpirationDate.setBorder(BorderFactory.createCompoundBorder(
            fieldExpirationDate.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelExpirationDate);
        add(fieldExpirationDate);
    }

    private void setupConfirmNewPinField() {
        JLabel labelRemainingBalance = new JLabel("Remaining Balance:");
        final int y = fieldExpirationDate.getY() + fieldExpirationDate.getHeight() + 16;

        // Setup Confirm New Pin label
        labelRemainingBalance.setBounds(fieldExpirationDate.getX(), y, 156, 24);
        labelRemainingBalance.setFont(new Font("Arial", Font.BOLD, 14));
        labelRemainingBalance.setHorizontalAlignment(SwingConstants.LEFT);
        labelRemainingBalance.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Confirm New Pin field
        final int width = WIDTH_FRAME - 24 - fieldExpirationDate.getX();

        fieldRemainingBalance.setBounds(fieldExpirationDate.getX(), y + labelRemainingBalance.getHeight() + 8, width, 32);
        fieldRemainingBalance.setFont(new Font("Arial", Font.BOLD, 14));
        fieldRemainingBalance.setText("0 VND");
        fieldRemainingBalance.setEditable(false);
        fieldRemainingBalance.setBorder(BorderFactory.createCompoundBorder(
            fieldRemainingBalance.getBorder(),
            BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));

        add(labelRemainingBalance);
        add(fieldRemainingBalance);
    }

    private void setupSubmitButton() {
        final int width = 124;
        final int x = fieldRemainingBalance.getX() + (fieldRemainingBalance.getWidth() - width) / 2;
        final int y = fieldRemainingBalance.getY() + fieldRemainingBalance.getHeight() + 20;

        btnSubmit.setBounds(x, y, width, 32);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 16));
        btnSubmit.setHorizontalAlignment(SwingConstants.CENTER);
        btnSubmit.setVerticalAlignment(SwingConstants.CENTER);
        btnSubmit.setFocusPainted(false);

        add(btnSubmit);
    }

    private void setEvents() {
        btnChooseAvatar.addActionListener(event -> chooseAvatar());

        btnSubmit.addActionListener(event -> createMember());
    }

    private void chooseAvatar() {
        final JFileChooser fileChooser = new JFileChooser();
        int state = fileChooser.showOpenDialog(this);

        if (state == APPROVE_OPTION) {
            File fileAvatar = fileChooser.getSelectedFile();
            Image avatar = viewModel.setAvatar(fileAvatar);

            panelAvatar.setImage(avatar);
        }
    }

    private void createMember() {
        String fullName = fieldFullName.getText().trim();
        Date dateOfBirth = (Date) datePicker.getModel().getValue();
        String phoneNumber = fieldPhoneNumber.getText();
        String errorMessage = viewModel.createMember(fullName, dateOfBirth, phoneNumber);

        if (errorMessage != null) {
            showMessageDialog(this, errorMessage, "Error", ERROR_MESSAGE);
            return;
        }

        showMessageDialog(this, "Create new member was successful.", null, INFORMATION_MESSAGE);
        navigateToHome();
    }

    private void navigateToHome() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToHome();
    }
}