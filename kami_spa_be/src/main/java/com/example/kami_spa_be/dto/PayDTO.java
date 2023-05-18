package com.example.kami_spa_be.dto;

public class PayDTO {
    private String email;
    private  double total;

    public PayDTO() {
    }

    public PayDTO(String email, double total) {
        this.email = email;
        this.total = total;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
