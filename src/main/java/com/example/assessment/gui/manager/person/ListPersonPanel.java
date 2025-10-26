package com.example.assessment.gui.manager.person;

import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.NonNull;

// Panel to list all person in the database
@CheckReturnValue
public final class ListPersonPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final transient IPersonBackend personBackend;

    @NonNull
    private final DefaultListModel<IPerson> listModel = new DefaultListModel<>();
    @NonNull
    private final JList<IPerson> list = new JList<>(listModel);
    @NonNull
    private final JScrollPane scrollPane;
    @NonNull
    private final JButton returnButton = new JButton("Return");

    public ListPersonPanel(@NonNull IPersonBackend personBackend, @NonNull ActionListener selectButtonAction, @NonNull ActionListener goBackAction) {
//       Init variables
        this.personBackend = personBackend;
        this.returnButton.addActionListener(goBackAction);
        this.returnButton.addActionListener((e) -> this.cleanup());

//        Add fields
        JPanel dummyPanel = new JPanel(new BorderLayout());
        dummyPanel.add(this.list, BorderLayout.CENTER);
        this.scrollPane = new JScrollPane(dummyPanel);

        this.list.setCellRenderer(new MyCellRenderer());
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scroll

        JPanel buttonPanel = new JPanel();
        for (Buttons bt : Buttons.values()) {
            JButton jb = new JButton(bt.getName());
            jb.setActionCommand(bt.toString());
            jb.addActionListener(selectButtonAction);
            buttonPanel.add(jb);
        }
        buttonPanel.add(returnButton);

        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void setup() {
//        Clean up previous data
        this.cleanup();

//        Add all person from database to list
        ImmutableList<IPerson> persons = this.personBackend.listPerson();
        for (IPerson p : persons) {
            this.listModel.addElement(p);
        }
    }

//    Clean up previous data
    public void cleanup() {
        this.listModel.clear();
    }

//    Get the person that has been selected
    public IPerson getSelectedPerson() {
        return this.list.getSelectedValue();
    }
}

// Helper class to display list content
@CheckReturnValue
class MyCellRenderer extends DefaultListCellRenderer {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean hasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
        IPerson p = (IPerson) value;
        String text = p.prettyToString().replace("\n", "<br/>");
        this.setText(String.format("<html>%s</html>", text));
        this.setPreferredSize(null);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        return this;
    }
}
