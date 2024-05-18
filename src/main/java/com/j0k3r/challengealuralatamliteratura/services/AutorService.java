package com.j0k3r.challengealuralatamliteratura.services;

import com.j0k3r.challengealuralatamliteratura.models.Autor;
import com.j0k3r.challengealuralatamliteratura.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> obtenerTodosLosAutores(){
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresPorNombre(String nombre){
        return autorRepository.findByNameContaining(nombre);
    }
}
