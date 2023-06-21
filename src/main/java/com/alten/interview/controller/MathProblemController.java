package com.alten.interview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alten.interview.data.MathProblem;
import com.alten.interview.data.MathProblemResponse;
import com.alten.interview.exception.BadRequestException;
import com.alten.interview.service.MathProblemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success|OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestException.class), examples = @ExampleObject(value = "{\"message\":\"Bad Request Error\"}"))),
        @ApiResponse(responseCode = "500", description = "internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class), examples = @ExampleObject(value = "{\"message\":\"Internal Server Error\"}")))
    })
@RestController
@RequestMapping("/interview")
public class MathProblemController {
	
    private final MathProblemService mathProblemService;

    public MathProblemController(MathProblemService mathProblemService) {
        this.mathProblemService = mathProblemService;
    }

    /**
     * Solves a math problem using a POST request.
     * 
     * @param problem The math problem to solve, represented as a MathProblem object.
     * @return A MathProblemResponse representing the solution.
     * @throws BadRequestException if any of the parameters are incorrect or fail validation.
     */
    @Operation(summary = "Solves a math problem using a POST request")
    @PostMapping("/mathProblem")
    public @ResponseBody MathProblemResponse solveProblem(@RequestBody MathProblem problem) {
    	checkException(problem.getX(),problem.getY(),problem.getN());
        return mathProblemService.solveProblem(problem.getX(), problem.getY(), problem.getN());
    }

    /**
     * Solves a math problem using a GET request.
     * 
     * @param x First parameter of the math problem.
     * @param y Second parameter of the math problem.
     * @param n Third parameter of the math problem.
     * @return A MathProblemResponse representing the solution.
     * @throws BadRequestException if any of the parameters are incorrect or fail validation.
     */
    @Operation(summary = "Solves a math problem using a GET request")
    @GetMapping("/mathProblem")
    public @ResponseBody MathProblemResponse solveProblemGet(@RequestParam int x, @RequestParam int y, @RequestParam int n) {
    	checkException(x,y,n);
        return mathProblemService.solveProblem(x, y, n);
    }
    
    public void checkException(Integer x,Integer y,Integer n) {
    	if(x == null || y == null || n == null) {
    		throw new BadRequestException("Parameters are incorrect");
    	}
    	if(x == 0) {
    		throw new BadRequestException("The x parameter cannot be zero");
    	}
    	if(y >= x) {
    		throw new BadRequestException("The x parameter cannot be greater than y");
    	}
    	if(y >= n) {
    		throw new BadRequestException("The y parameter cannot be greater than n");
    	}
    }
}
