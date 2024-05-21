package br.com.edu.fiap.techchallengelanchonete;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.edu.fiap.techchallengelanchonete.domain.Categoria;
import br.com.edu.fiap.techchallengelanchonete.domain.Cliente.Cliente;
import br.com.edu.fiap.techchallengelanchonete.domain.ItemPedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import br.com.edu.fiap.techchallengelanchonete.domain.Produto;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Id;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Nome;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Quantidade;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.categoria.CategoriaModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.produto.ProdutoModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.produto.ProdutoRepository;
import br.com.edu.fiap.techchallengelanchonete.messaging.PedidoNovoPublisher;
import br.com.edu.fiap.techchallengelanchonete.usecase.PedidoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallengeLanchoneteApplication.class)
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @MockBean
    private PedidoNovoPublisher pedidoNovoPublisher;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criaPedido() throws Exception {

        salvaProdutoModelMock();

        doNothing().when(pedidoNovoPublisher).publica(any(Pedido.class));

        mvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pedidoMock())))
                .andExpect(status().isCreated());

        verify(pedidoNovoPublisher, times(1)).publica(any(Pedido.class));
    }

    @Test
    void criaPedidoCliente() throws Exception {

        salvaProdutoModelMock();

        doNothing().when(pedidoNovoPublisher).publica(any(Pedido.class));

        mvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pedidoMockCliente())))
                .andExpect(status().isCreated());

        verify(pedidoNovoPublisher, times(1)).publica(any(Pedido.class));
    }

    @Test
    void listaPedidos() throws Exception {

        mvc.perform(get("/pedidos"))
                .andExpect(status().isOk());

    }

    @Test
    void buscaPedido() throws Exception {

        Long idPedido = 1l;
        mvc.perform(get("/pedidos/" + idPedido))
                .andExpect(status().isOk());

    }

    private String toJson(Object objeto) {
        try {
            return new ObjectMapper().writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Pedido pedidoMock() {
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

        Pedido pedido = new Pedido();
        pedido.setItens(itens);
        return pedido;
    }

    private Pedido pedidoMockCliente() {
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

        Cliente cliente = new Cliente();
        cliente.setId(new Id(1l));

        Pedido pedido = new Pedido();
        pedido.setItens(itens);
        return pedido;
    }
    private void salvaProdutoModelMock() {
        CategoriaModel categoriaModel = new CategoriaModel();
        categoriaModel.setNome("LANCHE");
        categoriaModel.setId(1l);

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("Hamburguer");
        produtoModel.setId(1l);
        produtoModel.setPreco(new BigDecimal(1l));
        produtoModel.setCategoria(categoriaModel);

        produtoRepository.save(produtoModel);
    }
}
