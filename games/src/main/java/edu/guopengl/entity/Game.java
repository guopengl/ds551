package edu.guopengl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private int timestamp;
    private String name;
    private String platform;
    private String publisher;
    private String developer;
    private double criticScore;
    private double userScore;
    private int year;
    private double price;
}
