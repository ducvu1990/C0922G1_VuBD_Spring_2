package com.example.kami_spa_be.security_authentication.payload.request;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Size;
import java.util.Objects;

public class ResetPasswordRequest implements Validator {

    private String username;
    private String password;

    @Size(min = 6,max = 20,message = "Mật khẩu mới phải từ 6-20 ký tự")
    private String newPassword;

    @Size(min = 6,max = 20,message = "Mật khẩu mới phải từ 6-20 ký tự")
    private String confirmNewPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String username, String password, String newPassword, String confirmNewPassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResetPasswordRequest resetPasswordRequest = (ResetPasswordRequest) target;
        if(!Objects.equals(resetPasswordRequest.getNewPassword(), resetPasswordRequest.getConfirmNewPassword())){
            errors.rejectValue("confirmNewPassword","confirmNewPasswordError","Mật khẩu xác nhận phải giống mật khẩu mới.");
        };
    }
}
