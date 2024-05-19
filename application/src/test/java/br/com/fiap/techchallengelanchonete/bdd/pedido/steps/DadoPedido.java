package br.com.fiap.techchallengelanchonete.bdd.pedido.steps;

import br.com.edu.fiap.techchallengelanchonete.domain.ItemPedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;


import java.util.List;

public class DadoPedido extends Stage<DadoPedido> {
    @ProvidedScenarioState
    private Pedido pedido;

    public DadoPedido pedido_com_itens(List<ItemPedido> itensPedido) {
        pedido = new Pedido();
        pedido.setItens(itensPedido);
        return self();
    }
}
