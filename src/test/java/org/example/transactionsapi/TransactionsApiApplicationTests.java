package org.example.transactionsapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class TransactionsApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void aggregateEndpointReturnsCategoryTotalsForTheMockData() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/aggregates")
                        .param("from", "2026-01-01")
                        .param("to", "2026-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grandTotal").value(483.49))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories.length()").value(4));

        mockMvc.perform(get("/api/v1/transactions/aggregates")
                        .param("accountId", "1")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grandTotal").value(125.5))
                .andExpect(jsonPath("$.categories[0].categoryName").value("Food"));

        mockMvc.perform(get("/api/v1/transactions/aggregates")
                        .param("accountId", "2")
                        .param("from", "2026-01-01")
                        .param("to", "2026-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grandTotal").value(309.99))
                .andExpect(jsonPath("$.categories.length()").value(2));
    }
}
