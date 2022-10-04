public class Game {
    protected Grid grid;
    Game(int height, int width) {
        Grid grid = new Grid(height, width);
        System.out.println(grid.grid.get(0).get(1));
    }
}
