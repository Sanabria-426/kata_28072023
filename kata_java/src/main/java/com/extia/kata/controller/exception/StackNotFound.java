package com.extia.kata.controller.exception;

public class StackNotFound extends RuntimeException {

    public StackNotFound() {
        super("Stack Not found");
    }
}
