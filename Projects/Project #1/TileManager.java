/*

Maksym Shkola
4/12/2023
Project #1 ; CS143 Spring

Description: The TileManager class manipulates the existing program that consists of Tile.java,
TileMain.java, and DrawingPanel. More specifically, TileManager is the logic
for a graphical program that allows the user to click on rectangular tiles
(which are registered in an ArrayList) and manipulates the rectangles based on user input.

*/


import java.util.*;
import java.util.List;
import java.awt.*;


public class TileManager {

    // ArrayList that stores rectangle tile information (objects)
    // with the following information: X/Y position and height/width information
    // The ArrayList gets manipulated in the code which causes the graphical
    // alterations visible when theres specific user input
    //private ArrayList<Tile> database = new ArrayList<Tile>();
    
    private List<Tile> database = new ArrayList<Tile>();

    public TileManager(){


    }

    // addTile method takes in a tile object from the other application files
    // and adds the object to the database arraylist
    public void addTile(Tile rect){
        database.add(rect);

    }

    // The drawAll method takes in Graphics g (graphics support code) and uses that to display
    // every tile in the database arraylist.
    public void drawAll(Graphics g){
        for(Tile tile : database){
            tile.draw(g);

        }

    }

    // Private support method getTile reduces redundancy from the following methods: raise,
    // lower, delete, and deleteAll. It passes you the x/y coordinates the user clicked.
    // If these coordinates touch any tiles, the tile object touched gets returned.

    private Tile getTile(int x, int y) {
        for (int i = database.size() - 1; i >= 0; i--) {
            Tile tile = database.get(i);
            // comparing the x/y position of the click to the x/y area covered by the tile
            // if on tile, return tile
            if (x >= tile.getX() && x <= tile.getX() + tile.getWidth() &&
                y >= tile.getY() && y <= tile.getY() + tile.getHeight()) {
                return tile;
            }
        }
        // if not on tile, return null
        return null;
    }


    //Called when the user left-clicks. It passes the x/y coordinates the user
    // clicked. If these coordinates touch any tiles (determined from the getTile method),
    // tile gets moved to the very top (end) of the arraylist (and graphically as well).

    public void raise(int x, int y) {
        // getTile call to determine if x/y coords land on tile
        Tile tile = getTile(x, y);
        if (tile != null) {
            database.remove(tile);
            database.add(tile);
        }
    }

    //Called when the user Shift left-clicks. It passes the x/y coordinates the user
    // clicked. If these coordinates touch any tiles (determined from the getTile method),
    // tile gets moved to the very bottom (beginning) of the arraylist
    // (and graphically as well).
    // This method takes in
    // x and y integers that support method getTile uses to determine
    // if the x and y touch any tiles, the tile object touched gets returned.

    public void lower(int x, int y){
        // getTile call to determine if x/y coords land on tile
        Tile tile = getTile(x, y);
        if (tile != null) {
            database.remove(tile);
            database.add(0, tile);
        }

    }

    // Delete method gets called when the user right-clicks.
    // If these coordinates touch any tiles, the topmost
    // tile gets deleted graphically and from the arraylist
    // This method takes in
    // x and y integers that support method getTile uses to determine
    // if the x and y touch any tiles, the tile object touched gets returned.

    public void delete(int x, int y){
        // getTile call to determine if x/y coords land on tile
        Tile tile = getTile(x, y);
        if (tile != null) {
            database.remove(tile);
        }
        
    }

    // deleteAll method is called when the user Shift-right-clicks.
    // If these coordinates touch any tiles, all tiles that the cursor
    // lands on (regardless if tiles on top or bottom of another) get
    // deleted graphically and from the ArrayList. This method takes in
    // x and y integers that support method getTile uses to determine
    // if the x and y touch any tiles, the tile object touched gets returned. 

    public void deleteAll(int x, int y){
        boolean removed = true;
        while (removed) {
            // getTile call to determine if x/y coords land on tile
            Tile tile = getTile(x, y);
            if (tile != null) {
                database.remove(tile);
            } else {
                removed = false;
            }
        }
    }

    // Called when the user types S. This method performs two actions:
    // (1) reordering the tiles in the list into a random
    // order, and (2) moving every tile on the screen to a new random x/y pixel position.
    // The random position is such that the square's top-left x/y position is non-negative
    // and also such that every pixel of the tile is within the passed width and height.
    // This method passes the width and height which is then used to determine the max
    // and min x/y coordinate limits. Collections.shuffle is used to shuffle the arraylist
    // graphically and arraylist wise

    public void shuffle(int width, int height){
        // shuffles the tile objects in the arraylist which in return shuffles
        // the tiles graphically
        Collections.shuffle(database);
        for(Tile tile : database){
            Random random = new Random();

            // System.out.println(tile.getX());
            // System.out.println(tile.getY());
            // System.out.println(tile.getWidth());
            // System.out.println(tile.getHeight());
            // System.out.println("\n\n");

            // setting the maximum limit for x and y coords
            int limitX = (width - tile.getWidth());
            int limitY = (height - tile.getHeight());

            // generation of a random x and random y in a range determined by the
            // limitX and limitY variables
            int randomNumberX = random.nextInt(limitX + 1 - 0) + 0;
            int randomNumberY = random.nextInt(limitY + 1 - 0) + 0;

            // sets x and y coords of the tile object 
            tile.setX(randomNumberX);
            tile.setY(randomNumberY);

        }


    }
    
}
