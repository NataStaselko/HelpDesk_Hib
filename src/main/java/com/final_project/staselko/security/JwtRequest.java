package com.final_project.staselko.security;

import com.final_project.staselko.utils.annotations.EmailValid;
import com.final_project.staselko.utils.annotations.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtRequest implements Serializable {
    @EmailValid
    private String username;

    @PasswordValid
    private String password;

}
