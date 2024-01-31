package com.example.api_nunesports.resource;

import com.example.api_nunesports.dto.ProdutoDto;
import com.example.api_nunesports.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:8080")

public class ProdutoResource {

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @PostMapping
    public ResponseEntity<ProdutoDto> cadastrar(@Valid @RequestBody ProdutoDto produtoDto){
        produtoDto = produtoService.cadastrar(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> listar(Pageable pageable) {
        return ResponseEntity.ok(produtoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(produtoService.buscarById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> atualizazr(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoDto produtoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.atualizar(id, produtoDto));
    }
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> delete(@PathVariable("produtoId") Long produtoId) {
        produtoService.delete(produtoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
