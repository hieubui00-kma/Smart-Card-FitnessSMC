package com.kma.fitnesssmc.data.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.smartcardio.*;
import java.util.List;

import static com.kma.fitnesssmc.util.Constants.AID;

public class SessionManager {
    private static SessionManager instance = null;

    private Card card;

    private SessionManager() {

    }

    public @Nullable Card connect() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        Card card = terminals.get(0).connect("*");
        CardChannel channel = card.getBasicChannel();
        CommandAPDU selectCommand = new CommandAPDU(0, 164, 4, 0, AID);
        ResponseAPDU response = channel.transmit(selectCommand);

        return response.getSW1() == 0x90 && response.getSW2() == 0x00 ? this.card = card : null;
    }

    public ResponseAPDU transmit(CommandAPDU command) throws NullPointerException, CardException {
        return card.getBasicChannel().transmit(command);
    }

    public void disconnect() throws NullPointerException, CardException {
        card.disconnect(false);
    }

    public static @NotNull SessionManager getInstance() {
        return instance == null ? instance = new SessionManager() : instance;
    }
}
