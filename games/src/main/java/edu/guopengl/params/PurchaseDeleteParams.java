package edu.guopengl.params;

import lombok.Data;

@Data
public class PurchaseDeleteParams {
    private String userName;
    private String gameName;
    private double price;
}
