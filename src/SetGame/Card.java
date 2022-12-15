package SetGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Path;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

/**
 * Class representing a Set Game's Card values, traits and UI.
 * 
 * @author Phil Reitz-Jones
 * @author Kaliana Andriamananjara
 */

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

    // List of lighter versions of colors (instead of lined) for middle shading
    private final List<Color> halfColors = List.of(
        new Color(255, 127, 127),
        new Color(200, 255, 200),
        new Color(127, 127, 255)
    );


    /**
     * @param color     Color value of a card (red, green, blue)
     * @param num       Number value of a card (1, 2, 3 of each shape)
     * @param shape     Shape of a card (triangle, diamond, rectangle)
     * @param fill      Shading of a card (empty, light or full/dark)
     */
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

    /**
     * @param trait     Takes in what kind of trait the caller wants (color, number, shape or shading)
     * @return          The value of the trait (which color, number, shape or shading)
     */
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


    /**
     * Place shapes within the card. Starting at x of card padding and a y that depends on amount of items in the card.
     * @param shapes    A list of the shapes which are going to be on the card
     */
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

    /**
     * Returns the color of fill based on its color AND fill status (aka full, half full or empty)
     * @param colorInt      Which color it is between red, green, blue
     * @param fillInt       Whether it is empty, light or dark
     * @return              The color that the shape will be filled
     */
    private Color getColorFromInt(int colorInt, int fillInt) {
        if (fillInt == 1) {
            return halfColors.get(colorInt);
        } else if (fillInt == 2) {
            return colors.get(colorInt);
        } else {
            return Color.WHITE;
        }
    }

    // below createShapes
    /**
     * 
     * @param color     The color of the shape
     * @param fill      The color it needs to be filled
     * @return          The Triangle with the correct outline and fill
     */
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

    /**
     * 
     * @param color     The color of the shape
     * @param fill      The color it needs to be filled
     * @return          The Diamond with the correct outline and fill
     */
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

        /**
     * 
     * @param color     The color of the shape
     * @param fill      The color it needs to be filled
     * @return          The Rectangle with the correct outline and fill
     */
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
