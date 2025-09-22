package dto;

import com.bajajfinserv.health.dto.SubmissionRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SubmissionService {
    private final WebClient webClient;

    public SubmissionService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        webClient.post()
                .uri(webhookUrl)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SubmissionRequest(finalQuery))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
