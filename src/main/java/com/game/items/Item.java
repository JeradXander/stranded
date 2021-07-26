package com.game.items;

public class Item {
    private int id;
    private int hpValue;
    private String type;
    private String itemName;
    private String location;
    private String description;
    private boolean keyItem = false;


    //Default constructor for Jackson JSON objects
    public Item () {
        super();
    }

    public Item(int id, int hpValue, String type, String itemName, String location, String description, boolean keyItem) {

        setId(id);
        setHpValue(hpValue);
        setType(type);
        setItemName(itemName);
        setLocation(location);
        setDescription(description);
        setKeyItem(keyItem);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0 ) {
            this.id = id;
        } else {
            System.out.println("ID must be positive");
            this.id = 0;
        }
    }

    public int getHpValue() {
        return hpValue;
    }

    public void setHpValue(int hpValue) {
        this.hpValue = hpValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.equals("food") || type.equals("weapon") || type.equals("other") || type.equals("tool")) {
            this.type = type;
        }
        else {
            this.type = "other";
        }
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isKeyItem() {
        return keyItem;
    }

    public void setKeyItem(boolean keyItem) {
        this.keyItem = keyItem;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + id +
                ", hpValue=" + hpValue +
                ", type='" + type + '\'' +
                ", itemName='" + itemName + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", keyItem=" + keyItem +
                '}';
    }
}

