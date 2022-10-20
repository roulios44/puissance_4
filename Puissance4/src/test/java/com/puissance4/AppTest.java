package com.puissance4;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import javax.sound.sampled.Line;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void gridIscorrect(){
        try{
            Grid g1 = new Grid(8, 6, 4);
            Grid g2 = new Grid(12, 10, 4);
        }
        catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void winByLine(){
        Class<Grid> gridClass = Grid.class;
        try{
            Method checkLine = gridClass.getDeclaredMethod("checkLine", boolean.class);
            checkLine.setAccessible(true);
        }
        catch(Exception e){

        }
    }

    @Test
    public void winByColumn(){
        Class<Grid> gridClass = Grid.class;
        try{
            Method checkColumn = gridClass.getDeclaredMethod("checkColumn", boolean.class);
            checkColumn.setAccessible(true);
        }
        catch(Exception e){

        }
    }

    @Test
    public void winByDiagonal(){
        Class<Grid> gridClass = Grid.class;
        try{
            Method checkLeftToRight = gridClass.getDeclaredMethod("checkLeftToRight", boolean.class);
            Method checkRightToLeft = gridClass.getDeclaredMethod("checkRightToLeft", boolean.class);
            checkLeftToRight.setAccessible(true);
            checkRightToLeft.setAccessible(true);
        }
        catch(Exception e){

        }
    }
    @Test
    public void equality(){
        Class<Game> gameClass = Game.class;
        try {
            Method winCondition = gameClass.getDeclaredMethod("winCondition", boolean.class);
            winCondition.setAccessible(false);
        }
        catch(Exception e){

        }
    }
}