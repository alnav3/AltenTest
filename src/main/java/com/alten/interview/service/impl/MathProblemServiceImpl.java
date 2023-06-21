package com.alten.interview.service.impl;

import org.springframework.stereotype.Service;

import com.alten.interview.data.MathProblemResponse;
import com.alten.interview.service.MathProblemService;


@Service
public class MathProblemServiceImpl implements MathProblemService {
    public MathProblemResponse solveProblem(int x, int y, int n) {
        int k = ((n / x) * x)+y;
        if (k > n) {
            k -= x;
        }
        return new MathProblemResponse(k);
    }
}