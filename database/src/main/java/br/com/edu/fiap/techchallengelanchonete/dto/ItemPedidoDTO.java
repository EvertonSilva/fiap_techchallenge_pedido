package br.com.edu.fiap.techchallengelanchonete.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemPedidoDTO {

    private String nomeProduto;
    private String descricaoProduto;
    private BigDecimal precoProduto;
    private String categoriaProduto;
    private Integer quantidadeItem;

}
