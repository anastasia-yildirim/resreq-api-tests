package models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponseModel {
    @JsonProperty("data")
    private UserData userData;
    private Support support;
}
