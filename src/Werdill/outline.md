# Werdill
Subtitle: "Compare to active ingredient in Wordle(TM)"

<!-------------------------------------------------------->

## Main Class

### Instance Variables
- priv `Grid` grid
- priv `Checker` checker
- private `List<Str>` words {get}

### Instance Methods
1. pub `void` main() (Main method)
    1. instantiate grid
    2. `reset` the grid
    3. is four (joke)
    3. chooseSolution()
    3. instantiate checker -> gives it the solution
    17. canvas.onKeyPressblahblah(procInFGrid)
    3. ``

2. priv `void` processInputFromGrid() *( \* the callback for the return key)*
    1. call grid.`getGuess()`
    219. 
    1. Checks to see if len = 5
    2. Check if word in list
    4. checker.check()

3. priv `Str` chooseSolution():
    1. returns word for word list


<!-------------------------------------------------------->

## WerdillUI (`GraphicsGroup`)

### Constants
- private static final `Color` BASE_COLOR = 
- private static final `Color` NOT_IN_COLOR =
- private static final `Color` WRONG_POSITION_COLOR =
- private static final `Color` RIGHT_POSITION_COLOR =
- private static final `Color` BACKGROUND_COLOR =
- * for each rectangle color: private static final `Color` TEXT_COLOR =
- private static final `Integer` RECTANGLE_SIDE_LENGTH =
- private static final `Integer` RECTANGLE_PADDING =

- private `int` guessNumber = 0;

- private final `TextField` guessField

### Instance Variables
- private `List<List<Rectangle>>` gridRectangles *( \* each row has its own list of rectangles to keep them organized.)*

### Instance Methods
1. constructor()

2. pub `void` reset() (reset the grid)
    1. Check if there are blocks already there (if so, `resetRectangleColor()`)
        - else:
            1. 2 c-fors; up to 6 for first, 5 for second
            2. Create each rectangle object
                - Store in list
                - Add to group
                - Add {!} `return callback` to last boxes each row
    2. 

3. priv `void` resetRectangleColor() (resets color to BASE_COLOR)
    1. Loop thru gridRectangles: 
        1. Set fill color to BASE_COLOR

3. priv `string` 

4. private `void` showCheckedGuess()
    1. guessNumber++
    2. get gridRectangles.get(guessNumber)

<!-- 3. pub `void` -->


<!-------------------------------------------------------->

## Checker
**LOTS OF UNIT TESTS** "It's a tricky algorithm" -Paul

### Instance Variable
- private `List<Str>` solution

### Instance Methods
1. constructor(wordList)
    1. chooses a solution randomly
    2. solution -> list

2. public `List<Integer>` checkWord(guess: `Str`) (checks if word is solution [0 = not in word; 1 = in word but wrong spot; 2 = in right spot])
    1. guess -> ArrayList
    7. solution -> copy to ArrayList
    1. returnList = List.of(0, 0, 0, 0, 0)
    2. c-style for < 5
        1. Checking if letter is the same
        2. if `checkIfInRightPosition`
            1. put 2 in the letters index
        3. if `checkIfInWord`
            1. put 1 in the letters index in return
        4. else:
            1. put 0 in ltr index
    19. return returnList

3. private `bool` checkIfInWord(letter, solnList)
    1. if letter in solnList:
        1. remove from solnList
        1. return true
    2. else: return false

3. private `bool` checkIfInRightPosition(letter, indexInGuess, solnList)
    1. get the letter at index in solution
    2. if that letter is = to the guess
        1. return rtue
    3. return false
