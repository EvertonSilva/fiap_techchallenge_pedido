package br.com.edu.fiap.techchallengelanchonete.domain;

import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.Cliente;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Codigo;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.DataCriacao;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Valor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Pedido extends DomainObject {
    private List<ItemPedido> itens;
    private Cliente cliente;
    private Codigo codigo;
    private DataCriacao data;

    public Pedido() {
        this.codigo = new Codigo();
        this.data = new DataCriacao();
        this.cliente = new Cliente();
        this.itens = new ArrayList<>();
    }

    public static List<Pedido> ordenaPorDataCriacao(List<Pedido> pedidos) {
        if (pedidos == null)
            return new ArrayList<>();

        List<Pedido> pedidosOrdenados = new ArrayList<>(pedidos);
        pedidosOrdenados = ordenaPorDataMaisAntiga(pedidosOrdenados);

        return pedidosOrdenados;
    }

    public static List<Pedido> ordenaPorDataMaisAntiga(List<Pedido> pedidos) {
        if (pedidos == null)
            return new ArrayList<>();

        List<Pedido> pedidosOrdenados = new ArrayList<>(pedidos);
        pedidosOrdenados.sort(
            Comparator.comparing(
                pedido -> pedido.getData().getValor().getTime()));

        return pedidosOrdenados;
    }

    public Valor getValorTotal() {
        Valor valorTotal = new Valor(new BigDecimal(0));

        if (itens == null)
            return valorTotal;

        BigDecimal valorTotalAtualizado = new BigDecimal(0);
        for (ItemPedido item: itens) {
            var subTotal = item.getSubTotal().getValor();
            valorTotalAtualizado = valorTotalAtualizado.add(subTotal);
        }

        var valorTotalAtualizadoArredondado = valorTotalAtualizado.setScale(2, RoundingMode.HALF_DOWN);
        valorTotal.setValor(valorTotalAtualizadoArredondado);
        return valorTotal;
    }
}
