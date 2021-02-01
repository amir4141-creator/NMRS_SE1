package controller;

import model.Role;
import view.MainFrame;

import javax.swing.*;

public class Controller {

    public static void run() {
        SwingUtilities.invokeLater(new MainFrame() {
            @Override
            protected boolean signUpActionLoginPanel(String username, String password) {
                return false;
            }

            @Override
            protected boolean loginActionLoginPanel(String username, String password) {
                return false;
            }

            @Override
            protected void postActionPostPanel() {

            }

            @Override
            protected void deleteActionProfilePanel() {

            }

            @Override
            protected boolean changeNameActionProfilePanel(String nawName) {
                return false;
            }

            @Override
            protected boolean addMoneyActionProfilePanel(int changeMoneyAmount) {
                return false;
            }

            @Override
            protected String getNameProfilePanel() {
                return null;
            }

            @Override
            protected int getMoneyProfilePanel() {
                return 0;
            }

            @Override
            protected Role getRoleProfilePanel() {
                return null;
            }
        });
    }
}
