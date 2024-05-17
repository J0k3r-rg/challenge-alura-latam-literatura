package com.j0k3r.challengealuralatamliteratura.services;

import com.j0k3r.challengealuralatamliteratura.models.Lang;
import com.j0k3r.challengealuralatamliteratura.repositories.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LangService {

    @Autowired
    private LangRepository langRepository;

    public List<Lang> obtenerTodosLosLenguajes(){
        return langRepository.findAll();
    }
}
