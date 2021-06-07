package com.example.desafioSpring.dtos;

public class CreateUserRequest {
    private String name;
    private boolean isSeller;

    public CreateUserRequest(String name, boolean isSeller) {
        this.name = name;
        this.isSeller = isSeller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }
}
