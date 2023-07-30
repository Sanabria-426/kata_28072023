package com.extia.kata.controller;

import com.extia.kata.controller.exception.StackNotFound;
import com.extia.kata.model.Stack;
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
@RequestMapping(value = "/rpn/stack")
public class StackController {

    @Autowired
    private IStackRepository stackRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Stack> getStacks() {
        return stackRepository.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createStack() {

        Stack s = new Stack();
        stackRepository.insert(s);

        return s.getId();
    }

    @RequestMapping(value = "/{stackId}", method = RequestMethod.GET)
    public ResponseEntity getStack(@PathVariable String stackId) {

        Optional<Stack> stack = stackRepository.findById(stackId);

        return stack.map(ResponseEntity::ok)
                .orElseThrow(StackNotFound::new);
    }

    @RequestMapping(value = "/{stackId}/{value}", method = RequestMethod.POST)
    public ResponseEntity createValueInStack(
            @PathVariable String stackId,
            @PathVariable String value
    ) {
        Optional<Stack> oStack = stackRepository.findById(stackId);

        if (oStack.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The stack you are trying to alter does not exist");
        }

        Stack stack = oStack.get();

        if (!isNumeric(value)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The value you are trying to insert is not numerical");
        }

        stack.getValues().add(value);
        stackRepository.save(stack);

        return ResponseEntity.ok().body("Value added to the stack");
    }

    @RequestMapping(value = "/{stackId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStack(@PathVariable String stackId) {
        Optional<Stack> oStack = stackRepository.findById(stackId);

        if (oStack.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The stack you are trying to alter does not exist");
        }

        Stack stack = oStack.get();

        stackRepository.delete(stack);

        return ResponseEntity.status(HttpStatus.OK).body("Successful data deletion");
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
