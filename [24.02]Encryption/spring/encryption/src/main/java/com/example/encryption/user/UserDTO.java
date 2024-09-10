package com.example.encryption.user;

import com.example.encryption.crypto.EncryptedField;
import com.example.encryption.crypto.HashingField;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    private String name;
    @EncryptedField
    private String address;
    @EncryptedField
    private String email;
    @EncryptedField
    private String phonee;
    @HashingField
    private String phoneh;
    private String etc;

}
