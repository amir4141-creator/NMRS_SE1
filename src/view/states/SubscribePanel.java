package view.states;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class SubscribePanel extends JPanel {
    private JList<PublisherInfo> publisherList;
    private JButton backButton;
    private JButton okButton;
    private ArrayList<String> selectedPublisherNames;

    public SubscribePanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        selectedPublisherNames = new ArrayList<>();

        publisherList = new JList<>(getPublisherInfo());
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
        publisherList.setBackground(Color.DARK_GRAY.darker());
        publisherList.setCellRenderer((list, value, index, isSelected, ch) -> {
            var c = new JCheckBox();
            c.setSelected(value.isSelected());
            c.setText(value.toString());
            c.setPreferredSize(new Dimension(1100, 36));
            c.setOpaque(true);
            c.setBackground(list.getBackground());

            var p = new JPanel(new BorderLayout());
            p.add(c, BorderLayout.CENTER);
            p.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.DARK_GRAY));

            return p;
        });

        add(new JScrollPane(publisherList), BorderLayout.CENTER);

        var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.add(okButton);
        wrapper.add(backButton);
        add(wrapper, BorderLayout.SOUTH);

        publisherList.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(2, 2, 2, 2)));

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    protected abstract void backAction();

    protected abstract String[] getPublisherNames();

    // an empty array list will be filled with name of selected publishers
    // set selected names array of customer by customer.setSelectedPublishers(selectedPublishersName)
    protected abstract void okButtonAction(ArrayList<String> selectedPublisherNames);

    private PublisherInfo[] getPublisherInfo() {
        var names = getPublisherNames();
        var res = new PublisherInfo[names.length];
        for (int i = 0; i < names.length; i++)
            res[i] = new PublisherInfo(names[i]);
        return res;
    }
}

class PublisherInfo {
    private final String name;
    private boolean isSelected;

    public PublisherInfo(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
