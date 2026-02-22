package app.OwLearning.Api.Chapitre;


import app.OwLearning.Domain.Ports.IServices.IServiceChapitre;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chapitre")
public class ChapitreController {
    private final IServiceChapitre serviceChapitre;

    public ChapitreController(IServiceChapitre serviceChapitre){
        this.serviceChapitre = serviceChapitre;
    }
}
