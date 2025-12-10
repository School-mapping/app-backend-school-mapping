package school.sptech;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

public class SlackNotifier {

    private BancoRepositorio bancoRepositorio = new BancoRepositorio();;

    public void notificar(String message) {

        String token = bancoRepositorio.getJdbcTemplate().queryForObject("SELECT token FROM TB_Bot_Slack WHERE id = 1", String.class);
        
        Slack slack = Slack.getInstance();

        try {
            MethodsClient methods = slack.methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("school_mapping_hub")
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