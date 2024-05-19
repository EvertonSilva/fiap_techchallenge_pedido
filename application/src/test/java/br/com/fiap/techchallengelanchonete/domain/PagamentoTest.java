//package br.com.fiap.techchallengelanchonete.domain;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//
//class PagamentoTest {
//
//    @Test
//    void deveCriarItemPedidoVazio() {
//        var pagamento = new Pagamento();
//
//        assertAll("Atributos vazios",
//            () -> assertThat(pagamento).isNotNull(),
//            () -> assertThat(pagamento.getDataExpiracaoPagamento()).isNotNull(),
//            () -> assertThat(pagamento.getPixCopiaECola()).isNotNull(),
//            () -> assertThat(pagamento.getPixQRCode64()).isNotNull(),
//            () -> assertThat(pagamento.getStatus())
//                    .isNotNull()
//                    .isEqualTo(StatusPagamento.AGUARDANDO)
//        );
//    }
//
//}
