package models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUsersListResponseModel {
    private int page;
    @JsonProperty("per_page")
    private int perPage;
    private int total;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("data")
    private List<UserData> userData;
    private Support support;
}