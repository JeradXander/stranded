package com.game.enemies;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.conditions.Combat;
import com.game.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Alien {

    //Instance Fields
    private int hp;
    private int attackDamage; //Damage strength per enemy 0-10 recommended..
    private int defense; //incoming Attack int divided by this amount
    private String type;
    private String location;
    private boolean isAlive = true;
    Player player ;

    //Constants
    private final int MIN_HP = 0;


    //Jackson default constructor
    public Alien() {
        super();
    }
    //Alien Constructor
    public Alien(int HP, int attackDamage, int defense, String type, String location, boolean alive, Player _player) {
        player = _player;
        setHp(HP);
        setAttackDamage(attackDamage);
        setDefense(defense);
        setType(type);
        setLocation(location);
        setAlive(alive);
    }

    //Fight methods

    public void Attack() {
        // Gives 1/chance # of a super attack.
        // Currently 8.3%
        Random rand = new Random();
        int chance = 12;

        if (getType().equals("boss")) { //Bosses get higher superAttack chance!
            chance = 10;
        }

        int randAttack = rand.nextInt(chance);

        if (randAttack == chance-1) {
            player.takeDamage(superAttack());
        }
        else {
            player.takeDamage(normalAttack());
        }
    }

    private int normalAttack() {
        Random rand = new Random();
        int range = rand.nextInt(3)+1;

        int damage = attackDamage * range;
        Combat.setEnemyResult(getType() + " attacks for " + damage);
       return damage;
    }

    private int superAttack() {
        Random rand = new Random();
        int range = rand.nextInt(5)+1;

        int damage = attackDamage * range;
        Combat.setEnemyResult(getType() + " super attacks for " + damage);
        return damage;
    }

    public void takeDamage(int AttackStr) {


        int totalDamage = AttackStr/defense;
        if (totalDamage == 0) { //Give at least 1 damage per turn with fists...
            totalDamage += 1;
        }

        setHp(-totalDamage);
    }

    //Getters & Setters
    public int getHp() {
        return hp;
    }

    private void setHp(int HP) {
        // If HP value is negative and takes HP below 0, just set to MIN_HP
        int newHP = this.hp + HP;
        if (MIN_HP >= newHP) {
            this.hp = MIN_HP;
            //setAlive(); //Will change alive to false if at or below min
        }
        this.hp += HP;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void checkAlive() {
        if (this.hp > MIN_HP) {
            this.isAlive = true;
        } else {
            this.isAlive = false;
        }
    }

    @Override
    public String toString() {
        return "Alien{" +
                "HP=" + hp +
                ", attackDamage=" + attackDamage +
                ", defense=" + defense +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", isAlive=" + isAlive +
                ", MIN_HP=" + MIN_HP +
                '}';
    }
}