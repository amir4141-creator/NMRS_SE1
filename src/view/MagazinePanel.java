package view;

import javax.swing.*;
import java.awt.*;

public abstract class MagazinePanel extends JPanel {

    private JTextArea content;
    private JLabel titleLabel;
    private JButton commentButton;
    private JButton likeButton;
    private JButton downloadButton;
    protected boolean isLiked;

    public MagazinePanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(5, 5));

        content = new JTextArea(getContent());
        content.setEditable(false);
        titleLabel = new JLabel(getTitle(), JLabel.CENTER);
        commentButton = new JButton("Comments");
        commentButton.addActionListener(e -> commentButtonAction());
        isLiked = false;
        downloadButton = new JButton("Download");
        downloadButton.addActionListener(e -> downloadButtonAction());
        likeButton = new JButton("Like - " + getLikeNumber());
        likeButton.setForeground(Color.RED.darker());
        likeButton.addActionListener(e -> likeButtonAction());

        add(new JScrollPane(content), BorderLayout.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        titleLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        titleLabel.setFont(new Font("serif", Font.BOLD, 28));

        downloadButton.setEnabled(isDownloadable());

        var wrapper = new JPanel(new GridLayout(1, 0));
        wrapper.add(likeButton);
        wrapper.add(commentButton);
        wrapper.add(downloadButton);
        wrapper.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));

        add(wrapper, BorderLayout.SOUTH);

        likeButton.addActionListener(e -> {
            isLiked = !isLiked;
            if (isLiked) {
                likeButton.setForeground(Color.GREEN.darker());
            } else {
                likeButton.setForeground(Color.RED.darker());
            }
        });

        var f = new Font("serif", Font.PLAIN, 20);
        likeButton.setFont(f);
        downloadButton.setFont(f);
        commentButton.setFont(f);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLoweredBevelBorder()
        ));
    }

    protected abstract String getTitle();
    protected abstract String getContent();
    protected abstract boolean isDownloadable();
    protected abstract int getLikeNumber();
    protected abstract void commentButtonAction();
    protected abstract void likeButtonAction();
    protected abstract void downloadButtonAction();
}
