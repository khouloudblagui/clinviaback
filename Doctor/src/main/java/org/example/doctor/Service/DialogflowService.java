package org.example.doctor.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class DialogflowService {

    private final SessionsClient sessionsClient;
    private final String projectId = "chatbotm-dical-ufel";  // Remplacez par votre ID de projet
    private final String sessionId = "random-session-id";  // Vous pouvez générer un ID unique pour chaque utilisateur ou session

    public DialogflowService() {
        SessionsClient tempSessionsClient = null;
        try {
            // Charger les informations d'identification depuis le fichier JSON
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:/Users/ISET/Downloads/chatbotm-dical-ufel-1209bab0dd95.json"))
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            // Créer un SessionsSettings avec les informations d'identification
            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            // Initialiser le client Dialogflow avec les paramètres de sessions
            tempSessionsClient = SessionsClient.create(sessionsSettings);
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du client Dialogflow: " + e.getMessage());
            e.printStackTrace();
        }
        this.sessionsClient = tempSessionsClient;
    }

    public String getDialogflowResponse(String textInput, Map<String, Object> parameters) {
        if (sessionsClient == null) {
            return "Client Dialogflow non initialisé.";
        }

        try {
            // Créer une session ID
            SessionName session = SessionName.of(projectId, sessionId);

            // Préparer la requête avec le texte et les paramètres
            TextInput.Builder textInputBuilder = TextInput.newBuilder().setText(textInput).setLanguageCode("fr");

            QueryInput queryInput = QueryInput.newBuilder().setText(textInputBuilder).build();

            // Ajouter des paramètres supplémentaires si nécessaire
            Struct.Builder paramsBuilder = Struct.newBuilder();
            parameters.forEach((key, value) -> paramsBuilder.putFields(key, Value.newBuilder().setStringValue(value.toString()).build()));

            QueryParameters queryParams = QueryParameters.newBuilder().setPayload(paramsBuilder.build()).build();

            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session.toString())
                    .setQueryInput(queryInput)
                    .setQueryParams(queryParams)
                    .build();

            // Envoie la requête à Dialogflow et reçoit la réponse
            DetectIntentResponse response = sessionsClient.detectIntent(request);

            // Extraire la réponse de Dialogflow
            return response.getQueryResult().getFulfillmentText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Désolé, une erreur s'est produite en interrogeant le service Dialogflow.";
        }
    }
}
