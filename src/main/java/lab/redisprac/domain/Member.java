package lab.redisprac.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

// @Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {

    private Long id;
    private String name;
    private String email;
}