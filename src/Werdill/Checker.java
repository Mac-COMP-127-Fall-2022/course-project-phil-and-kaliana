package Werdill;

import java.util.List;

public class Checker {
    private String[] solution;

    public String getSolution() {
        return solution.toString();
    }

    public void setSolution(String newSolution) {
        solution = newSolution.split("");
    }

    public Checker(String solution) {
        setSolution(solution);
    }

}
