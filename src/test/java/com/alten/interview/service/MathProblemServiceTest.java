package com.alten.interview.service;

import org.junit.jupiter.api.Test;

import com.alten.interview.service.impl.MathProblemServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathProblemServiceTest {

    private final MathProblemService mathProblemService = new MathProblemServiceImpl();

    @Test
    public void testSolveProblem1() {
        assertEquals(12339, mathProblemService.solveProblem(7, 5, 12345).getK());
    }

    @Test
    public void testSolveProblem2() {
        assertEquals(0, mathProblemService.solveProblem(5, 0, 4).getK());
    }

    @Test
    public void testSolveProblem3() {
        assertEquals(15, mathProblemService.solveProblem(10, 5, 15).getK());
    }

    @Test
    public void testSolveProblem4() {
        assertEquals(54306, mathProblemService.solveProblem(17, 8, 54321).getK());
    }

    @Test
    public void testSolveProblem5() {
        assertEquals(999999995, mathProblemService.solveProblem(499999993, 9, 1000000000).getK());
    }

    @Test
    public void testSolveProblem6() {
        assertEquals(185, mathProblemService.solveProblem(10, 5, 187).getK());
    }

    @Test
    public void testSolveProblem7() {
        assertEquals(999999998, mathProblemService.solveProblem(2, 0, 999999999).getK());
    }
}
