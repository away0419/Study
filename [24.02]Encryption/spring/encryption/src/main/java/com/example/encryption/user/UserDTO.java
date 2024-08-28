package com.example.encryption.user;

import com.example.encryption.encryption.EncryptedField;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class UserDTO {
    private String name;
    @EncryptedField
    private String address;
    @EncryptedField
    private String phone;
    @EncryptedField
    private String email;
    private String etc;

}
