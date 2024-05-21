package br.com.edu.fiap.techchallengelanchonete;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.Cliente;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.CPF;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Email;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Nome;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.cliente.ClienteModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.cliente.ClienteRepository;
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
public class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void criaCliente() throws Exception {
        limpaBaseClientes();
        mvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(clienteMock())))
                .andExpect(status().isCreated());
    }

    @Test
    void buscaCliente() throws Exception {
        salvaClienteModelMock();
        String cpf = "44362907866";
        mvc.perform(get("/clientes?cpf=" + cpf))
                .andExpect(status().isOk());
    }
    private String toJson(Object objeto) {
        try {
            return new ObjectMapper().writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Cliente clienteMock() {
        Cliente cliente = new Cliente();
        cliente.setCpf(new CPF("44362907866"));
        cliente.setNome(new Nome("Lucas Batista da Silva"));
        cliente.setEmail(new Email("lucasbatista605@gmail.com"));
        return cliente;
    }

    private void salvaClienteModelMock() {
        ClienteModel cliente = new ClienteModel();
        cliente.setCpf("44362907866");
        cliente.setEmail("lucasbatista605@gmail.com");
        cliente.setNome("Lucas Batista da Silva");
        clienteRepository.save(cliente);
    }

    private void limpaBaseClientes() {
        clienteRepository.deleteAll();
    }

}
