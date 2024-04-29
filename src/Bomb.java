import java.awt.*;
public class Bomb {
    public String name;
    public int xpos;
    public int ypos;

    public int width = 75;
    public int height = 75;

    public Rectangle rec;

    boolean isAlive = false;

    public Bomb (String paramName, int paramXpos, int paramYpos) {
        name = paramName;
        xpos = paramXpos;
        ypos = paramYpos;
        rec = new Rectangle(xpos,ypos,width, height);
    }
    public void spawn(){
        xpos = (int)(Math.random()* 925);
        ypos = (int)(Math.random()* 725);

        rec = new Rectangle(xpos,ypos,width, height);
    }
}
