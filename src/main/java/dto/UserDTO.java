package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String fullname;

    private String password;

    private String email;

    private boolean admin = false;

    private boolean active = true;
}
