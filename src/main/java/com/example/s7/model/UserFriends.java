package com.example.s7.model;

import javax.persistence.*;


@Entity
@Table(name = "friends")
public class UserFriends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private Integer id;
    @Column(name="request_id")
    private  Integer requestId;
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "status")
    private String status;

    public UserFriends(){}

    public UserFriends(Integer requestId, Integer addressId, String status){
        this.requestId = requestId;
        this.addressId = addressId;
        this.status = status;
    }
    public UserFriends(Integer id, Integer requestId, Integer addressId, String status) {
        this.id = id;
        this.requestId = requestId;
        this.addressId = addressId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
