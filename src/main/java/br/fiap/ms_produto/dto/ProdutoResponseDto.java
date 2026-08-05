package br.fiap.ms_produto.dto;

import br.fiap.ms_produto.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoResponseDto {

    private Long id;

    private String nome;
    private String descricao;
    private Double valor;
    private CategoriaResponseDto categoria;


    public ProdutoResponseDto(Produto produto){
        id = produto.getId();
        nome = produto.getNome();
        descricao=produto.getDescricao();
        valor=produto.getValor();
        categoria = new  CategoriaResponseDto(produto.getCategoria());
    }
}
