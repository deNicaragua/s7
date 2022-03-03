package com.example.s7.model;

public class Friend {
    private final Integer id;
    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Friend(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

}
