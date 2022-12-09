package SetGame;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Card extends GraphicsGroup {
    private int color;
    private int num;
    private int shape;
    private int fill;

    private final Rectangle cardBase;

    public static final int CARD_PADDING = 5;

    public static final int CARD_WIDTH = 30;
    public static final int CARD_HEIGHT = 50;
    
    public Card(int color, int num, int shape, int fill) {
        this.color = color;
        this.num = num;
        this.shape = shape;
        this.fill = fill;

        cardBase = new Rectangle(0, 0, CARD_WIDTH, CARD_HEIGHT);
        add(cardBase);
    }

    public Integer getTrait(int trait){
        switch (trait) {
            case 0:
                return color;

            case 1:
                return num;
            
            case 2:
                return shape;
            
            case 3:
                return fill;
        
            default:
                return null;
        }
    }  
}
