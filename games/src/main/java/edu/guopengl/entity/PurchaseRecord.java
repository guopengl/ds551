package edu.guopengl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRecord {
    private String userName;
    private String gameName;
    private int timestamp;
    private double price;
}
