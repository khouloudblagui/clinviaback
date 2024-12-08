package org.example.doctor.Controller;

import org.example.doctor.Service.DialogflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowWebhookController {

    private final DialogflowService dialogflowService;

    public DialogflowWebhookController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @PostMapping
    public ResponseEntity<?> handleDialogflowRequest(@RequestBody Map<String, Object> request) {
        if (request == null || !request.containsKey("queryResult")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requête invalide : queryResult manquant");
        }

        Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
        String userInput = (String) queryResult.get("queryText");

        // Extraire l'intent
        Map<String, Object> intent = (Map<String, Object>) queryResult.get("intent");
        String intentName = (String) intent.get("displayName");

        // Extraire les paramètres dynamiques
        Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");

        // Affichage des paramètres pour le debug
        System.out.println("Intent reçu : " + intentName);
        System.out.println("Parameters reçus : " + parameters);

        // Logique spécifique selon l'intent
        String responseText = "";
        switch (intentName) {
            case "InfoVaccinIntent":
                responseText = "Les vaccins recommandés varient selon l'âge et les pays. Veuillez consulter votre médecin pour un calendrier personnalisé.";
                break;

            case "PlanifierRendezVousIntent":
                String date = (String) parameters.get("date-time");
                String specialite = (String) parameters.get("specialite");
                responseText = "Je vais vous aider à planifier un rendez-vous avec un " + specialite + " à " + date + ".";
                break;

            case "RecommanderMedecinIntent":
                responseText = "En fonction de vos symptômes, nous allons vous recommander un médecin.";
                break;
            case "InfoMedicamentIntent":
                // Vérifiez que l'entité 'medicament' est bien extraite
                if (parameters.containsKey("medicament")) {
                    String medicament = (String) parameters.get("medicament");
                    responseText = "Voici des informations sur " + medicament + ".";
                } else {
                    responseText = "Je n'ai pas compris le médicament. Pouvez-vous reformuler ?";
                }
                break;

            case "ConseilsSanteIntent":
                responseText = "Pour rester en bonne santé, il est recommandé de manger équilibré, faire de l'exercice régulièrement, et consulter un médecin pour un suivi régulier.";
                break;

            case "IdentifierAllergieIntent":
                // Vérifiez que l'entité 'medicament' est bien extraite
                if (parameters.containsKey("medicament")) {
                    String medicament = (String) parameters.get("medicament");
                    responseText = "Vous pensez être allergique à " + medicament + ". Veuillez consulter un médecin pour un diagnostic précis.";
                } else {
                    responseText = "Je n'ai pas compris. Pouvez-vous préciser le médicament auquel vous êtes peut-être allergique ?";
                }
                break;

            default:
                responseText = "Je n'ai pas compris. Pouvez-vous reformuler ?";
                break;
        }

        // Construction de la réponse à envoyer à Dialogflow
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> fulfillmentMessage = new HashMap<>();
        fulfillmentMessage.put("text", Collections.singletonMap("text", Collections.singletonList(responseText)));
        response.put("fulfillmentMessages", Collections.singletonList(fulfillmentMessage));

        // Retourner la réponse à Dialogflow
        return ResponseEntity.ok(response);
    }
}
