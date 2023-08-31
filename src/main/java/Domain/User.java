package Domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    private String user;
    private String password;
    private String correo;


    public User(String  user, String password){
        this.user= user;
        this.password =password;
    }


}
