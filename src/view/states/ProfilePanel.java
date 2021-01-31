package view.states;

import model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class ProfilePanel extends JPanel {

    protected JLabel nameLabel;
    protected JLabel roleLabel;
    protected JLabel moneyLabel;
    protected JButton backButton;
    protected JButton deleteAccount;
    protected JTable subscribed;
    protected JTable published;
    protected JButton addMoney;
    protected JButton changeName;
    protected JButton privacy;

    public ProfilePanel() {
        setLayout(new BorderLayout(20, 20));
        init();
    }

    private void init() {
        deleteAccount = new JButton("Delete Account");
        deleteAccount.addActionListener(e -> deleteAccountAction());
        privacy = new JButton("Privacy");
        privacy.addActionListener(e -> privacyAction());
        changeName = new JButton("Change Name");
        changeName.addActionListener(e -> changeNameAction());
        addMoney = new JButton("Add Money");
        addMoney.addActionListener(e -> addMoneyAction());
        nameLabel = new JLabel("", JLabel.CENTER);
        roleLabel = new JLabel("", JLabel.CENTER);
        moneyLabel = new JLabel("", JLabel.CENTER);
        backButton = new JButton("Back");
        subscribed = new JTable();
        subscribed.setModel(new DefaultTableModel(new Object[][] {}, new Object[] {"No.", "Name"}));
        subscribed.getColumnModel().getColumn(0).setMaxWidth(50);
        subscribed.setRowHeight(40);
        published = new JTable(new Object[][] {}, new Object[] {"No.", "Name"});
        published.getColumnModel().getColumn(0).setMaxWidth(50);
        published.setRowHeight(40);


        backButton.addActionListener(e -> backAction());

        var wrapper = new JPanel(new GridLayout(2, 3, 30, 0));
        wrapper.add(nameLabel);
        wrapper.add(moneyLabel);
        wrapper.add(roleLabel);
        wrapper.add(changeName);
        wrapper.add(addMoney);
        wrapper.add(privacy);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        add(wrapper, BorderLayout.NORTH);

        var wrapper2 = new JPanel(new GridLayout(0, 1));
        wrapper2.add(new JScrollPane(subscribed));
        if (getRole() == Role.PUBLISHER)
            wrapper2.add(new JScrollPane(published));

        add(wrapper2, BorderLayout.CENTER);

        insertSubscribe("AMIR_HOSSEIN");
        insertSubscribe("ARMAN");

        nameLabel.setText("   Name: " + getCustomerName());
        roleLabel.setText("   Role: " + getRole());
        moneyLabel.setText("   Money: " + getMoney());

        var wrapper3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper3.add(deleteAccount);
        wrapper3.add(backButton);

        add(wrapper3, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void privacyAction() {
        String message = "null";
        JOptionPane.showMessageDialog(null, message, "Privacy Policy", JOptionPane.INFORMATION_MESSAGE);
    }

    protected abstract void deleteAccountAction();

    protected abstract void changeNameAction();

    protected abstract void addMoneyAction();

    protected abstract String getCustomerName();

    protected abstract int getMoney();

    protected abstract Role getRole();

    protected void insertSubscribe(String name) {
        ((DefaultTableModel) subscribed.getModel()).addRow(new Object[] {subscribed.getRowCount()+1, name});
    }

    protected void insertPublished(String name) {
        ((DefaultTableModel) published.getModel()).addRow(new Object[] {published.getRowCount()+1, name});
    }

    protected abstract void backAction();
}
