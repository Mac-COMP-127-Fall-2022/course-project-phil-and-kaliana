package Werdill;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Checker {
    private String[] solution;

    private final List<String> words;
    private final List<String> solutions;

    private static final String WORD_LIST_PATH = "res/wordList/words.txt";
    private static final String SOLUTIONS_PATH = "res/wordList/solutions.txt";

    private Random rand = new Random();

    public String getSolution() {
        return solution.toString();
    }

    public void setSolution(String newSolution) { // NOTE: switch to private if not unit testing
        // System.out.println(newSolution);
        solution = newSolution.toUpperCase().split("");
    }

    public Checker() throws IOException {
        words = Files.lines(Path.of(WORD_LIST_PATH)).toList();
        solutions = Files.lines(Path.of(SOLUTIONS_PATH)).toList();

        chooseSolution();
    }

    private void chooseSolution() {
        int index = rand.nextInt(solutions.size());
        setSolution(solutions.get(index));
        System.out.println(Arrays.toString(solution));
    }

    public List<String> getWords() {
        return words;
    }

    public Integer[] check(String[] guess) {
        //TODO: Check is in word list
        // if (!words.contains(guess.toString().toLowerCase())) {
        //     return null;
        // }

        ArrayList<String> solutionCopy = new ArrayList<>(Arrays.asList(solution));

        Integer[] ret = new Integer[5];
        // <testVersion>
        for (int i = 0; i < 5; i++) {
            if (guess[i].equals(solutionCopy.get(i))) {
                ret[i] = 2;
                // guess[i] = "";
                solutionCopy.set(i, "") ;//= "";
                continue;
            } 
            if (solutionCopy.contains(guess[i])) {
                ret[i] = 1;
                solutionCopy.set(i, "") ;//= "";
                continue;
            }
            ret[i] = 0;
            // guess[i] = "";
        }
        // </testVersion>
        return ret;
    }
}
