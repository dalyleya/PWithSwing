package world.places;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 14.02.13
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class WorldMap {
    private static WorldMap ourInstance = new WorldMap();

    private List<Place> upperLevelPlaces = new ArrayList<Place>();

    private Map<String, Place> places = new HashMap<String, Place>();

    public static WorldMap getInstance() {
        return ourInstance;
    }

    private WorldMap() {
    }

    public Place addPlace(Place parent, String name) {
        Place place = new Place(name, parent);
        if (parent == null){
            upperLevelPlaces.add(place);
        }

        String placeId = null;

        do{
            placeId = UUID.randomUUID().toString();
        } while (places.containsKey(placeId));
        place.setId(placeId);
        places.put(placeId, place);
        return place;
    }

    public List<Place> getUpperLevelPlaces(){
        return Collections.unmodifiableList(upperLevelPlaces);
    }

    public int getUpperLevelCount() {
        return upperLevelPlaces.size();
    }

    public void deletePlace(Place place) {
        upperLevelPlaces.remove(place);
    }

    public Place getPlace(String id) {
        return places.get(id);
    }

    public void saveMap(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(path), upperLevelPlaces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMap(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        upperLevelPlaces = mapper.readValue(new File(path), new TypeReference<List<Place>>(){});

        places.clear();

        for (Place place: upperLevelPlaces){
            addPlace(place);
        }
    }

    private void addPlace(Place place) {
        for (Place child: place.getChildren()){
            addPlace(child);
        }
        places.put(place.getId(), place);
    }
}

