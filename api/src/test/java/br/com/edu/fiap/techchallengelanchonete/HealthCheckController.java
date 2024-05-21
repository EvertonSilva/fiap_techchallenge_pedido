package br.com.edu.fiap.techchallengelanchonete;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallengeLanchoneteApplication.class)
@AutoConfigureMockMvc
public class HealthCheckController {
    @Autowired
    private MockMvc mvc;

    @Test
    void healthCheck() throws Exception {

        mvc.perform(get("/health"))
                .andExpect(status().isOk());

    }
}
