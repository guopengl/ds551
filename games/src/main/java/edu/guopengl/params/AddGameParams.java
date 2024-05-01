package edu.guopengl.params;

import lombok.Data;

@Data
public class AddGameParams {
    private String name;
    private String platform;
    private String publisher;
    private String developer;
    private double criticScore;
    private double userScore;
    private int year;
    private double price;
}
