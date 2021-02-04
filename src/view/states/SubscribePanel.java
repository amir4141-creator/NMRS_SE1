package view.states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class SubscribePanel extends JPanel {
    private JList<MagazineInfo> publisherList;
    private JButton backButton;
    private JButton okButton;
    private ArrayList<String> selectedPublisherNames;

    public SubscribePanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        selectedPublisherNames = new ArrayList<>();

        okButton = new JButton("OK");
        okButton.addActionListener(e -> backButton.doClick());
        okButton.addActionListener(e -> okButtonAction(selectedPublisherNames));
        okButton.addActionListener(e -> {
            for (int i = 0; i < publisherList.getModel().getSize(); i++) {
                var pi = publisherList.getModel().getElementAt(i);
                if (pi.isSelected())
                    selectedPublisherNames.add(pi.toString());
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> backAction());

        var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.add(okButton);
        wrapper.add(backButton);
        add(wrapper, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    protected abstract void backAction();

    protected abstract HashMap<String, Integer> getMagazineInfo();

    // an empty array list will be filled with name of selected publishers
    // set selected names array of customer by customer.setSelectedMagazines(selectedMagazinesTitle)
    protected abstract void okButtonAction(ArrayList<String> selectedPublisherNames);

    private MagazineInfo[] getMagazineListInfo() {
        var names = this.getMagazineInfo();
        var res = new MagazineInfo[names.size()];
        int counter = 0;
        for (var kv : names.entrySet())
            res[counter++] = new MagazineInfo(kv.getKey(), kv.getValue());
        return res;
    }

    public void prepare() {
        removeAll();
        init();
        if (publisherList != null)
            remove(publisherList);
        publisherList = new JList<>(this.getMagazineListInfo());
        publisherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        publisherList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = publisherList.locationToIndex(e.getPoint());
                var element = publisherList.getModel().getElementAt(index);
                element.setSelected(!element.isSelected());
                publisherList.repaint(publisherList.getCellBounds(index, index));
            }
        });
//        publisherList.setBackground(MainFrame.isDarkTheme ? Color.DARK_GRAY.darker() : new JLabel().getBackground());
        publisherList.setCellRenderer((list, value, index, isSelected, ch) -> {
            var c = new JCheckBox();
            c.setSelected(value.isSelected());
            c.setText(value.toString());
            c.setPreferredSize(new Dimension(900, 36));
            c.setOpaque(true);
            c.setBackground(list.getBackground());

            var p = new JPanel(new BorderLayout());
            p.add(c, BorderLayout.CENTER);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.DARK_GRAY));

            var price = new JLabel(String.valueOf(value.getPrice()), JLabel.LEFT);
            p.add(price, BorderLayout.EAST);

            return p;
        });

        add(new JScrollPane(publisherList), BorderLayout.CENTER);

        publisherList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    }
}

class MagazineInfo {
    private final String name;
    private final int price;
    private boolean isSelected;

    public MagazineInfo(String name, int price) {
        this.name = name;
        this.price = price;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
