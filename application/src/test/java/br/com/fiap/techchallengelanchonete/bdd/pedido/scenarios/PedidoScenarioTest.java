package br.com.fiap.techchallengelanchonete.bdd.pedido.scenarios;

import br.com.edu.fiap.techchallengelanchonete.domain.Categoria;
import br.com.edu.fiap.techchallengelanchonete.domain.ItemPedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Produto;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Id;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Nome;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Quantidade;
import br.com.fiap.techchallengelanchonete.bdd.pedido.steps.EntaoPedidoCriado;
import br.com.fiap.techchallengelanchonete.bdd.pedido.steps.DadoPedido;
import br.com.fiap.techchallengelanchonete.bdd.pedido.steps.QuandoSolicitaInclusaoPedido;
import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PedidoScenarioTest extends ScenarioTest<DadoPedido, QuandoSolicitaInclusaoPedido, EntaoPedidoCriado> {
    @Test
    public void cria_pedido() {
        given().pedido_com_itens(montaItens());
        when().solicita_inclusao_pedido();
        then().pedido_deve_ser_criado_com_status_201();
    }

    private List<ItemPedido> montaItens() {
        Categoria categoria = new Categoria();
        categoria.setId(new Id(1l));

        Produto produto = new Produto();
        produto.setId(new Id(1l));
        produto.setNome(new Nome("Hamburguer"));
        produto.setCategoria(categoria);

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(new Quantidade(1));

        List<ItemPedido> itens = new ArrayList<>();
        itens.add(item);
        return itens;
    }
}
