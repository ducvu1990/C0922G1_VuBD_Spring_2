package com.example.kami_spa_be.dto;

public class OrderRequestDTO {
    private Long productId;
    private String email;
    private  int quantity;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long productId, String email, int quantity) {
        this.productId = productId;
        this.email = email;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
