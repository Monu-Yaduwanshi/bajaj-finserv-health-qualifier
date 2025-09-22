package service;

import com.bajajfinserv.health.dto.WebhookRequest;
import com.bajajfinserv.health.dto.WebhookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebhookService {
    private final WebClient webClient;

    public WebhookService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public WebhookResponse generateWebhook(String name, String regNo, String email) {
        return webClient.post()
                .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                .bodyValue(new WebhookRequest(name, regNo, email))
                .retrieve()
                .bodyToMono(WebhookResponse.class)
                .block();
    }
}
