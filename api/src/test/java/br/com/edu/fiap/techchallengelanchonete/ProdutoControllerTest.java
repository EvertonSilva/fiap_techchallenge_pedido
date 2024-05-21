package br.com.edu.fiap.techchallengelanchonete;

import br.com.edu.fiap.techchallengelanchonete.domain.Categoria;
import br.com.edu.fiap.techchallengelanchonete.domain.Produto;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Descricao;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Id;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Nome;
import br.com.edu.fiap.techchallengelanchonete.domain.valueobject.Valor;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.categoria.CategoriaModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.produto.ProdutoModel;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.produto.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallengeLanchoneteApplication.class)
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void criaProduto() throws Exception {
        String categoria = "Lanches";
        mvc.perform(post("/produtos?categoria=" + categoria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(produtoMock())))
                .andExpect(status().isCreated());
    }

    @Test
    void listaProdutos() throws Exception {
        salvaProdutoModelMock();
        mvc.perform(get("/produtos"))
                .andExpect(status().isOk());
    }

    @Test
    void buscaProduto() throws Exception {
        salvaProdutoModelMock();
        Long idProduto = 1l;
        mvc.perform(get("/produtos/" + idProduto))
                .andExpect(status().isOk());
    }

    private String toJson(Object objeto) {
        try {
            return new ObjectMapper().writeValueAsString(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Produto produtoMock() {
        Categoria categoria = new Categoria();
        categoria.setId(new Id(1l));

        Produto produto = new Produto();
        produto.setNome(new Nome("Hamburguer"));
        produto.setDescricao(new Descricao("Hamburguer"));
        produto.setPreco(new Valor(new BigDecimal(1)));
        produto.setCategoria(categoria);
        return produto;
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
