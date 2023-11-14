package user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private int age;

    public UserEntity transforUser(){
        return new UserEntity(id, name, age);
    }
    public static UserDTO of(UserEntity userEntity){
        return  new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getAge());
    }
}
