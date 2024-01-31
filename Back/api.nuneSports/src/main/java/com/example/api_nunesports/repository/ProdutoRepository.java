package com.example.api_nunesports.repository;

import com.example.api_nunesports.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    Produto findByNomeIgnoreCaseContaining(String nome);

}
