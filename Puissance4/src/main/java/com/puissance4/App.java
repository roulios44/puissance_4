package com.puissance4;
/**
 * This app class is the class to lauch to start the programm
 */
public class App {
    /**
     * 
     * @param args juste arg to lauch the app manualy (with java extensions pack on VS code)
     * main of the App class
     */
    public static void main(String[] args){
        Game game = new Game();
        game.menu();
    }
}
