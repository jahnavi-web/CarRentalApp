package com.training.CarRentalApp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @JsonProperty("name")
    private String name;

    @Column(unique =true)
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @Column(unique =true)
    @JsonProperty("dl")
    private String dl;

    @JsonProperty("phone")
    private Long phone;

    @JsonProperty("profile_pic")
    @Column(columnDefinition = "LONGTEXT")
    private String profile_pic;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String aadhar) {
        this.dl = aadhar;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Users() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Users(Long user_id, String name, String email, String password, String dl, Long phone,
                 String profile_pic) {
        super();
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dl = dl;
        this.phone = phone;
        this.profile_pic = profile_pic;
    }


}

