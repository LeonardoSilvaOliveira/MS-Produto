package br.fiap.ms_produto.dto;

import br.fiap.ms_produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoRequestDto {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 10, message = "Descricação deve ter pelo menos 10 caracteres")
    private String descricao;

    @NotNull(message = "O campo valor é requerido")
    @Positive(message = "O campo valor deve ser um número positivo maior que zero")
    private Double valor;

    @NotNull(message = "Campo categoria é requerido")
    private Long categoriaId;

    public ProdutoRequestDto(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();

        categoriaId = produto.getCategoria().getId();
    }
}
