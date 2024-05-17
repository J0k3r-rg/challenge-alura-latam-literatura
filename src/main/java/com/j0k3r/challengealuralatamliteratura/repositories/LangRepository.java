package com.j0k3r.challengealuralatamliteratura.repositories;

import com.j0k3r.challengealuralatamliteratura.models.Lang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LangRepository extends JpaRepository<Lang,Long> {
    Optional<Lang> findByLang(String lang);
}
