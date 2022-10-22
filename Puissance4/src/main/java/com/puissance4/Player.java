package com.puissance4;

import java.io.Serializable;
/**
 * Class of the player his info 
 */
public class Player implements Serializable{
    /**
     * Name of the player
     */
    public String name;
    /**
     * Symbole of the player
     */
    public String symbole;
    /**
     * bool to check if the player have win or not 
     */
    public boolean haveWin = false;
    /**
     * constructor of the player class
     * @param name name of the player
     * @param symbole symbole of the player
     */
    Player(String name, String symbole){
        this.name = name;
        this.symbole = symbole;
    }
}