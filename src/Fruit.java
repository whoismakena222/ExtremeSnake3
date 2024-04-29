import java.awt.*;

public class Fruit {
        public String name;
        public int xpos;
        public int ypos;

        public int points;

        public int width = 75;
        public int height = 75;


        public Rectangle rec;

        boolean isAlive = false;

    public Fruit (String paramName, int paramPoints, int paramXpos, int paramYpos) {
        name = paramName;
        points = paramPoints;
        xpos = paramXpos;
        ypos = paramYpos;
        rec = new Rectangle(xpos,ypos,width, height);
    }
    public void spawnn(){
        xpos = (int)(Math.random()* 925);
        ypos = (int)(Math.random()* 725);

        rec = new Rectangle(xpos,ypos,width, height);
    }

}
