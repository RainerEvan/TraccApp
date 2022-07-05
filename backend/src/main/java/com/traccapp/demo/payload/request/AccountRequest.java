package com.traccapp.demo.payload.request;

import java.util.UUID;
import com.traccapp.demo.data.ERoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String contactNo;
    private UUID divisionId;
    private Boolean isActive;
    private ERoles rolesName;
}
