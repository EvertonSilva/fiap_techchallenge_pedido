package br.com.edu.fiap.techchallengelanchonete.infrastructure;

import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;

public interface IPedidoNovoPublisher {
    void publica(Pedido pedido);
}
