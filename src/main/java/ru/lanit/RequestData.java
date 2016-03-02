package ru.lanit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Getter
@Setter
@ToString
public class RequestData {
    private String login;
    private String password;
}