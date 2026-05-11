package app.OwLearning.Infrastructure.Persistence;

import app.OwLearning.Domain.Models.Chapitre;
import app.OwLearning.Domain.Models.Cours;
import app.OwLearning.Domain.Models.Discussion;
import app.OwLearning.Domain.Models.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelationReconstructor
{
    public void reconstructCoursChapitres(Cours cours)
    {
        if (cours != null && cours.getChapitres() != null)
        {
            for (int i=0; i<cours.getChapitres().size(); i++)
            {
                Chapitre chapitre = cours.getChapitres().get(i);
                chapitre.setCours(cours);
            }
        }
    }

    public void reconstructCoursChapitres(List<Cours> coursList)
    {
        if (coursList != null)
        {
            for (int i=0; i<coursList.size(); i++)
            {
                Cours cours = coursList.get(i);
                reconstructCoursChapitres(cours);
            }
        }
    }

    public void reconstructDiscussionMessages(Discussion discussion)
    {
        if (discussion != null && discussion.getMessages() != null)
        {
            for(int i=0; i<discussion.getMessages().size(); i++)
            {
                Message message = discussion.getMessages().get(i);
                message.setDiscussion(discussion);
            }
        }
    }

    public void reconstructDiscussionMessages(List<Discussion> discussionsList)
    {
        if (discussionsList != null)
        {
            for(int i=0; i<discussionsList.size(); i++)
            {
                Discussion discussion = discussionsList.get(i);
                reconstructDiscussionMessages(discussion);
            }
        }
    }
}
