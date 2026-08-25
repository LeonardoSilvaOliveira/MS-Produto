package br.fiap.ms_produto.service;


import br.fiap.ms_produto.dto.ProdutoDTO;
import br.fiap.ms_produto.dto.ProdutoRequestDto;
import br.fiap.ms_produto.dto.ProdutoResponseDto;
import br.fiap.ms_produto.entities.Categoria;
import br.fiap.ms_produto.entities.Produto;
import br.fiap.ms_produto.exceptions.DatabaseException;
import br.fiap.ms_produto.exceptions.ResourceNotFoundException;
import br.fiap.ms_produto.repository.CategoriaRepository;
import br.fiap.ms_produto.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAllProdutos() {

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findProdutoById(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoResponseDto saveProduto(ProdutoRequestDto requestDto) {

        Produto produto = new  Produto();
        copyDtoToProduto(requestDto, produto);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDto(produto);
    }

    @Transactional
    public ProdutoResponseDto updateProduto(Long id, ProdutoRequestDto requestDto) {

        try {
            Produto produto = produtoRepository.getReferenceById(id);
            copyDtoToProduto(requestDto, produto);
            produto = produtoRepository.save(produto);
            return new ProdutoResponseDto(produto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível atualizar o produto. ID: " + id);
        }
    }

    @Transactional
    public void deleteProdutoById(Long id) {

        if (!produtoRepository.existsById(id)) {

            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        produtoRepository.deleteById(id);

    }

    private void copyDtoToProduto(ProdutoRequestDto requestDto, Produto produto) {

        produto.setNome(requestDto.getNome());
        produto.setDescricao(requestDto.getDescricao());
        produto.setValor(requestDto.getValor());

        Categoria categoria = categoriaRepository.findById(requestDto.getCategoriaId()).orElseThrow(
                () -> new DatabaseException("Não foi possivel salvar o produto. categoria inesxistente" +
                        " (ID: " + requestDto.getCategoriaId() + ")")
        );

        produto.setCategoria(categoria);
    }

}
