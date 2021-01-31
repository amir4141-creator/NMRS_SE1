package view.states;

import javax.swing.*;
import java.awt.*;

public abstract class PostPanel extends JPanel {

    protected JTextArea textArea;
    protected JButton postButton;
    protected JCheckBox downloadableCheckBox;
    protected JTextField title;
    protected JTextField price;

    public PostPanel() {
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        textArea = new JTextArea("Content...");
        postButton = new JButton("Post");
        downloadableCheckBox = new JCheckBox("Downloadable");
        title = new JTextField("Title...");
        title.setFont(new Font("serif", Font.BOLD, 28));
        title.setPreferredSize(new Dimension(600, 60));
        price = new JTextField("Price...");

        var wrapper = new JPanel(new BorderLayout(20, 20));
        var j1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        j1.add(title);
        var j2 = new JPanel(new GridLayout(1, 2, 600, 0));
        j2.setPreferredSize(new Dimension(1280, 50));
        j2.add(price);
        j2.add(downloadableCheckBox);
        wrapper.add(textArea, BorderLayout.CENTER);
        wrapper.add(j1, BorderLayout.NORTH);
        wrapper.add(j2, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> backAction());
        var j3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        postButton.addActionListener(e -> postAction());
        j3.add(backButton);
        j3.add(postButton);

        add(j3, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    protected abstract void backAction();
    protected abstract void postAction();
}
