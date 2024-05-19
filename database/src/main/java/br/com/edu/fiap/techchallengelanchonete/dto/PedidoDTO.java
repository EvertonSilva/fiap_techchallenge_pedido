package br.com.edu.fiap.techchallengelanchonete.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDTO {
    
    private List<ItemPedidoDTO> itens;
    private String nomeCliente;
    private String emailCliente;
    private String cpfCliente;
    private String codigo;
    private Date data;
    private BigDecimal valorTotal;

}
