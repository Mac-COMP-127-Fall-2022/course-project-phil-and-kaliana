package SetGame;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int color;
    private int num;
    private int shape;
    private int fill;

    public Card(int color, int num, int shape, int fill) {
        this.color = color;
        this.num = num;
        this.shape = shape;
        this.fill = fill;
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
