package com.kma.fitnesssmc;

import javax.smartcardio.*;
import java.util.List;

import static com.kma.fitnesssmc.util.Constants.AID;

public class FitnessSMC {

    public static void main(String[] args) {
        TerminalFactory factory = TerminalFactory.getDefault();
        try {
            List<CardTerminal> terminals = factory.terminals().list();
            CardTerminal terminal = terminals.get(0);
            Card card = terminal.connect("*");
            CardChannel channel = card.getBasicChannel();
            CommandAPDU selectCommand = new CommandAPDU(0, 164, 4, 0, AID);
            ResponseAPDU response = channel.transmit(selectCommand);
            System.out.println("Response: " + response);
            card.disconnect(false);
        } catch (CardException e) {
            throw new RuntimeException(e);
        }
    }
}
