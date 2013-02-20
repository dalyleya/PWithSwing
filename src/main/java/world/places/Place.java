package world.places;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14.02.13
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class Place {
    private List<Place> children = new ArrayList<Place>();
    private Set<String> linkedPlaces = new TreeSet<String>();
    private String name;
    private String parentId;
    private String description = "";

    private String id;
    private List<String> cachedList;

    public Place(){}

    public Place(String name, Place parent) {
        this.name = name;

        if (parent != null){
            this.parentId = parent.getId();
        }

        if (parent != null){
            parent.addChildren(this);
        }
    }

    private void addChildren(Place place) {
        children.add(place);
    }

    @JsonIgnore
    public Place getParent() {
        return WorldMap.getInstance().getPlace(parentId);
    }

    @JsonIgnore
    public int getChildrenCount() {
        return children.size();
    }

    public void deleteChildren(Place place) {
        children.remove(place);
    }

    public Place getLinkedPlace(int i) {
        return WorldMap.getInstance().getPlace(cachedList.get(i));
    }

    public List<Place> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @JsonIgnore
    public int getLinkedCount() {
        return linkedPlaces.size();
    }

    public boolean hasParent(){
        return parentId != null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void linkTo(Place placeTo) {
        linkedPlaces.add(placeTo.getId());
        cachedList = new ArrayList<String>(linkedPlaces);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
