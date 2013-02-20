package gui;

import gui.utils.VirtualPlacesRoot;
import world.places.Place;
import world.places.WorldMap;

import javax.swing.*;
import java.awt.event.*;

public class NameDialogue extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;

    private Place parentPlace;

    public NameDialogue(Place parent) {
        if (!(parent instanceof VirtualPlacesRoot)){
            parentPlace = parent;
        }

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
    }

    private void onOK() {
        WorldMap.getInstance().addPlace(parentPlace, nameTextField.getText());
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
