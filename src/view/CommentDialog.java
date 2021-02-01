package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class CommentDialog extends JDialog {

    private JTable comments;
    protected JTextField commentTextField;
    protected JButton commit;

    public CommentDialog() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(20, 20));

        comments = new JTable(new DefaultTableModel(new Object[][] {}, new Object[] {"No.", "UserName", "Comment"}));

        comments.setRowHeight(40);
        comments.getColumnModel().getColumn(0).setMaxWidth(35);
        comments.getColumnModel().getColumn(1).setMaxWidth(150);

        commentTextField = new JTextField("Comment....");
        commit = new JButton("Commit");
        commit.addActionListener(e -> commitAction());

        var wrapper2 = new JPanel(new BorderLayout());
        wrapper2.add(new JScrollPane(comments), BorderLayout.CENTER);
        add(wrapper2, BorderLayout.CENTER);
        wrapper2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        var wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setPreferredSize(new Dimension(1160, 50));
        wrapper.add(commentTextField, BorderLayout.CENTER);
        wrapper.add(commit, BorderLayout.EAST);

        insertComment("Amir", "JAFAR");
        insertComment("Amir", "JAFAR");
        insertComment("Amir", "JAFAR");
        insertComment("Amir", "JAFAR");
        insertComment("Amir", "JAFAR");

        add(wrapper, BorderLayout.SOUTH);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    protected void insertComment(String name, String comment) {
        ((DefaultTableModel) comments.getModel()).addRow(new Object[] {comments.getRowCount()+1, name, comment});
    }

    protected abstract void commitAction();
}
