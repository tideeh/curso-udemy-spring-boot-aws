package com.example.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.util.*;
import com.example.api.util.exceptions.UnsupportedMathOperationException;

@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath math = new SimpleMath();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
                    @PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne) || !NumberConverter.isNumber(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        return math.sum(NumberConverter.stringToDouble(numberOne), NumberConverter.stringToDouble(numberTwo));
    }
    
    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(
                    @PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne) || !NumberConverter.isNumber(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        return math.sub(NumberConverter.stringToDouble(numberOne), NumberConverter.stringToDouble(numberTwo));
    }

    @GetMapping("/mult/{numberOne}/{numberTwo}")
    public Double mult(
                    @PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne) || !NumberConverter.isNumber(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        return math.mult(NumberConverter.stringToDouble(numberOne), NumberConverter.stringToDouble(numberTwo));
    }

    @GetMapping("/div/{numberOne}/{numberTwo}")
    public Double div(
                    @PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne) || !NumberConverter.isNumber(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        if(NumberConverter.stringToDouble(numberTwo) == 0) {
            throw new UnsupportedMathOperationException("Division by zero");
        }
        
        return math.div(NumberConverter.stringToDouble(numberOne), NumberConverter.stringToDouble(numberTwo));
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
                    @PathVariable(value = "numberOne") String numberOne,
                    @PathVariable(value = "numberTwo") String numberTwo)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne) || !NumberConverter.isNumber(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        return math.mean(NumberConverter.stringToDouble(numberOne), NumberConverter.stringToDouble(numberTwo));
    }

    @GetMapping("/squareRoot/{numberOne}")
    public Double squareRoot(
                    @PathVariable(value = "numberOne") String numberOne)
                    throws Exception {

        if(!NumberConverter.isNumber(numberOne)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        
        return math.squareRoot(NumberConverter.stringToDouble(numberOne));
    }
    
}
