package school.sptech;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;

public class SlackNotifier {

    public void notificar(String message) {
         String token = System.getenv("SLACK_BOT_TOKEN");

        Slack slack = Slack.getInstance();

        String channelId = System.getenv("SLACK_CHANNEL_ID");

        try {
            MethodsClient methods = slack.methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channelId)
                    .text(message)
                    .build();

            ChatPostMessageResponse response = methods.chatPostMessage(request);

            if (response.isOk()) {
                System.out.println("Mensagem enviada com sucesso!");
            } else {
                System.out.println("Erro ao enviar: " + response.getError());
            }

        } catch (IOException | SlackApiException e) {
            System.out.println("Erro de conexão ou API: " + e.getMessage());
        }
    }
}