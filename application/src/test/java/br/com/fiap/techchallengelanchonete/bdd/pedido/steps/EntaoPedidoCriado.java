package br.com.fiap.techchallengelanchonete.bdd.pedido.steps;

import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
public class EntaoPedidoCriado extends Stage<EntaoPedidoCriado> {
    @ProvidedScenarioState
    private ResponseEntity<Pedido> response;

    public EntaoPedidoCriado pedido_deve_ser_criado_com_status_201() {
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        Pedido pedidoCriado = response.getBody();
        assertThat(pedidoCriado).isNotNull();
        assertThat(pedidoCriado.getId()).isNotNull();
        return self();
    }
}
