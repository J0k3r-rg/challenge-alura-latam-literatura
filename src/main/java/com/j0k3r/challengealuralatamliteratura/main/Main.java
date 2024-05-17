package com.j0k3r.challengealuralatamliteratura.main;

import com.j0k3r.challengealuralatamliteratura.apis.Gutendex;
import com.j0k3r.challengealuralatamliteratura.mappers.MapperData;
import com.j0k3r.challengealuralatamliteratura.repositories.LibroRepository;
import com.j0k3r.challengealuralatamliteratura.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Main {

    @Autowired
    private Gutendex gutendex;

    @Autowired
    private MapperData mapperData;

    @Autowired
    private LibroRepository libroRepository;

    public void init(){
        ResponseResult libros = mapperData.getData(gutendex.search("harry"), ResponseResult.class);
        System.out.println(libros);
    }

}
