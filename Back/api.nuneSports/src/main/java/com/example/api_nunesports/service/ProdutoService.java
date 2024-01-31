package com.example.api_nunesports.service;

import com.example.api_nunesports.domain.Produto;
import com.example.api_nunesports.dto.ProdutoDto;
import com.example.api_nunesports.handler.exception.NotFound;
import com.example.api_nunesports.handler.exception.ConflitoException;
import com.example.api_nunesports.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoDto cadastrar(ProdutoDto produtoDto) {
        Produto produto = modelMapper.map(produtoDto,Produto.class);
        if(Objects.nonNull(produtoRepository.findByNomeIgnoreCaseContaining(produtoDto.getNome()))){
            throw new ConflitoException("Produto já cadastrado!");
        }

        Produto produtoCadastrado = produtoRepository.save(produto);
        return modelMapper.map(produtoCadastrado,ProdutoDto.class);
    }

    public Page<ProdutoDto> listar(Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        List<ProdutoDto> produtoDtos = produtos.getContent()
                .stream()
                .map(listaDeprodutos -> modelMapper.map(listaDeprodutos, ProdutoDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(produtoDtos, pageable, produtos.getTotalElements());
    }

    public ProdutoDto buscarById(Long id) {
        return  modelMapper.map(produtoRepository.findById(id).orElseThrow(new NotFound("Produto não encontrado")),ProdutoDto.class);
    }

    @Transactional
    public ProdutoDto atualizar(Long idProduto, ProdutoDto produtoDto) {
        produtoDto.setId(idProduto);
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produtoRepository.findById(idProduto).orElseThrow(new NotFound("Produto não encontrado"));
        Produto produtoSalvo = produtoRepository.save(produto);
        return modelMapper.map(produtoSalvo, ProdutoDto.class);
    }

    public void delete(Long produtoId) {
        produtoRepository.delete(produtoRepository.findById(produtoId).orElseThrow(new NotFound("Produto não encontrado")));
    }
}
