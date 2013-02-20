package gui;

import gui.utils.PlacesTreeModel;
import gui.utils.VirtualPlacesRoot;
import world.places.Place;
import world.places.WorldMap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14.02.13
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class Form1 {
    private JPanel myPanel;
    private JTree tree1;
    private JButton deleteButton;
    private JButton addLocationButton;
    private JButton linkLocationsButton;
    private JButton saveButton;
    private JButton openButton;

    public Form1() {
        tree1.setModel(new PlacesTreeModel());
        addLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Place node = (Place) tree1.getLastSelectedPathComponent();

                NameDialogue dialogue = new NameDialogue(node);
                dialogue.setVisible(true);
                tree1.setModel(new PlacesTreeModel());
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Place node = (Place) tree1.getLastSelectedPathComponent();
                if (node == null || (node instanceof VirtualPlacesRoot)) {
                    JOptionPane.showMessageDialog(null, "Choose something to be destroyed");
                }
                else {
                    if (node.hasParent()) {
                        node.getParent().deleteChildren(node);
                    } else WorldMap.getInstance().deletePlace(node);
                    tree1.setModel(new PlacesTreeModel());
                }
            }
        });
        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    Place node = (Place) tree1.getLastSelectedPathComponent();
                    if (node == null) JOptionPane.showMessageDialog(null, "Choose something to edit");
                    else {
                        PlaceDialogue placeDialogue = new PlaceDialogue(node);
                        placeDialogue.setVisible(true);
                    }
                }
            }
        });
        linkLocationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tree1.getSelectionCount() > 1){
                    TreePath[] selectionPaths = tree1.getSelectionPaths();

                    for (int i = 0; i < selectionPaths.length; i++){
                        Place placeFrom = (Place)selectionPaths[i].getLastPathComponent();

                        for (int k = i + 1; k < selectionPaths.length; k++){
                            Place placeTo = (Place)selectionPaths[k].getLastPathComponent();
                            placeFrom.linkTo(placeTo);
                            placeTo.linkTo(placeFrom);
                        }
                    }
                }
                tree1.setModel(new PlacesTreeModel());
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorldMap.getInstance().saveMap("map.json");
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    WorldMap.getInstance().readMap("map.json");
                    tree1.setModel(new PlacesTreeModel());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Form1");
        frame.setContentPane(new Form1().myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
