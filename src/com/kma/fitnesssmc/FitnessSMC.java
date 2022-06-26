package com.kma.fitnesssmc;

import com.kma.fitnesssmc.data.local.FitnessDatabase;
import com.kma.fitnesssmc.ui.main.MainFrame;

import java.sql.SQLException;

public class FitnessSMC {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        FitnessDatabase.getInstance().connect();
        new MainFrame().setVisible(true);
    }
}