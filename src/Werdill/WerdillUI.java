package Werdill;

import edu.macalester.graphics.GraphicsGroup;

import java.awt.Color;
import edu.macalester.graphics.events.Key;

public class WerdillUI extends GraphicsGroup {
    // private static final Color BASE_COLOR = new Color();
    
    private static final Color NOT_IN_COLOR = new Color(0xafafaf);
    private static final Color WRONG_POSITION_COLOR = new Color(0xffc900);
    private static final Color RIGHT_POSITION_COLOR = new Color(0x049c00);
    private static final Color BACKGROUND_COLOR = new Color(0xffffff);

    private static final Color WRONG_RIGHT_TEXT_COLOR = new Color(0xffffff);
    private static final Color NOT_IN_AND_BASE_TEXT_COLOR = new Color(0xffffff);

    private int guessNumber;


    public WerdillUI(WerdillGame werdillGame) {
        getCanvas().onKeyDown((event) -> {
            if (event.getKey() == Key.RETURN_OR_ENTER) {

            }
        });
    }

    public void reset() {
        guessNumber = 0;
    }

    private void setCurrentRowTo(int[] checkedCharacters) {
        // 0 = not in; 1 = in wrong position; 2 = in correct position

        
    }

}
