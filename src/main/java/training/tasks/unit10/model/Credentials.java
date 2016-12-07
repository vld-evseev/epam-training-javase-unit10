package training.tasks.unit10.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Credentials {

    String login;
    String password;

}
