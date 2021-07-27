package com.game.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.items.Item;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class GameWorld {
    public static String currentLocation = "Crash Site";
    private static String previousLocation = "Crash Site";

    //Fields for Game Assets
    private static HashMap<String, Location> planet1;
    private static HashMap<String, ArrayList<Item>> gameItems;
    private static HashMap<String, ArrayList<Item>> hiddenItems;

    public GameWorld() throws IOException {
        createGameAssets();
    }


// Methods

    private void createGameAssets() throws IOException{
        //Load our locations from planet1.json file into array of location objects

       byte[] locationData = Files.readAllBytes(Paths.get("src/resources/planet1.json"));
      //  byte[] locationData = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("planet1.json")).toExternalForm()));
        ObjectMapper objectMapper = new ObjectMapper();
        Location[] location = objectMapper.readValue(locationData, Location[].class);
     //   Location[] location = createLocation();

        //load our planet Hashmap with location objects
        planet1 = new HashMap<>();
        for (Location loc: location ) {
            planet1.put(loc.getName(), loc);
        }
        //Create Array of Items from JSON
   //    byte[] itemData = Files.readAllBytes(Paths.get("resources/items.json"));
       byte[] itemData = Files.readAllBytes(Paths.get("src/resources/items.json"));
    // byte[] itemData = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("items.json")).toExternalForm()));
        Item[] itemsArray = objectMapper.readValue(itemData,Item[].class);

        //Create our hashmap to hold our Array List for game items
        int numberOfLocations = locationData.length;
        gameItems = new HashMap<>(numberOfLocations);

        //Create Array List for each location
        for (Location loc: location) {
            gameItems.put(loc.getName(),new ArrayList<Item>());
        }

        //Load our Items into their respective location ArrayList

        for (Item item: itemsArray) {
            //Looks up the correct ArrayList based on item location in Hashmap, and adds Item element to the ArrayList
            gameItems.get(item.getLocation()).add(item);
        }

        //Create Hidden Items hashmap

        byte[] hiddenItemData = Files.readAllBytes(Paths.get("src/resources/hidden.json"));

    //  byte[] hiddenItemData = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource("hidden.json")).toExternalForm()));

        Item[] hiddenItemsArray = objectMapper.readValue(hiddenItemData,Item[].class);

        hiddenItems = new HashMap<>(numberOfLocations);
        for (Location loc: location) {
            hiddenItems.put(loc.getName(),new ArrayList<Item>());
        }
        for (Item hiddenitem : hiddenItemsArray) {
            hiddenItems.get(hiddenitem.getLocation()).add(hiddenitem);
        }
    }


    public static String getNextLocation(String currentLocationArg, String direction) {
        String nextLocation = "";
        switch (direction.toLowerCase()) {
            case "north":
                nextLocation = planet1.get(currentLocationArg).getNorth();
                break;
            case "east":
                nextLocation = planet1.get(currentLocationArg).getEast();
                break;
            case "south":
                nextLocation = planet1.get(currentLocationArg).getSouth();
                break;
            default:
                nextLocation = planet1.get(currentLocationArg).getWest();
        }
        return nextLocation;
    }


    public static void addGameItem(String loc, Item name) {
        if (loc != null && !loc.equals("")) {
            gameItems.get(loc).add(name);
        }

    }

    public static void removeGameItem(String loc, Item name) {
        if (loc != null && !loc.equals("")) {
            gameItems.get(loc).remove(name);
        }
    }

    public static void removeHiddenItem(String loc, Item name) {
        if (loc != null && !loc.equals("")) {
            hiddenItems.get(loc).remove(name);
        }
    }

    public static StringBuilder getItemsByLocation(String loc) {
        if (loc == null || loc.equals("")) {
            return null;
        }

        ArrayList<Item> itemsArray = gameItems.get(loc);
        StringBuilder LocationItems = new StringBuilder();

        for (Item item:itemsArray) {
            LocationItems.append(item.getItemName()).append(" ");
        }


        return LocationItems;
    }

    //Getters & Setters for planets & items
    public static HashMap<String, Location> getPlanet1() {
        return planet1;
    }

    public static HashMap<String, ArrayList<Item>> getGameItems() {
        return gameItems;
    }

    public static HashMap<String, ArrayList<Item>> getHiddenItems() {
        return hiddenItems;
    }


    public static String getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(String newLocation) {
        setPreviousLocation(currentLocation);
        if (currentLocation != null){
            currentLocation = newLocation;
        }


    }

    public static String getPreviousLocation() {
        return previousLocation;
    }

    private static void setPreviousLocation(String previousLocation) {
        if (previousLocation != null) {
            GameWorld.previousLocation = previousLocation;
        }
    }

    @Override
    public String toString() {
        return "gameWorld{" +
                "planet1=" + planet1 +
                '}';
    }

    private Location[] createLocation(){

//        public Location(int Id, String name, String description, String north, String east, String south, String west)
        Location location1 = new Location(1,"Crash Site","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location2 = new Location(1,"Frozen Tundra","Temperatures quickly dropped and now you're in the middle of a frozen tundra wasteland.  You don't see much around you except a barely visible path to the west",null,null,"Crash Site","Crater");
        Location location3 = new Location(3,"Crater","You fall into a large, but shallow crater.  You notice some plastic crates with some Tools sticking out.  Maybe you can fix your ship with it.  You also see some vegetation starting to grow south of the crater",null,"Frozen Tundra","Jungle",null);
        Location location4 = new Location(4,"Jungle","All you see is a densely packed jungle with lots of trees everywhere.  On a tree branch you spot a fruit that you can eat.  Hopefully it's not too poisonous. Doesn't seem like there's anywhere else to go but back","Crater",null,null,null);
        Location location5 = new Location(5,"Beach","Looks like a very nice deserted beach.  You could picture yourself vacationing here if you hadn't crash landed and trying to fight to survive.  You do see a fish that washed up on shore though, maybe you can eat it. You also spot a small creek emptying out into the beach to the south.",null,null,"Creek","Crash Site");

        Location location6 = new Location(6,"Creek","Just a small freshwater creek here going up the mountain to the west.  The mountain looks pretty inaccessible from here though.  Maybe you can go around it.","Beach",null,null,null);
        Location location7 = new Location(7,"Mountains","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location8 = new Location(8,"Alien Compound","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location9 = new Location(9,"Far East Crater","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location10 = new Location(10,"Abandoned Storage Facility","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location11 = new Location(11,"Lava Tubes","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location12 = new Location(12,"Abandoned Ship","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location13 = new Location(13,"South Crater","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location14 = new Location(14,"Landing Site","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location15 = new Location(15,"Alien Compound 2","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location16 = new Location(16,"Lava Tube","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location17 = new Location(17,"Rock Shrine","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location18 = new Location(18,"Dead Volcano","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location19 = new Location(19,"Fuel Outpost","You see your crashed ship with obvious signs of damage. You'll need to find tools to fix them. You take a look around and see paths to the North, East and South. Moving on this planet will drain your HP!","Frozen Tundra","Beach","Mountains",null);
        Location location20 = new Location(20,"Starting Items","This is used to hold starting items, but keep them from showing up at the Crash site.",null,null,null,null);

        Location[] locations = {location1,location2,location3,location4,location5,location6,location7,location8,location9,location10,location11,location12,location13,location14,location15,location16,location17,location18,location19,location20};

        return locations;
    }

    private Item[] createHiddenItems(){

        Item item1 = new Item(20, 15, "weapon", "pistol", "Crash Site", "A lowest bidder made pistol. Hopefully you won't have too much trouble", false);
        Item item2 = new Item(21, 40, "food", "water", "Far East Crater", "Water must be carried in order to survive. ", false);
        Item item3 = new Item(22, 10, "food", "creamcicle", "Abandoned Storage Facility", "Nice, space dessert. A little treat for you! ", false);
        Item item4 = new Item(23, -30, "food", "radiation", "Abandoned Ship", "Radiation detected from a nuclear powered engine. Suit up, otherwise you will die. ", false);
        Item item5 = new Item(24, 35, "weapon", "flamethrower", "South Crater", "Good find. This can be a great defense when fighting the aliens. ", false);
        Item item6 = new Item(25, 0, "other", "skeleton", "Rock Shrine", "Looks like remains from another", false);
        Item item7 = new Item(26, 90, "tool", "spacesuit", "Dead Volcano", "You found full spacesuit. This will give you maximum comfort and maximum protection. ", false);
        Item item8 = new Item(27, 0, "tool", "fuel", "Fuel Outpost", "Awesome. You have acquired more fuel to get back to earth. ", true);
        Item[] hiddenItems = {item1, item2, item3, item4, item5, item6, item7, item8};

        return hiddenItems;
    }

    private Item [] createItems(){
// int id, int hpValue, String type, String itemName, String location, String description, boolean keyItem
        Item item0 = new Item(31, 0, "tool", "shovel", "Frozen Tundra", "A shovel to dig your spacecraft out of the mess you made", true);
        Item item1 = new Item(32, 0, "tool", "toolset", "Crater", "How did your toolset fall out here? You need these to fix your craft.", true);
        Item item2 = new Item(33, 20, "food", "fruit", "Jungle", "Delicious exotic fruit", false);
        Item item3 = new Item(34, 15, "food", "fish", "Beach", "A flopping fish. You're not a huge fan of sushi though..", false);
        Item item4 = new Item(35, 20, "food", "water", "Creek", "Looks like clear alien water. No way it could hurt right?", false);
        Item item5 = new Item(36, 40, "food", "canned-food", "Lava Tubes", "Seem like you can spend a night or two here. ", false);
        Item item6 = new Item(37, 0, "tool", "empty-fuel-can", "Lava Tube", "Great! You can now have more backup fuel. ", false);
        Item item7 = new Item(38, 25, "food", "goop", "Alien Compound", "Some strange goop that aliens were eating. Might be ok to try ", false);
        Item item8 = new Item(39, 60, "food", "food-pack", "Alien Compound 2", "Some strange goop that aliens were eating. Might be ok to try ", false);
        Item[] items = {item0, item1, item2, item3, item4, item5, item6, item7, item8};
        return items;
    }
}
