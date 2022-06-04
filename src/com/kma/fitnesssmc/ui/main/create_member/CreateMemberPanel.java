package com.kma.fitnesssmc.ui.main.create_member;

import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
import com.kma.fitnesssmc.data.repository.MemberRepository;
import com.kma.fitnesssmc.ui.main.MainFrame;
import com.kma.fitnesssmc.ui.main.component.ImagePanel;
import com.kma.fitnesssmc.ui.main.component.PasswordField;
import com.kma.fitnesssmc.ui.main.component.TextField;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import static com.kma.fitnesssmc.util.Constants.HEIGHT_FRAME;
import static com.kma.fitnesssmc.util.Constants.WIDTH_FRAME;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class CreateMemberPanel extends JPanel {
    private final JLabel labelTitle = new JLabel("CREATE NEW MEMBER");

    private final ImagePanel panelAvatar = new ImagePanel();

    private final JButton btnChooseAvatar = new JButton("Choose avatar");

    private final TextField fieldFullName = new TextField();

    private JDatePickerImpl datePicker;

    private final TextField fieldPhoneNumber = new TextField();

    private final PasswordField fieldNewPin = new PasswordField();

    private final PasswordField fieldConfirmNewPin = new PasswordField();

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
        setupNewPinField();
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
        final int y = panelAvatar.getY();

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

    private void setupNewPinField() {
        JLabel labelNewPin = new JLabel("New PIN:");
        final int y = fieldPhoneNumber.getY() + fieldPhoneNumber.getHeight() + 16;

        // Setup New Pin label
        labelNewPin.setBounds(fieldPhoneNumber.getX(), y, 62, 24);
        labelNewPin.setFont(new Font("Arial", Font.BOLD, 14));
        labelNewPin.setHorizontalAlignment(SwingConstants.CENTER);
        labelNewPin.setVerticalAlignment(SwingConstants.CENTER);

        // Setup New Pin field
        final int width = WIDTH_FRAME - 24 - fieldPhoneNumber.getX();

        fieldNewPin.setBounds(fieldPhoneNumber.getX(), y + labelNewPin.getHeight() + 8, width, 32);
        fieldNewPin.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldNewPin.setMaxLength(6);
        fieldNewPin.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldNewPin.setBorder(BorderFactory.createCompoundBorder(
            fieldNewPin.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelNewPin);
        add(fieldNewPin);
    }

    private void setupConfirmNewPinField() {
        JLabel labelConfirmNewPin = new JLabel("Confirm New PIN:");
        final int y = fieldNewPin.getY() + fieldNewPin.getHeight() + 16;

        // Setup Confirm New Pin label
        labelConfirmNewPin.setBounds(fieldNewPin.getX(), y, 120, 24);
        labelConfirmNewPin.setFont(new Font("Arial", Font.BOLD, 14));
        labelConfirmNewPin.setHorizontalAlignment(SwingConstants.CENTER);
        labelConfirmNewPin.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Confirm New Pin field
        final int width = WIDTH_FRAME - 24 - fieldNewPin.getX();

        fieldConfirmNewPin.setBounds(fieldNewPin.getX(), y + labelConfirmNewPin.getHeight() + 8, width, 32);
        fieldConfirmNewPin.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldConfirmNewPin.setMaxLength(6);
        fieldConfirmNewPin.setDigits(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        fieldConfirmNewPin.setBorder(BorderFactory.createCompoundBorder(
            fieldConfirmNewPin.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelConfirmNewPin);
        add(fieldConfirmNewPin);
    }

    private void setupSubmitButton() {
        final int width = 124;
        final int x = fieldConfirmNewPin.getX() + (fieldConfirmNewPin.getWidth() - width) / 2;
        final int y = fieldConfirmNewPin.getY() + fieldConfirmNewPin.getHeight() + 16;

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
        String newPin = new String(fieldNewPin.getPassword());
        String confirmNewPin = new String(fieldConfirmNewPin.getPassword());
        String errorMessage = viewModel.validate(fullName, dateOfBirth, phoneNumber, newPin, confirmNewPin);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = viewModel.createMember(fullName, dateOfBirth, phoneNumber, newPin);
        if (member != null) {
            navigateToHome();
            return;
        }

        JOptionPane.showMessageDialog(this, "New member creation failed!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void navigateToHome() {
        MainFrame mainFrame = (MainFrame) getWindowAncestor(this);
        mainFrame.navigateToHome();
    }
}
