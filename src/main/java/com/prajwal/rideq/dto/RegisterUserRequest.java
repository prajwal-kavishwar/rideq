package com.prajwal.rideq.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

    @NotBlank(message = "name is required")
    @Size(max=100,message = "name must be less than 100 character")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Size(min=6,message = "password must be atleast 6 letters")
    private String password;


}
