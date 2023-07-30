package com.extia.kata.controller;

import com.extia.kata.model.Operand;
import com.extia.kata.model.Stack;
import com.extia.kata.repository.IOperandRepository;
import com.extia.kata.repository.IStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rpn/op")
public class OperandController {

    @Autowired
    private IOperandRepository operandRepository;

    @Autowired
    private IStackRepository stackRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Operand>> getOperators() {

        List<Operand> operandList = operandRepository.findAll();

        return ResponseEntity.ok(operandList);
    }

    @RequestMapping(value = "/{operand}/stack/{stackId}", method = RequestMethod.POST)
    public ResponseEntity applyOperandToStack(
            @PathVariable String operand,
            @PathVariable String stackId
    ) {

        Optional<Stack> oStack = stackRepository.findById(stackId);

        if (oStack.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("This stack does not exist");
        }
        Stack stack = oStack.get();
        List<String> values = stack.getValues();

        if (values.size() < 2) {
            return ResponseEntity.badRequest()
                    .body("The stack contains less than 2 values");
        }

        // Stream<String> operands = Stream.of("PLUS", "MINUS", "DIVIDED_BY", "TIMES");
        List<Operand> operands = operandRepository.findAll();
        boolean operandExists = operands.stream().anyMatch(s -> s.getOperand().equals(operand));

        if (!operandExists) {
            return ResponseEntity.badRequest()
                    .body("The requested operand does not exist");
        }


        if (values.get(values.size() - 2).equals("0")) {
            return ResponseEntity.badRequest()
                    .body("The second-to-last value of your stack is Zero, which means you are trying to divide by 0");
        }

        // Computing
        stack.computeValues(operand);
        // Recording
        stackRepository.save(stack);

        return ResponseEntity.ok().body("Computation successful");
    }
}
