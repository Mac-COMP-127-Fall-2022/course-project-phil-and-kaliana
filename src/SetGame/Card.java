package SetGame;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Path;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class Card extends GraphicsGroup {
    private int color;
    private int num;
    private int shape;
    private int fill;

    private final Rectangle cardBase;

    public static final int CARD_PADDING = 20;

    public static final int CARD_WIDTH = 120;
    public static final int CARD_HEIGHT = 200;

    public static final int SHAPE_WIDTH = 80;
    public static final int SHAPE_HEIGHT = 40;

    public static final int SHAPE_AND_CARD_STROKE_WIDTH = 2;

    // List of base colors 
    private final List<Color> colors = List.of(
        Color.RED,
        new Color(0, 200, 0),
        Color.BLUE
    );

    // List of lighter versions of colors (instead of lined)
    private final List<Color> halfColors = List.of(
        new Color(255, 127, 127),
        new Color(200, 255, 200),
        new Color(127, 127, 255)
    );
    
    public Card(int color, int num, int shape, int fill) {
        this.color = color;
        this.num = num;
        this.shape = shape;
        this.fill = fill;

        cardBase = new Rectangle(0, 0, CARD_WIDTH, CARD_HEIGHT);
        cardBase.setStrokeWidth(SHAPE_AND_CARD_STROKE_WIDTH);
        add(cardBase);

        ArrayList<GraphicsObject> shapes = new ArrayList<>();
        // trait 1 is number of shapes in a card
        for (int i = 0; i <= getTrait(1); i++) {
            // trait 2 is shape
            switch (getTrait(2)) {
                // trait 0 is color, trait 3 is fill/shading. Trait 0/default is Triangle, 1 is Diamond, and 2 is Rectangle
                case 0:
                    shapes.add(createTriangle(getTrait(0), getTrait(3)));
                    break;
                case 1:
                    shapes.add(createDiamond(getTrait(0), getTrait(3)));
                    break;
                case 2:
                    shapes.add(createRectangle(getTrait(0), getTrait(3)));
                    break;
                default:
                    shapes.add(createTriangle(getTrait(0), getTrait(3)));
                    break;
            }
        }
        placeShapes(shapes);
    }

    // gets trait. 0 is color, 1 is number, 2 is shape, 3 is fill/shading
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

    // Place shapes within the card. Starting at x of card padding
    // and a y that depends on amount of items in the card.
    private void placeShapes(ArrayList<GraphicsObject> shapes) {
        int x = CARD_PADDING;
        double y;

        switch (shapes.size()) {
            case 1:
                GraphicsObject shape = shapes.get(0);
                add(shape);
                shape.setPosition(
                    x, 
                    getCenter().getY() - SHAPE_HEIGHT/2.0
                );
                break;
            case 2:
                y = SHAPE_HEIGHT + CARD_PADDING/2.0;
                for (GraphicsObject item : shapes) {
                    add(item);
                    item.setPosition(
                        x, 
                        y
                    );
                    y += SHAPE_HEIGHT + CARD_PADDING;
                }
                break;
            case 3:
                y = CARD_PADDING;
                for (GraphicsObject item : shapes) {
                    add(item);
                    item.setPosition(
                        x,
                        y
                    );
                    y += SHAPE_HEIGHT + CARD_PADDING;
                }
                break;
            default:
                break;
        }
    }

    // Returns the color of fill based on its color AND fill status (aka full, half full or empty)
    private Color getColorFromInt(int colorInt, int fillInt) {
        if (fillInt == 1) {
            return halfColors.get(colorInt);
        } else if (fillInt == 2) {
            return colors.get(colorInt);
        } else {
            return Color.WHITE;
        }
    }

    // createShapes
    private Path createTriangle(int color, int fill){
        // crying
        Path triangle = Path.makeTriangle(
            0, SHAPE_HEIGHT,
            SHAPE_WIDTH/2.0, 0,
            SHAPE_WIDTH, SHAPE_HEIGHT
        );
        
        triangle.setFillColor(getColorFromInt(color, fill));
        triangle.setStrokeColor(colors.get(color));
        triangle.setStrokeWidth(SHAPE_AND_CARD_STROKE_WIDTH);

        return triangle;
    }

    private Path createDiamond(int color, int fill){
        // more crying
        Path diamond = new Path(List.of(
            new Point(0, SHAPE_HEIGHT/2.0),
            new Point(SHAPE_WIDTH/2.0, SHAPE_HEIGHT),
            new Point(SHAPE_WIDTH, SHAPE_HEIGHT/2.0),
            new Point(SHAPE_WIDTH/2.0, 0)
        ));
        
        diamond.setFillColor(getColorFromInt(color, fill));
        diamond.setStrokeColor(colors.get(color));
        diamond.setStrokeWidth(SHAPE_AND_CARD_STROKE_WIDTH);

        return diamond;
    }

    private Path createRectangle(int color, int fill){
        // even more crying
        Path rect = new Path(
            new Point(0, 0),
            new Point(SHAPE_WIDTH, 0),
            new Point(SHAPE_WIDTH, SHAPE_HEIGHT),
            new Point(0, SHAPE_HEIGHT)
        );
        
        rect.setFillColor(getColorFromInt(color, fill));
        rect.setStrokeColor(colors.get(color));
        rect.setStrokeWidth(SHAPE_AND_CARD_STROKE_WIDTH);

        return rect;
    }

    public Rectangle getCardBase() {
        return cardBase;
    }
}
