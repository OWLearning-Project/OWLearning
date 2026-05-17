package app.OwLearning.Api.DTO.response;

import java.util.List;

public class DiscussionResponse {
    private int id;
    private List<UtilisateurAuthentifieResponse> participants;
    private List<MessageResponse> messages;

    public DiscussionResponse() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<UtilisateurAuthentifieResponse> getParticipants() { return participants; }
    public void setParticipants(List<UtilisateurAuthentifieResponse> participants) { this.participants = participants; }

    public List<MessageResponse> getMessages() { return messages; }
    public void setMessages(List<MessageResponse> messages) { this.messages = messages; }

}
