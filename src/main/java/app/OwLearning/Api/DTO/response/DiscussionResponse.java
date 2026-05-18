package app.OwLearning.Api.DTO.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiscussionResponse {
    private int id;
    private List<UtilisateurAuthentifieResponse> participants;
    private List<MessageResponse> messages;

    public DiscussionResponse() {}

}
