package com.example.investbeacon.models;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

    @NotNull
    private long id;

    private String curPassword;

    private String newPassword;

    private String conPassword;

    public Password() {}

    public Password(long id, String curPassword, String newPassword, String conPassword) {
        this.id = id;
        this.curPassword = curPassword;
        this.newPassword = newPassword;
        this.conPassword = conPassword;
    }

    public Password(long id) {
        this.id = id;
    }
}
