package com.puissance4;

import java.io.Serializable;

public class Player implements Serializable{
    public String name;
    public String symbole;
    public boolean haveWin = false;
    Player(String name, String symbole){
        this.name = name;
        this.symbole = symbole;
    }
}