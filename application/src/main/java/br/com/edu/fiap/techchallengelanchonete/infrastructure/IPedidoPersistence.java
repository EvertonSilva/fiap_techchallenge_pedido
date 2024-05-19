package br.com.edu.fiap.techchallengelanchonete.infrastructure;

import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoPersistence {
    Pedido registraPedido(Pedido pedido);
    Optional<Pedido> pedidoPorId(Long idPedido);
    List<Pedido> listaPedidos();
    Pedido atualizarPedido(Pedido pedido);
    Optional<Pedido> consultaPedidoPorCodigo(String codigoPedido);
}
