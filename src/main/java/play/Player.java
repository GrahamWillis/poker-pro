package play;

/**
 * Created by graham on 12/07/16.
 */
public class Player {
    private String name;
    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
