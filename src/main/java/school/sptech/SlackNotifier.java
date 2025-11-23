package school.sptech;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

public class SlackNotifier {

    public void notificar(String message) {
        // Carrega as variáveis de ambiente do arquivo .env
        Dotenv dotenv = Dotenv.configure()
                .directory("C:\\Users\\victo\\OneDrive\\Área de Trabalho\\SchoolMapping\\app-backend-school-mapping")
                .load();
                
        String token = dotenv.get("SLACK_BOT_TOKEN");
        String channelId = dotenv.get("SLACK_CHANNEL_ID");
        
        Slack slack = Slack.getInstance();

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