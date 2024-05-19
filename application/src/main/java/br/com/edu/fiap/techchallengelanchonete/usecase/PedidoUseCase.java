package br.com.edu.fiap.techchallengelanchonete.usecase;

import br.com.edu.fiap.techchallengelanchonete.domain.*;
import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.ClienteNulo;
import br.com.edu.fiap.techchallengelanchonete.exception.ApplicationException;
import br.com.edu.fiap.techchallengelanchonete.exception.NotFoundResourceException;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.IClientePersistence;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.IPedidoNovoPublisher;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.IPedidoPersistence;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.IProdutoPersistence;

import java.util.List;
import java.util.Optional;

public class PedidoUseCase {
    private IPedidoPersistence pedidoPersistence;
    private IProdutoPersistence produtoPersistence;
    private IClientePersistence clientePersistence;
    private IPedidoNovoPublisher pedidoNovoPublisher;

    public PedidoUseCase(IPedidoPersistence pedidoPersistence, IProdutoPersistence produtoPersistence, IClientePersistence clientePersistence, IPedidoNovoPublisher pedidoNovoPublisher) {
        this.pedidoPersistence = pedidoPersistence;
        this.produtoPersistence = produtoPersistence;
        this.clientePersistence = clientePersistence;
        this.pedidoNovoPublisher = pedidoNovoPublisher;
    }

    public Pedido registraPedido(Pedido pedido) {
        if (!protudosExistentes(pedido))
            throw new ApplicationException("Produto(s) inexistente(s)!");

        pedido.getItens().forEach(itemPedido -> {
            var optionalProduto = this.produtoPersistence.buscaId(itemPedido.getProduto().getId().getValor());
            itemPedido.setProduto(optionalProduto.orElseThrow(() -> new NotFoundResourceException("Produto não encontrado!")));
        });

        if (pedido.getCliente() != null 
            && pedido.getCliente().getId() != null 
            && pedido.getCliente().getId().getValor() > 0)
        {
            var clienteExistente = this.clientePersistence.buscaId(pedido.getCliente().getId().getValor());
            if (clienteExistente instanceof ClienteNulo)
                throw new NotFoundResourceException("Cliente não encontrado!");

            pedido.setCliente(clienteExistente);
        } else {
            pedido.setCliente(new ClienteNulo());
        }
        Pedido pedidoSalvo = this.pedidoPersistence.registraPedido(pedido);
        if(pedidoSalvo != null)
            this.pedidoNovoPublisher.publica(pedido);
        return pedidoSalvo;
    }

    public Pedido atualizaPedido(Pedido pedido) {
        return this.pedidoPersistence.atualizarPedido(pedido);
    }

    public Optional<Pedido> consultaPedido(String codigoPedido) {
        return pedidoPersistence.consultaPedidoPorCodigo(codigoPedido);
    }

    public Optional<Pedido> buscaPorId(Long idPedido) {
        return pedidoPersistence.pedidoPorId(idPedido);
    }

    public List<Pedido> listaPedidos() {
        List<Pedido> pedidos = pedidoPersistence.listaPedidos();
        return Pedido.ordenaPorDataCriacao(pedidos);
    }

    private boolean protudosExistentes(Pedido pedido) {
        return pedido.getItens() != null
            && !pedido.getItens().isEmpty()
            && pedido.getItens().stream().allMatch(x -> x.getProduto() != null);
    }
}
