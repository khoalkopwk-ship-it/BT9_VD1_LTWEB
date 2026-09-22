package vn.iotstar.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private Long roleId;
    private String roleName;
    private boolean enabled;
    private LocalDateTime createdAt;
}