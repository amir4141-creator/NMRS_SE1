package view.states;

import models.Role;
import view.MagazinePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class StartPanel extends JPanel {

    protected JButton signInButton;
    protected JPanel magazinePanelList;

    public StartPanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(10, 10));

        magazinePanelList = new JPanel();
        magazinePanelList.setLayout(new BoxLayout(magazinePanelList, BoxLayout.Y_AXIS));
        magazinePanelList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        signInButton = new JButton("Sign in");
        signInButton.addActionListener(e -> setSignInButtonButtonAction());

        var wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.add(signInButton);
        add(wrapper, BorderLayout.NORTH);

        add(new JScrollPane(magazinePanelList), BorderLayout.CENTER);

        getPubicMagazineIndexes().forEach(this::insertMagazine);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void insertMagazine(int index) {
        magazinePanelList.add(new MagazinePanel(index) {

            @Override
            protected String getTitle() {
                return StartPanel.this.getTitle(index);
            }

            @Override
            protected String getContent() {
                return StartPanel.this.getContent(index);
            }

            @Override
            protected boolean isDownloadable() {
                return StartPanel.this.isDownloadable(index);
            }

            @Override
            protected int getLikeNumber() {
                return StartPanel.this.getLikeNumber(index);
            }

            @Override
            protected void commentButtonAction() {
                StartPanel.this.commentButtonAction(index);
            }

            @Override
            protected void likeButtonAction() {
//                if (isLiked) {
                    incrementLikeNumber(index);
//                } else {
//                    decrementLikeNumber(index);
//                }
                updateLikeButton();
            }

            @Override
            protected void downloadButtonAction() {
                StartPanel.this.downloadButtonAction(index);
            }
        });
    }

    protected abstract void setSignInButtonButtonAction();
    protected abstract String getTitle(int index);
    protected abstract String getContent(int index);
    protected abstract boolean isDownloadable(int index);
    protected abstract int getLikeNumber(int index);
    protected abstract void incrementLikeNumber(int index);
    protected abstract void decrementLikeNumber(int index);
    protected abstract void downloadButtonAction(int index);
    protected abstract void commentButtonAction(int index);

    protected abstract ArrayList<Integer> getPubicMagazineIndexes();

    public void prepare() {
        removeAll();
        init();
    }
}
