package br.com.fiap.techchallengelanchonete.bdd.pedido.steps;

import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class QuandoSolicitaInclusaoPedido extends Stage<QuandoSolicitaInclusaoPedido> {

    @ProvidedScenarioState
    private Pedido pedido;
    @ProvidedScenarioState
    private ResponseEntity<Pedido> response;
    private RestTemplate restTemplate = new RestTemplate();

    public QuandoSolicitaInclusaoPedido solicita_inclusao_pedido() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // insere produto previamente
        HttpEntity<String> requestProduto = new HttpEntity<>(toJson(pedido.getItens().get(0).getProduto()), headers);
        restTemplate.postForEntity("http://localhost:8081/api/produtos", requestProduto, Produto.class);

        // tenta inserir pedido
        HttpEntity<String> requestPedido = new HttpEntity<>(toJson(pedido), headers);
        response = restTemplate.postForEntity("http://localhost:8081/api/pedidos", requestPedido, Pedido.class);
        return self();
    }
    private String toJson(Object objeto) {
        try {
            return new ObjectMapper().writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
