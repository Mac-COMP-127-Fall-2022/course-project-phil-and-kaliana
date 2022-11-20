package Werdill;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    }

    public List<String> getWords() {
        return words;
    }

    public Integer[] check(String[] guess) {
        //TODO: Check is in word list

        Integer[] ret = new Integer[5];
        // <testVersion>
        for (int i = 0; i < 5; i++) {
            ret[i] = rand.nextInt(3);
        }
        // </testVersion>
        return ret;
    }

}
