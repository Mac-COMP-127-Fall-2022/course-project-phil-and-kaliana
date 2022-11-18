# SET

## Main Class

### Instance Variables

- priv `board` setUI
- priv `serChecker` setchecker
- cards `cards` cards

## Instance Methods
1. public `void` main()
    1. create board ui
    2. createDeck

2. checkBoard()
    1. if !Board.isFull()
        1. if deck.length >= 3
            1. addCards()
    2. else
        1. --



## setUI
### Instance Variables
- `List<Cards>` board
### Instance Methods
1. public `void` reset()
    1. Clear board

2. isFull()
    1. return (boardLength == 12)

3. addCards()
    1. cstyle loop 3
        1. add deck at index 0 to board
        2. remove index 0 from deck



## Card

### Instance Variables
- priv `List<Str>` cardColor
- priv `List<Int>` cardNum
- priv `List<Str>` cardShape
- priv `List<Str>` cardFill

- Deck = createDeck()

### Instance Methods
1. private `List` CreateDeck
    1. cardDeck = []
    2. for c in cardColor
        1. for n in cardNum
            1. for s in cardShape
                1. for f in cardfill
                    1. add card with attributes [c, n, s, f]
    3. return cardDeck





## SetChecker
