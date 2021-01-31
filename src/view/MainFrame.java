package view;

import model.Role;
import view.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements Runnable {

    private boolean isFullScreen;
    private MainPanel mainPanel;
    private LoginPanel loginPanel;
    private ProfilePanel profilePanel;
    private SignUpPanel signUpPanel;
    private PostPanel postPanel;

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

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {}

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

        initComponents();

        setState(State.PROFILE);
    }

    private void initComponents() {
        handleMenuBar();

        mainPanel = new MainPanel();
        loginPanel = new LoginPanel() {

            @Override
            protected void signUpAction() {

            }

            @Override
            protected void loginAction() {

            }

            @Override
            protected void backAction() {

            }
        };

        postPanel = new PostPanel() {
            @Override
            protected void backAction() {

            }

            @Override
            protected void postAction() {

            }
        };
        profilePanel = new ProfilePanel() {

            @Override
            protected void deleteAccountAction() {

            }

            @Override
            protected void changeNameAction() {

            }

            @Override
            protected void addMoneyAction() {

            }

            @Override
            protected String getCustomerName() {
                return null;
            }

            @Override
            protected int getMoney() {
                return 0;
            }

            @Override
            protected Role getRole() {
                return null;
            }

            @Override
            protected void backAction() {

            }
        };
        signUpPanel = new SignUpPanel();
    }

    private void setState(State state) {
        if (state == State.LOGIN) {
            setContentPane(loginPanel);
        } else if (state == State.MAIN) {
            setContentPane(mainPanel);
        } else if (state == State.PROFILE) {
            setContentPane(profilePanel);
        } else if (state == State.SIGNUP) {
            setContentPane(signUpPanel);
        } else if (state == State.POST) {
            setContentPane(postPanel);
        }
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

    @Override
    public void run() {
        setVisible(true);
    }
}
