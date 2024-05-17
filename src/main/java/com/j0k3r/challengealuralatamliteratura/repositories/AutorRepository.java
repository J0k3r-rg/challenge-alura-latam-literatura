package com.j0k3r.challengealuralatamliteratura.repositories;

import com.j0k3r.challengealuralatamliteratura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByName(String name);

}
