package br.com.edu.fiap.techchallengelanchonete.adapter;

import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.ClienteNulo;
import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Codigo;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.DataCriacao;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Id;
import br.com.edu.fiap.techchallengelanchonete.dto.ItemPedidoDTO;
import br.com.edu.fiap.techchallengelanchonete.dto.PedidoDTO;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.cliente.ClienteModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.pedido.PedidoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PedidoAdapter implements IAdapter<Pedido, PedidoModel> {

    private ClienteAdapter clienteAdpter;
    private ItemPedidoAdapter itemPedidoAdapter;

    @Autowired
    public PedidoAdapter(ItemPedidoAdapter itemPedidoAdapter) {
        this.clienteAdpter = new ClienteAdapter();
        this.itemPedidoAdapter = itemPedidoAdapter;
    }

    @Override
    public Pedido toDomain(PedidoModel pedidoModel) {
        if (pedidoModel == null)
            return null;

        var pedido = new Pedido();

        pedido.setId(new Id(pedidoModel.getId()));
        pedido.setCliente(clienteAdpter.toDomain(pedidoModel.getCliente()));
        pedido.setItens(pedidoModel.getItens().stream().map(x -> itemPedidoAdapter.toDomain(x)).collect(Collectors.toList()));
        pedido.setCodigo(new Codigo(pedidoModel.getCodigo()));
        pedido.setData(new DataCriacao(pedidoModel.getDataCriacao()));

        return pedido;
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        if (pedido == null)
            return null;

        var pedidoModel = new PedidoModel();

        if (pedido.getId() != null && pedido.getId().getValor() > 0)
            pedidoModel.setId(pedido.getId().getValor());

        ClienteModel clienteModel = null;
        if (!(pedido.getCliente() instanceof ClienteNulo))
            clienteModel = clienteAdpter.toModel(pedido.getCliente());

        pedidoModel.setCliente(clienteModel);
        pedidoModel.setItens(pedido.getItens().stream().map(x -> itemPedidoAdapter.toModel(x)).collect(Collectors.toList()));
        pedidoModel.setCodigo(pedido.getCodigo().getValor());
        pedidoModel.setDataCriacao(pedido.getData().getValor());

        return pedidoModel;
    }

    public PedidoDTO toDTO(Pedido pedido) {
        var pedidoDTO = new PedidoDTO();

        var itensDTO = new ArrayList<ItemPedidoDTO>();
        pedido.getItens().forEach(produto -> {
            var itemPedido = new ItemPedidoDTO();
            itemPedido.setNomeProduto(produto.getProduto().getNome().getPrimeiro());
            itemPedido.setDescricaoProduto(produto.getProduto().getDescricao().getValor());
            itemPedido.setCategoriaProduto(produto.getProduto().getCategoria().getNome().getPrimeiro());
            itemPedido.setPrecoProduto(produto.getProduto().getPreco().getValor());
            itemPedido.setQuantidadeItem(produto.getQuantidade().getValor());
            itensDTO.add(itemPedido);
        });

        pedidoDTO.setCodigo(pedido.getCodigo().getValor());
        pedidoDTO.setCpfCliente(pedido.getCliente().getCpf().getValor());
        pedidoDTO.setEmailCliente(pedido.getCliente().getEmail().getValor());
        pedidoDTO.setNomeCliente(pedido.getCliente().getNome().getValor());
        pedidoDTO.setValorTotal(pedido.getValorTotal().getValor());
        pedidoDTO.setData(pedido.getData().getValor());
        pedidoDTO.setItens(itensDTO);

        return pedidoDTO;
    }
}
