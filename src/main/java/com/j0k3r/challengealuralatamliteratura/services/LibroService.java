package com.j0k3r.challengealuralatamliteratura.services;

import com.j0k3r.challengealuralatamliteratura.models.Autor;
import com.j0k3r.challengealuralatamliteratura.models.Libro;
import com.j0k3r.challengealuralatamliteratura.repositories.AutorRepository;
import com.j0k3r.challengealuralatamliteratura.repositories.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void guardarLibro(Libro libro){
        if(!libroRepository.existsById(libro.getId())){
            List<Autor> autorList = new ArrayList<>();
            libro.getAuthors().forEach(autorElement ->{
                Autor autor = autorRepository.findByName(autorElement.getName()).orElse(null);
                autorList.add(autor == null ? autorRepository.save(autorElement):autor);
            });
            libro.setAuthors(autorList);
            libroRepository.save(libro);
        }
    }

    public List<Libro> buscarTodosLosLibros(){
        return libroRepository.findAll();
    }

    public List<Libro> buscarLibroPorTitulo(String title){
        return libroRepository.findByTitle(title);
    }

}
