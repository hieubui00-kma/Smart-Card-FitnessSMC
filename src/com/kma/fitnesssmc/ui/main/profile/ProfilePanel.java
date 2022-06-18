package com.kma.fitnesssmc.ui.main.profile;

import com.kma.fitnesssmc.data.manager.FileManager;
import com.kma.fitnesssmc.data.manager.SessionManager;
import com.kma.fitnesssmc.data.model.Member;
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
import static javax.swing.SwingUtilities.getWindowAncestor;

public class ProfilePanel extends JPanel {
    private final JLabel labelTitle = new JLabel("MEMBER PROFILE");

    private final ImagePanel panelAvatar = new ImagePanel();

    private final JButton btnChooseAvatar = new JButton("Choose avatar");

    private final JTextField fieldMemberID = new JTextField();

    private final TextField fieldFullName = new TextField();

    private JDatePickerImpl datePicker;

    private final TextField fieldPhoneNumber = new TextField();

    private final JTextField fieldExpirationDate = new JTextField();

    private final JButton btnSave = new JButton("Save");

    private final JButton btnBack = new JButton("Back");

    private ProfileViewModel viewModel;

    public ProfilePanel() {
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
        setupMemberIDLabel();
        setupFullNameField();
        setupDateOfBirthPicker();
        setupPhoneNumberField();
        setupExpirationDateField();
        setupSaveButton();
        setupBackButton();

        setEvents();

        getMember();
    }

    private void inject() {
        SessionManager sessionManager = SessionManager.getInstance();
        FileManager fileManager = FileManager.getInstance();
        File dataStorage = fileManager.getDataStorage();
        MemberRepository memberRepository = new MemberRepository(sessionManager, dataStorage);

        viewModel = new ProfileViewModel(memberRepository);
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
        final int y = panelAvatar.getY() + panelAvatar.getHeight() + 24;

        btnChooseAvatar.setBounds(x, y, width, 40);
        btnChooseAvatar.setFont(new Font("Arial", Font.BOLD, 16));
        btnChooseAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        btnChooseAvatar.setVerticalAlignment(SwingConstants.CENTER);
        btnChooseAvatar.setFocusPainted(false);

        add(btnChooseAvatar);
    }

    private void setupMemberIDLabel() {
        JLabel labelMemberID = new JLabel("Member ID:");
        final int x = panelAvatar.getX() + panelAvatar.getWidth() + 56;
        final int y = panelAvatar.getY();

        // Setup Full name label
        labelMemberID.setBounds(x, y, 80, 22);
        labelMemberID.setFont(new Font("Arial", Font.BOLD, 14));
        labelMemberID.setHorizontalAlignment(SwingConstants.LEFT);
        labelMemberID.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Full name field
        final int width = WIDTH_FRAME - 24 - x;

        fieldMemberID.setBounds(x, y + labelMemberID.getHeight() + 8, width, 32);
        fieldMemberID.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldMemberID.setEditable(false);
        fieldMemberID.setBorder(BorderFactory.createCompoundBorder(
            fieldMemberID.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelMemberID);
        add(fieldMemberID);
    }

    private void setupFullNameField() {
        JLabel labelFullName = new JLabel("Full name:");
        final int x = panelAvatar.getX() + panelAvatar.getWidth() + 56;
        final int y = fieldMemberID.getY() + fieldMemberID.getHeight() + 16;

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
        JLabel labelNewPin = new JLabel("Expiration Date:");
        final int y = fieldPhoneNumber.getY() + fieldPhoneNumber.getHeight() + 16;

        // Setup Expiration Date label
        labelNewPin.setBounds(fieldPhoneNumber.getX(), y, 128, 24);
        labelNewPin.setFont(new Font("Arial", Font.BOLD, 14));
        labelNewPin.setHorizontalAlignment(SwingConstants.LEFT);
        labelNewPin.setVerticalAlignment(SwingConstants.CENTER);

        // Setup Expiration Date field
        final int width = WIDTH_FRAME - 24 - fieldPhoneNumber.getX();

        fieldExpirationDate.setBounds(fieldPhoneNumber.getX(), y + labelNewPin.getHeight() + 8, width, 32);
        fieldExpirationDate.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldExpirationDate.setEditable(false);
        fieldExpirationDate.setBorder(BorderFactory.createCompoundBorder(
            fieldExpirationDate.getBorder(),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        add(labelNewPin);
        add(fieldExpirationDate);
    }

    private void setupSaveButton() {
        final int x = fieldExpirationDate.getX() + (fieldExpirationDate.getWidth() - 280) / 2;
        final int y = fieldExpirationDate.getY() + fieldExpirationDate.getHeight() + 24;

        btnSave.setBounds(x, y, 128, 40);
        btnSave.setFont(new Font("Arial", Font.BOLD, 16));
        btnSave.setHorizontalAlignment(SwingConstants.CENTER);
        btnSave.setVerticalAlignment(SwingConstants.CENTER);
        btnSave.setFocusPainted(false);

        add(btnSave);
    }

    private void setupBackButton() {
        final int x = btnSave.getX() + btnSave.getWidth() + 24;

        btnBack.setBounds(x, btnSave.getY(), 128, 40);
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setHorizontalAlignment(SwingConstants.CENTER);
        btnBack.setVerticalAlignment(SwingConstants.CENTER);
        btnBack.setFocusPainted(false);

        add(btnBack);
    }

    private void setEvents() {
        btnChooseAvatar.addActionListener(event -> chooseAvatar());

        btnSave.addActionListener(event -> updateMemberProfile());

        btnBack.addActionListener(event -> navigateToHome());
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

    private void updateMemberProfile() {
        String fullName = fieldFullName.getText().trim();
        Date dateOfBirth = (Date) datePicker.getModel().getValue();
        String phoneNumber = fieldPhoneNumber.getText();
        String errorMessage = viewModel.updateMemberProfile(fullName, dateOfBirth, phoneNumber);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Update successful!", "Error", JOptionPane.INFORMATION_MESSAGE);
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

        Image avatar = viewModel.setAvatar(member.getAvatar());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        panelAvatar.setImage(avatar);
        fieldMemberID.setText(member.getID());
        fieldFullName.setText(member.getFullName());
        setDateOfBirth(member.getDateOfBirth());
        fieldPhoneNumber.setText(member.getPhoneNumber());
        fieldExpirationDate.setText(dateFormat.format(member.getExpirationDate()));
    }

    private void setDateOfBirth(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        datePicker.getModel().setYear(calendar.get(Calendar.YEAR));
        datePicker.getModel().setMonth(calendar.get(Calendar.MONTH));
        datePicker.getModel().setDay(calendar.get(Calendar.DATE));
        datePicker.getModel().setSelected(true);
        datePicker.getJFormattedTextField().setValue(calendar);
    }
}
