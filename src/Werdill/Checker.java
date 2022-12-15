package Werdill;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Checker {
    // not final so Checker may be reused in the case of a reset.
    private String[] solution;

    private final List<String> words;
    private final List<String> solutions;

    private static final String WORD_LIST_PATH = "res/wordList/words.txt";
    private static final String SOLUTIONS_PATH = "res/wordList/solutions.txt";

    private Random rand = new Random();

    public Checker() throws IOException {
        words = Files.lines(Path.of(WORD_LIST_PATH)).toList();
        solutions = Files.lines(Path.of(SOLUTIONS_PATH)).toList();

        chooseSolution();
    }

    public String[] getSolution() {
        return solution;
    }

    private void setSolution(String newSolution) {
        solution = newSolution.toUpperCase().split("");
    }

    /**
     * Changes Checker's correct solution to a new, random choice.
     */
    public void chooseSolution() {
        int index = rand.nextInt(solutions.size());
        setSolution(solutions.get(index));
        // FOR TESTING, CHANGE AND UNCOMMENT BELOW LINE:
        // setSolution("ember");
    }

    public List<String> getWords() {
        return words;
    }

    /**
     * Check the guess against the selected solution.
     * 
     * Legend; returned integers' meanings:
     *  0 = letter not in solution
     *  1 = letter in solution but wrong position
     *  2 = letter in correct position
     * @param guessCopy
     * @return 
     */
    public Integer[] check(String[] guess) {
        String[] guessCopy = guess.clone();
        // Ensures all letter are lowercase
        String guessString = "";
        for (String ltr : guessCopy) {
            guessString += ltr.toLowerCase();
        }
        // Reject non-words
        if (!words.contains(guessString)) {
            return null;
        }

        // Clone solution so letters are not inadvertently removed from primary instance variable
        ArrayList<String> solutionCopy = new ArrayList<>(Arrays.asList(solution));
        
        // Creates returned Integer Array
        Integer[] ret = new Integer[]{0, 0, 0, 0, 0};
        // Checks ALL letters for *exact,* correct position. 
        // Removes letter from guess Array if it is in the right spot (avoid false yellow boxes)
        for (int i = 0; i < 5; i++) {
            if (guessCopy[i].equals(solutionCopy.get(i))) {
                ret[i] = 2;
                guessCopy[i] = "";
                solutionCopy.set(i, "") ;
            }
        }
        // Checks if solution contains each letter
        // Again, removes letters once checked to avoid false positives.
        for (int i = 0; i < 5; i++) {
            if (solutionCopy.contains(guessCopy[i]) && ret[i] == 0) {
                ret[i] = 1;
                solutionCopy.remove(solutionCopy.indexOf(guessCopy[i]));
                guessCopy[i] = "";
            }
        }
        return ret;
    }
}
