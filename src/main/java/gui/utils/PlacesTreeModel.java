package gui.utils;

import world.places.Place;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14.02.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class PlacesTreeModel implements TreeModel {
    @Override
    public Object getRoot() {
        return new VirtualPlacesRoot();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Place place = (Place) parent;

        int childrenCount = place.getChildrenCount();
        if (index >= childrenCount) {
            return place.getLinkedPlace(index - childrenCount);
        }

        return place.getChildren().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Place place = (Place) parent;
        return place.getChildrenCount() + place.getLinkedCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        Place place = (Place) node;
        return place.getChildrenCount() + place.getLinkedCount() == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
