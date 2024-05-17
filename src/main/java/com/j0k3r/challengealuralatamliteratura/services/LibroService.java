package com.j0k3r.challengealuralatamliteratura.services;

import com.j0k3r.challengealuralatamliteratura.models.Autor;
import com.j0k3r.challengealuralatamliteratura.models.Lang;
import com.j0k3r.challengealuralatamliteratura.models.Libro;
import com.j0k3r.challengealuralatamliteratura.repositories.AutorRepository;
import com.j0k3r.challengealuralatamliteratura.repositories.LangRepository;
import com.j0k3r.challengealuralatamliteratura.repositories.LibroRepository;
import com.j0k3r.challengealuralatamliteratura.response.LibroResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LangRepository langRepository;

    @Transactional
    public void guardarLibro(LibroResponse libro){
        if(!libroRepository.existsById(libro.id())){
            List<Autor> autorList = new ArrayList<>();
            List<Lang> langList = new ArrayList<>();
            libro.authors().forEach(autorElement ->{
                Autor autor = autorRepository.findByName(autorElement.getName()).orElse(null);
                autorList.add(autor == null ? autorRepository.save(autorElement):autor);
            });
            libro.languages().forEach(langElement -> {
                Lang lang = langRepository.findByLang(langElement).orElse(null);
                langList.add(lang== null ? langRepository.save(new Lang(langElement)) : lang);
            });
            Libro libroFinal = new Libro(libro.id(), libro.title(),langList, libro.download_count(), autorList);
            libroRepository.save(libroFinal);
        }
    }

    public List<Libro> buscarTodosLosLibros(){
        return libroRepository.findAll();
    }

    public List<Libro> buscarLibroPorTitulo(String title){
        return libroRepository.findByTitle(title);
    }

    public List<Libro> buscarPorLenguaje(String lang){
        return libroRepository.findByLanguagesLang(lang);
    }

}
