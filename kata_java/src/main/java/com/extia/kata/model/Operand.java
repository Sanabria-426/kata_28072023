package com.extia.kata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("operator")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operand {

    @Id
    private String id;
    private String operand;
}