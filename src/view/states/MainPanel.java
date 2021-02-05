package view.states;

import models.Role;
import view.MagazinePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class MainPanel extends JPanel {

    protected JButton logoutButton;
    protected JButton publishNewMagazineButton;
    protected JButton subscribeButton;
    protected JButton profileButton;
    protected JPanel magazinePanelList;

    public MainPanel() {
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

        logoutButton = new JButton("Logout");
        publishNewMagazineButton = new JButton("Publish");
        profileButton = new JButton("Profile");
        subscribeButton = new JButton("Subscribe");

        logoutButton.addActionListener(e -> logoutButtonAction());
        publishNewMagazineButton.addActionListener(e -> publishNewMagazineButtonAction());
        subscribeButton.addActionListener(e -> subscribeButtonAction());
        profileButton.addActionListener(e -> profileButtonAction());

        var wrapper = new JPanel(new BorderLayout(10, 10));
        var wrapper3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper3.add(logoutButton);
        wrapper.add(wrapper3, BorderLayout.WEST);

        var wrapper2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper2.add(profileButton);
        wrapper2.add(subscribeButton);
        if (getRole() == Role.PUBLISHER)
            wrapper2.add(publishNewMagazineButton);

        wrapper.add(wrapper2, BorderLayout.EAST);

        add(wrapper, BorderLayout.NORTH);

        getMagazineIndexArray().forEach(this::insertMagazine);

        add(new JScrollPane(magazinePanelList), BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void insertMagazine(int index) {
        magazinePanelList.add(new MagazinePanel(index) {

            @Override
            protected String getTitle() {
                return MainPanel.this.getTitle(index);
            }

            @Override
            protected String getContent() {
                return MainPanel.this.getContent(index);
            }

            @Override
            protected boolean isDownloadable() {
                return MainPanel.this.isDownloadable(index);
            }

            @Override
            protected int getLikeNumber() {
                return MainPanel.this.getLikeNumber(index);
            }

            @Override
            protected void commentButtonAction() {
                MainPanel.this.commentButtonAction(index);
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
                MainPanel.this.downloadButtonAction(index);
            }
        });
    }

    protected abstract Role getRole();
    protected abstract ArrayList<Integer> getMagazineIndexArray();
    protected abstract void logoutButtonAction();
    protected abstract void subscribeButtonAction();
    protected abstract void profileButtonAction();
    protected abstract void publishNewMagazineButtonAction();
    protected abstract String getTitle(int index);
    protected abstract String getContent(int index);
    protected abstract boolean isDownloadable(int index);
    protected abstract int getLikeNumber(int index);
    protected abstract void incrementLikeNumber(int index);
    protected abstract void decrementLikeNumber(int index);
    protected abstract void downloadButtonAction(int index);
    protected abstract void commentButtonAction(int index);
}

