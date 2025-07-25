package com.example.assessment_1.backend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
public class Manager extends Person implements IManager {

}
