package com;

import com.ui.Controller;
import com.ui.View;

import javax.swing.*;
import java.awt.*;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(((thread, throwable) -> {
            JOptionPane.showMessageDialog(
                    null,
                    throwable.getClass().getSimpleName() + " " + throwable.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }));

            var view = new View();
            var controller = new Controller(view);
        
    }
}
