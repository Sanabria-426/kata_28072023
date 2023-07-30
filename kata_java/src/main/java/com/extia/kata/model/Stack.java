package com.extia.kata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Document("stack")
@Getter
@Setter
@AllArgsConstructor
public class Stack {

    @Id
    private String id;
    private List<String> values;

    public Stack() {
        values = new ArrayList<>();
    }

    public void computeValues(String operand) {

        // Computing
        String firstValue = values.get(values.size() - 1);
        values.remove(values.size() - 1);
        String secondValue = values.get(values.size() - 1);
        values.remove(values.size() - 1);

        String newValue = null;

        switch(operand) {
            case "PLUS":
                newValue = String.valueOf(Integer.parseInt(firstValue) + Integer.parseInt(secondValue));
                break;
            case "MINUS":
                newValue = String.valueOf(Integer.parseInt(firstValue) - Integer.parseInt(secondValue));
                break;
            case "TIMES":
                newValue = String.valueOf(Integer.parseInt(firstValue) * Integer.parseInt(secondValue));
                break;
            case "DIVIDED_BY":
                if (secondValue.equals("0")) {
                    return;
                }
                newValue = String.valueOf(Integer.parseInt(firstValue) / Integer.parseInt(secondValue));
                break;
            default:
                break;
        }

        values.add(newValue);
    }
}
