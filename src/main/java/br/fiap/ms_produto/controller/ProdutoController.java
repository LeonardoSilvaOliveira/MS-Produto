package br.fiap.ms_produto.controller;

import br.fiap.ms_produto.dto.ProdutoDTO;
import br.fiap.ms_produto.dto.ProdutoRequestDto;
import br.fiap.ms_produto.dto.ProdutoResponseDto;
import br.fiap.ms_produto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoService produtoService;

//    @Profile("test")
//    @GetMapping("/--demo/500")
//    public String force500(){
//        throw new RuntimeException("Erro 500 forçado");
//    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos(){

        List<ProdutoDTO> list = produtoService.findAllProdutos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id){

        ProdutoDTO produtoDTO = produtoService.findProdutoById(id);

        return ResponseEntity.ok(produtoDTO);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> createProduto(@RequestBody @Valid ProdutoRequestDto requestDto){

        ProdutoResponseDto produtoDTO = produtoService.saveProduto(requestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(produtoDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> updateProduto(@PathVariable Long id,
                                                    @RequestBody @Valid ProdutoRequestDto requestDto){

        ProdutoResponseDto produtoDTO = produtoService.updatePruduto(id, requestDto);

        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){

        produtoService.deleteProdutoById(id);

        return ResponseEntity.noContent().build();
    }
}
