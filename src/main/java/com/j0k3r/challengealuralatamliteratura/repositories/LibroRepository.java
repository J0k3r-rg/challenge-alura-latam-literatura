package com.j0k3r.challengealuralatamliteratura.repositories;

import com.j0k3r.challengealuralatamliteratura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
