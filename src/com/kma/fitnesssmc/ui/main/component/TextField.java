package com.kma.fitnesssmc.ui.main.component;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextField extends JTextField {
    private KeyListener onMaxLengthListener;

    private KeyListener onDigitsListener;

    public TextField() {
    }

    public TextField(String text) {
        super(text);
    }

    public TextField(int columns) {
        super(columns);
    }

    public TextField(String text, int columns) {
        super(text, columns);
    }

    public TextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public void setMaxLength(int maxLength) {
        if (onMaxLengthListener != null) {
            removeKeyListener(onMaxLengthListener);
        }

        addKeyListener(onMaxLengthListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                if (getText().length() >= maxLength) {
                    event.consume();
                }
            }
        });
    }

    public void setDigits(char[] digits) {
        if (onDigitsListener != null) {
            removeKeyListener(onDigitsListener);
        }

        addKeyListener(onDigitsListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                for (char digit : digits) {
                    if (digit == event.getKeyChar()) {
                        return;
                    }
                }
                event.consume();
            }
        });
    }
}
