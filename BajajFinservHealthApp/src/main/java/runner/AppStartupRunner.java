package runner;

import com.bajajfinserv.health.dto.WebhookResponse;
import com.bajajfinserv.health.service.SQLSolverService;
import com.bajajfinserv.health.service.SubmissionService;
import com.bajajfinserv.health.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;
    private final SQLSolverService sqlSolverService;
    private final SubmissionService submissionService;

    public AppStartupRunner(WebhookService webhookService,
                            SQLSolverService sqlSolverService,
                            SubmissionService submissionService) {
        this.webhookService = webhookService;
        this.sqlSolverService = sqlSolverService;
        this.submissionService = submissionService;
    }

    @Override
    public void run(String... args) {
        String name = "John Doe";
        String regNo = "REG12346";  // ✅ Even RegNo (Question 2)
        String email = "john@example.com";

        WebhookResponse response = webhookService.generateWebhook(name, regNo, email);

        String finalQuery = sqlSolverService.solveSQLQuery(regNo);

        submissionService.submitSolution(response.getWebhook(), response.getAccessToken(), finalQuery);
    }
}
