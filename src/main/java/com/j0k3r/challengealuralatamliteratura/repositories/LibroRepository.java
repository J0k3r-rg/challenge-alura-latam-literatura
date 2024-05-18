package com.j0k3r.challengealuralatamliteratura.repositories;

import com.j0k3r.challengealuralatamliteratura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTitle(String title);

    List<Libro> findByLanguagesLang(String lang);

    List<Libro> findByAuthorsName(String name);

    @Query(
            "SELECT l FROM Libro l ORDER BY l.download_count DESC LIMIT 10"
    )
    List<Libro> findTop10OrderByDownload_countDesc();

}
