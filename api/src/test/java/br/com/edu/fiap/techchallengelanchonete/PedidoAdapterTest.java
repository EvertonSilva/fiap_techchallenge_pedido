package br.com.edu.fiap.techchallengelanchonete;

import br.com.edu.fiap.techchallengelanchonete.adapter.CategoriaAdapter;
import br.com.edu.fiap.techchallengelanchonete.adapter.ItemPedidoAdapter;
import br.com.edu.fiap.techchallengelanchonete.adapter.PedidoAdapter;
import br.com.edu.fiap.techchallengelanchonete.adapter.ProdutoAdapter;
import br.com.edu.fiap.techchallengelanchonete.domain.Categoria;
import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.Cliente;
import br.com.edu.fiap.techchallengelanchonete.domain.ItemPedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Produto;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.*;
import br.com.edu.fiap.techchallengelanchonete.dto.ItemPedidoDTO;
import br.com.edu.fiap.techchallengelanchonete.dto.PedidoDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public class PedidoAdapterTest {

    CategoriaAdapter categoriaAdapter = new CategoriaAdapter();
    ProdutoAdapter produtoAdapter = new ProdutoAdapter(categoriaAdapter);
    ItemPedidoAdapter itemPedidoAdapter = new ItemPedidoAdapter(produtoAdapter);
    PedidoAdapter pedidoAdapter = new PedidoAdapter(itemPedidoAdapter);

    @Test
    public void testToDTO() {
        // Configuração do pedido
        Produto produto1 = new Produto();
        produto1.setNome(new Nome("Produto1"));
        produto1.setDescricao(new Descricao("Descricao1"));
        produto1.setCategoria(new Categoria(new Nome("Categoria1")));
        produto1.setPreco(new Valor(new BigDecimal("100.00")));

        ItemPedido item1 = new ItemPedido();
        item1.setProduto(produto1);
        item1.setQuantidade(new Quantidade(2));

        Produto produto2 = new Produto();
        produto2.setNome(new Nome("Produto2"));
        produto2.setDescricao(new Descricao("Descricao2"));
        produto2.setCategoria(new Categoria(new Nome("Categoria2")));
        produto2.setPreco(new Valor(new BigDecimal("50.00")));

        ItemPedido item2 = new ItemPedido();
        item2.setProduto(produto2);
        item2.setQuantidade(new Quantidade(1));

        Cliente cliente = new Cliente();
        cliente.setCpf(new CPF("12345678909"));
        cliente.setEmail(new Email("cliente@exemplo.com"));
        cliente.setNome(new Nome("Cliente Nome"));

        Pedido pedido = new Pedido();
        pedido.setItens(new ArrayList<>(Arrays.asList(item1, item2)));
        pedido.setCliente(cliente);
        pedido.setCodigo(new Codigo("123"));
        pedido.setData(new DataCriacao(new Date()));

        // Criação do DTO
        PedidoDTO pedidoDTO = pedidoAdapter.toDTO(pedido);

        // Verificações
        Assert.assertNotNull(pedidoDTO);
        Assert.assertEquals("123", pedidoDTO.getCodigo());
        Assert.assertEquals("12345678909", pedidoDTO.getCpfCliente());
        Assert.assertEquals("cliente@exemplo.com", pedidoDTO.getEmailCliente());
        Assert.assertEquals("Cliente Nome", pedidoDTO.getNomeCliente());
        Assert.assertEquals(new BigDecimal("250.00"), pedidoDTO.getValorTotal());
        Assert.assertEquals(pedido.getData().getValor(), pedidoDTO.getData());

        List<ItemPedidoDTO> itensDTO = pedidoDTO.getItens();
        Assert.assertNotNull(itensDTO);
        Assert.assertEquals(2, itensDTO.size());
    }
}