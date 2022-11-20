package Werdill;

import java.util.Random;

public class Checker {
    private String[] solution;

    private Random rand = new Random();

    public String getSolution() {
        return solution.toString();
    }

    public void setSolution(String newSolution) {
        solution = newSolution.toUpperCase().split("");
    }

    public Checker(String solution) {
        setSolution(solution);
    }

    public int[] check(String[] guess) {
        int[] ret = new int[5];
        for (int i = 0; i < 5; i++) {
            ret[i] = rand.nextInt(3);
        }
        return ret;
    }

}
