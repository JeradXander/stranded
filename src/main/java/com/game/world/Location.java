package com.game.world;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
// Instance Fields for locations on planet
    private int locationId;
    private String name;
    private String description;
    //Will need field for items in each location... e.g. private Item gun = new Item(gun);

    // Links to other possible paths per location
    private String north;
    private String east;
    private String south;
    private String west;

    //Default constructor for Jackson JSON objects
    public Location() {
        super();
    }
    //Constructor for building location
    public Location(int Id, String name, String description, String north, String east, String south, String west) {
        setLocationId(Id);
        setName(name);
        setDescription(description);
        setNorth(north);
        setEast(east);
        setSouth(south);
        setWest(west);

    }

    //Methods

    public StringBuilder getDirections() {
        StringBuilder directions = new StringBuilder();
        directions.append("Paths Available: \n");
        if (north != null) {
            directions.append("north").append(" ");
        }
        if (east != null) {
            directions.append("east").append(" ");
        }
        if (south != null) {
            directions.append("south").append(" ");
        }
        if (west != null) {
            directions.append("west").append(" ");
        }
        return directions;
    }

    //Getters & Setters
    public int getLocationId() {
        return locationId;
    }

    private void setLocationId(int locationId) {
        if (locationId>0) {
            this.locationId = locationId;
        }  else {
            System.out.println("Location ID must be greater than 0");
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getNorth() {
        return north;
    }

    private void setNorth(String north) {
        this.north = north;
    }

    public String getEast() {
        return east;
    }

    private void setEast(String east) {
        this.east = east;
    }

    public String getSouth() {
        return south;
    }

    private void setSouth(String south) {
        this.south = south;
    }

    public String getWest() {
        return west;
    }

    private void setWest(String west) {
        this.west = west;
    }



    @Override
    public String toString() {
        return "{" +
                "locationId=" + locationId +
                ", name='" + name + '\n' +
                ", description='" + description + '\n' +
                ", north='" + north + '\n' +
                ", east='" + east + '\n' +
                ", south='" + south + '\n' +
                ", west='" + west + '\n' +
                '}';
    }
}
