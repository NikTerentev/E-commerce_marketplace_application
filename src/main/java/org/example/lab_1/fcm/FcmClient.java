package org.example.lab_1.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FcmClient {

    public FcmClient() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            Logger.getLogger(FcmClient.class.getName())
                    .log(Level.SEVERE, null, e);
        }
    }

    public static void sendPersonal(String clientToken, String title, String body)
            throws ExecutionException, InterruptedException {
        Message message = Message.builder().setToken(clientToken)
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(createBuilder(title, body).build())
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        System.out.println("Отправленное сообщение: " + response);
    }

    private static WebpushNotification.Builder createBuilder(String title, String body) {
        WebpushNotification.Builder builder = WebpushNotification.builder();
        builder.addAction(new WebpushNotification
                        .Action("http://localhost:8080/chat", "Открыть чат"))
                .setTitle(title)
                .setBody(body);
        return builder;
    }
}
