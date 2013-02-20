package gui.utils;

import world.places.Place;
import world.places.WorldMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14.02.13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class VirtualPlacesRoot extends Place {


    public VirtualPlacesRoot() {
        super("VRP", null);
    }

    @Override
    public List<Place> getChildren() {
        return WorldMap.getInstance().getUpperLevelPlaces();
    }

    @Override
    public int getChildrenCount() {
        return WorldMap.getInstance().getUpperLevelCount();
    }
}
