package view;

import models.Role;
import view.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MainFrame extends JFrame implements Runnable {

    private boolean isFullScreen;
    private boolean isDarkTheme = true;
    private boolean isFirstCompile = true;
    private MainPanel mainPanel;
    private LoginPanel loginPanel;
    private ProfilePanel profilePanel;
    private PostPanel postPanel;
    private SubscribePanel subscribePanel;
    private StartPanel startPanel;

    public MainFrame() {
        init();
    }

    private void init() {

        isFullScreen = false;

        setTitle("Newspaper And Magazine");
        setSize(1280, 720);
        setLocationByPlatform(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeAction();
            }
        });

        handleUIManager();

        initComponents();

        setState(State.START);
    }

    private void initComponents() {
        handleMenuBar();

        mainPanel = null;
        postPanel = null;
        profilePanel = null;
        subscribePanel = null;

        startPanel = new StartPanel() {

            @Override
            protected void setSignInButtonButtonAction() {
                setState(State.LOGIN);
            }

            @Override
            protected String getTitle(int index) {
                return MainFrame.this.getTitle(index);
            }

            @Override
            protected String getContent(int index) {
                return MainFrame.this.getContent(index);
            }

            @Override
            protected boolean isDownloadable(int index) {
                return MainFrame.this.isDownloadable(index);
            }

            @Override
            protected int getLikeNumber(int index) {
                return MainFrame.this.getLikeNumber(index);
            }

            @Override
            protected void incrementLikeNumber(int index) {
                incrementLikeByIndex(index);
            }

            @Override
            protected void decrementLikeNumber(int index) {
                decrementLikeByIndex(index);
            }

            @Override
            protected void downloadButtonAction(int index) {
                MainFrame.this.downloadButtonAction(index);
            }

            @Override
            protected void commentButtonAction(int index) {
                commentAction(index);
            }

            @Override
            protected ArrayList<Integer> getPubicMagazineIndexes() {
                return getPublicIndexesStartPanel();
            }
        };
        loginPanel = new LoginPanel() {

            @Override
            protected void signUpAction() {
                if (signUpActionLoginPanel(userNameTextField.getText(), new String(passwordField.getPassword()), (Role) role.getSelectedItem()))
                    goToMainState();
            }

            @Override
            protected void loginAction() {
                if (loginActionLoginPanel(userNameTextField.getText(), new String(passwordField.getPassword())))
                    goToMainState();
            }

            @Override
            protected void backAction() {
                setState(State.START);
            }
        };
    }

    private void setMainPanel() {
        mainPanel = new MainPanel() {
            @Override
            protected Role getRole() {
                return getRoleProfilePanel();
            }

            @Override
            protected ArrayList<Integer> getMagazineIndexArray() {
                return getMagazineIndexList();
            }

            @Override
            protected void logoutButtonAction() {
                MainFrame.this.logoutButtonAction();
                mainPanel = null;
                postPanel = null;
                profilePanel = null;
                subscribePanel = null;
                setState(State.LOGIN);
            }

            @Override
            protected void subscribeButtonAction() {
                setState(State.SUBSCRIBE);
            }

            @Override
            protected void profileButtonAction() {
                setState(State.PROFILE);
            }

            @Override
            protected void publishNewMagazineButtonAction() {
                setState(State.POST);
            }

            @Override
            protected String getTitle(int index) {
                return MainFrame.this.getTitle(index);
            }

            @Override
            protected String getContent(int index) {
                return MainFrame.this.getContent(index);
            }

            @Override
            protected boolean isDownloadable(int index) {
                return MainFrame.this.isDownloadable(index);
            }

            @Override
            protected int getLikeNumber(int index) {
                return MainFrame.this.getLikeNumber(index);
            }

            @Override
            protected void incrementLikeNumber(int index) {
                incrementLikeByIndex(index);
            }

            @Override
            protected void decrementLikeNumber(int index) {
                decrementLikeByIndex(index);
            }

            @Override
            protected void downloadButtonAction(int index) {
                MainFrame.this.downloadButtonAction(index);
            }

            @Override
            protected void commentButtonAction(int index) {
                commentAction(index);
            }
        };
    }

    private void setProfilePanel() {
        profilePanel = new ProfilePanel() {

            @Override
            protected void deleteAccountAction() {
                setState(State.START);
                mainPanel = null;
                postPanel = null;
                profilePanel = null;
                subscribePanel = null;
                deleteActionProfilePanel();
            }

            @Override
            protected void changeNameAction() {
                var ft = new JTextField("NewName...");
                if (JOptionPane.showConfirmDialog(null, ft, "Change Name", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
                    return;
                if (changeNameActionProfilePanel(ft.getText()))
                    updateName();
            }

            @Override
            protected void addMoneyAction() {
                var mt = new JTextField("Money Amount(int)....");
                if (JOptionPane.showConfirmDialog(null, mt, "Add Money", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
                    return;
                try {
                    if (addMoneyActionProfilePanel(Integer.parseInt(mt.getText())))
                        updateMoney();
                } catch (NumberFormatException ignore) {}
            }

            @Override
            protected String getCustomerName() {
                return getNameProfilePanel();
            }

            @Override
            protected int getMoney() {
                return getMoneyProfilePanel();
            }

            @Override
            protected Role getRole() {
                return getRoleProfilePanel();
            }

            @Override
            protected ArrayList<String> getSubscribedMagazine() {
                return getSubscribedMagazineProfilePanel();
            }

            @Override
            protected ArrayList<String> getPublishedMagazine() {
                return getPublishedMagazineProfilePanel();
            }

            @Override
            protected void backAction() {
                goToMainState();
            }
        };
    }

    private void setSubscribePanel() {
        subscribePanel = new SubscribePanel() {
            @Override
            protected void backAction() {
                goToMainState();
            }

            @Override
            protected HashMap<String, Integer> getMagazineInfo() {
                return getMagazineInfoArraySubscribePanel();
            }

            @Override
            protected void okButtonAction(ArrayList<String> selectedPublisherNames) {
                setSubscribedPublishersSubscribePanel(selectedPublisherNames);
            }
        };
    }

    public void setPostPanel() {
        postPanel = new PostPanel() {
            @Override
            protected void backAction() {
                goToMainState();
            }

            @Override
            protected void postAction() {
                try {
                    mainPanel.insertMagazine(postActionPostPanel(title.getText(), textArea.getText(),
                            publicCheckBox.isSelected() ? 0 : Integer.parseInt(price.getText()), downloadableCheckBox.isSelected(), publicCheckBox.isSelected()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                goToMainState();
            }
        };
    }

    /// start panel
    protected abstract ArrayList<Integer> getPublicIndexesStartPanel();
    ///

    /// login panel
    protected abstract boolean signUpActionLoginPanel(String username, String password, Role role);
    protected abstract boolean loginActionLoginPanel(String username, String password);
    ///

    /// post panel
    // last index should be returned
    protected abstract int postActionPostPanel(String title, String content, int price, boolean isDownloadable, boolean isPublic);
    ///

    /// profile panel
    protected abstract void deleteActionProfilePanel();
    protected abstract boolean changeNameActionProfilePanel(String nawName);
    protected abstract boolean addMoneyActionProfilePanel(int changeMoneyAmount);
    protected abstract String getNameProfilePanel();
    protected abstract int getMoneyProfilePanel();
    protected abstract Role getRoleProfilePanel();
    protected abstract ArrayList<String> getPublishedMagazineProfilePanel();
    protected abstract ArrayList<String> getSubscribedMagazineProfilePanel();
    ///

    /// subscribe panel
    protected abstract HashMap<String, Integer> getMagazineInfoArraySubscribePanel();
    protected abstract void setSubscribedPublishersSubscribePanel(ArrayList<String> selectedPublisherNames);
    ///

    /// main panel
    protected abstract String getTitle(int index);
    protected abstract String getContent(int index);
    protected abstract int getLikeNumber(int index);
    protected abstract boolean isDownloadable(int index);
    protected abstract ArrayList<Integer> getMagazineIndexList();
    protected abstract void logoutButtonAction();
    protected abstract void downloadButtonAction(int index);
    ///

    /// main frame
    protected abstract boolean isOnline();
    ///

    /// main panel
    // 0 is name, 1 is content
    protected abstract ArrayList<String[]> getCommentByIndex(int index);
    ///

    /// comment dialog
    protected abstract boolean addComment(int magazineIndex, String[] newComment);
    ///

    /// magazine panel
    protected abstract void incrementLikeByIndex(int index);
    protected abstract void decrementLikeByIndex(int index);
    ///

    private void commentAction(int index) {
        var cd = new CommentDialog() {
            @Override
            protected void commitAction() {
                if (!isOnline()) {
                    JOptionPane.showMessageDialog(null, "You Should Login First", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                var content = commentTextField.getText();
                if (content.replace(" ", "").isEmpty())
                    return;
                var arr = new String[] {getNameProfilePanel(), content};
                if (addComment(index, arr))
                    insertComment(arr);
            }
        };
        cd.addAllComments(getCommentByIndex(index));
        cd.setVisible(true);
    }

    private void goToMainState() {
        setState(State.MAIN);
    }

    private void setState(State state) {
        if (state == State.LOGIN) {
            setContentPane(loginPanel);
        } else if (state == State.MAIN) {
            if (mainPanel == null)
                setMainPanel();
            setContentPane(mainPanel);
        } else if (state == State.PROFILE) {
            if (profilePanel == null)
                setProfilePanel();
            profilePanel.prepare();
            setContentPane(profilePanel);
        } else if (state == State.POST) {
            if (postPanel == null)
                setPostPanel();
            postPanel.prepare();
            setContentPane(postPanel);
        } else if (state == State.SUBSCRIBE) {
            if (subscribePanel == null)
                setSubscribePanel();
            subscribePanel.prepare();
            setContentPane(subscribePanel);
        } else if (state == State.START) {
            startPanel.prepare();
            setContentPane(startPanel);
        }
        repaint();
        revalidate();
    }

    private void handleMenuBar() {
        var menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");

        JMenuItem fullScreen = new JMenuItem("Full Screen");
        fullScreen.addActionListener(e -> toggleFullScreen());
        fullScreen.setAccelerator(KeyStroke.getKeyStroke("F11"));
        viewMenu.add(fullScreen);

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(e -> closeAction());
        quit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.ALT_DOWN_MASK));
        fileMenu.add(quit);

        JMenuItem tray = new JMenuItem("System Tray");
        tray.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
        viewMenu.add(tray);

        JMenuItem toggleDarkTheme = new JMenuItem("Toggle Dark Theme");
        toggleDarkTheme.addActionListener(e -> toggleDarkTheme());
        toggleDarkTheme.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_DOWN_MASK));
        viewMenu.add(toggleDarkTheme);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        handleSystemTray(menuBar);

    }

    public void toggleFullScreen() {
        isFullScreen = !isFullScreen;
        setVisible(false);
        if (isFullScreen) {
            dispose();
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
        } else {
            dispose();
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
            setSize(1300, 720);
            setVisible(true);
        }
    }

    private void handleSystemTray(JMenuBar menuBar) {
        TrayIcon trayIcon;
        SystemTray tray;
        String tooltip = getTitle();
        String exitText = "Exit";
        String openText = "Open";
        PopupMenu popupMenu;

        if (!SystemTray.isSupported()) return;

        tray = SystemTray.getSystemTray();
        popupMenu = new PopupMenu();

        trayIcon = new TrayIcon(new ImageIcon(".\\res\\icon.png").getImage(), tooltip, popupMenu);
        JFrame main = this;
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    main.setVisible(true);
                    main.setState(Frame.NORMAL);
                }
            }
        });
        trayIcon.setImageAutoSize(true);

        MenuItem defaultItem = new MenuItem(exitText);
        defaultItem.addActionListener(e -> System.exit(0));
        popupMenu.add(defaultItem);

        defaultItem = new MenuItem(openText);
        defaultItem.addActionListener(e -> {
            this.setVisible(true);
            this.setExtendedState(JFrame.NORMAL);
        });
        popupMenu.add(defaultItem);

        this.add(popupMenu);

        this.addWindowStateListener(windowEvent -> {

            if (windowEvent.getNewState() == Frame.MAXIMIZED_BOTH) {
                tray.remove(trayIcon);
                main.setVisible(true);
            }

            if (windowEvent.getNewState() == Frame.NORMAL) {
                tray.remove(trayIcon);
                main.setVisible(true);
            }

        });

        menuBar.getMenu(1).getItem(1).addActionListener(e -> {
            try {
                tray.add(trayIcon);
                main.setVisible(false);
            } catch (AWTException ignore) {
            }
        });

        trayIcon.addActionListener(e -> tray.remove(trayIcon));

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (main.isVisible()) tray.remove(trayIcon);
        }, AWTEvent.ACTION_EVENT_MASK + AWTEvent.WINDOW_EVENT_MASK);

    }

    private void closeAction() {
        System.exit(0);
    }

    public void toggleDarkTheme() {
        isDarkTheme = !isDarkTheme;
        handleUIManager();
        repaint();
    }

    private void handleUIManager() {
        if (isFirstCompile)
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.exit(-1);
            }

        isFirstCompile = false;

        if (isDarkTheme) {
            UIManager.put("control", Color.DARK_GRAY.darker());
            UIManager.put("info", new Color(128,128,128));
            UIManager.put("nimbusBase", new Color( 18, 30, 49));
            UIManager.put("nimbusAlertYellow", new Color( 248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color( 128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115,164,209));
            UIManager.put("nimbusGreen", new Color(176,179,50));
            UIManager.put("nimbusInfoBlue", new Color( 66, 139, 221));
            UIManager.put("nimbusLightBackground", Color.DARK_GRAY);
            UIManager.put("nimbusOrange", new Color(191,98,4));
            UIManager.put("nimbusRed", new Color(169,46,34) );
            UIManager.put("nimbusSelectedText", new Color( 255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color( 104, 93, 156));
            UIManager.put("text", new Color( 230, 230, 230));
        } else {
            UIManager.put("control", new Color(214,217,223));
            UIManager.put("info", new Color(242,242,189));
            UIManager.put("nimbusBase", new Color( 51,98,140));
            UIManager.put("nimbusAlertYellow", new Color( 255,220,35));
            UIManager.put("nimbusDisabledText", new Color( 142,143,145));
            UIManager.put("nimbusFocus", new Color(115,164,209));
            UIManager.put("nimbusGreen", new Color(176,179,50));
            UIManager.put("nimbusInfoBlue", new Color( 47,92,180));
            UIManager.put("nimbusLightBackground", new Color(255,255,255));
            UIManager.put("nimbusOrange", new Color(191,98,4));
            UIManager.put("nimbusRed", new Color(169,46,34) );
            UIManager.put("nimbusSelectedText", new Color( 255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color( 57,105,138));
            UIManager.put("text", new Color( 0, 0, 0));
        }
    }

    @Override
    public void run() {
        setVisible(true);
    }
}
