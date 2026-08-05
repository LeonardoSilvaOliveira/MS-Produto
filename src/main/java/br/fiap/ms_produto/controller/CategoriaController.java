package br.fiap.ms_produto.controller;

import br.fiap.ms_produto.dto.CategoriaDTO;
import br.fiap.ms_produto.dto.CategoriaRequestDto;
import br.fiap.ms_produto.dto.CategoriaResponseDto;
import br.fiap.ms_produto.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        List<CategoriaDTO> categorias = categoriaService.findAllCategorias();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {

        CategoriaDTO categoriaDto = categoriaService.findCategoriaById(id);

        return ResponseEntity.ok(categoriaDto);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> createCategoria(
            @RequestBody @Valid CategoriaRequestDto inputDto) {

        CategoriaResponseDto categoriaDto = categoriaService.saveCategoria(inputDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoriaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> updateCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDto inputDto) {

       CategoriaResponseDto categoriaDto = categoriaService.updateCategoria(id, inputDto);

        return ResponseEntity.ok(categoriaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long id){

        categoriaService.deleteCategoriaById(id);

        return ResponseEntity.noContent().build();
    }
}
