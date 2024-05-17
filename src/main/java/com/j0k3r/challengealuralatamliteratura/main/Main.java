package com.j0k3r.challengealuralatamliteratura.main;

import com.j0k3r.challengealuralatamliteratura.apis.Gutendex;
import com.j0k3r.challengealuralatamliteratura.mappers.MapperData;
import com.j0k3r.challengealuralatamliteratura.models.Libro;
import com.j0k3r.challengealuralatamliteratura.response.ResponseResult;
import com.j0k3r.challengealuralatamliteratura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Main {

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private Gutendex gutendex;

    @Autowired
    private MapperData mapperData;

    @Autowired
    private LibroService libroService;

    public void init(){
        String opcion = "-1";
        while (!opcion.equals("0")){
            opcion = menuPrincipal();
            switch (opcion){
                case "1":
                    buscarLibroEnGutendex();
                    break;
                case "2":
                    listarTodosLosLibros();
                    break;
                case "0":
                    System.out.println("""
                            *******************************************************
                            **       Gracias por utilizar nuestros servicios     **
                            *******************************************************""");
                    break;
                default:
                    System.out.println("*****    Opcion ingresada incorrecta    *****");
            }
        }
    }

    private String menuPrincipal(){
        System.out.println("""
                ------------------------------------------------------
                1- Buscar libro en Gudendex
                2- Listar todos los libros de la base de datos
                
                0- Salir de la aplicacion
                Ingrese opcion de la operacion que desea realizar""");
        return scanner.nextLine();
    }

    private void buscarLibroEnGutendex() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        String title = scanner.nextLine();
        List<Libro> result = mapperData.getData(gutendex.search(title),ResponseResult.class).results().stream().limit(10).toList();
        if(result.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo");
            return;
        }
        System.out.println("Resultados de la busqueda");
        int i=1;
        for(Libro libro : result){
            System.out.printf("%d - %s \n",i,libro.toString());
            i++;
        }
        String res;
        System.out.println("Quiere guardar algun libro de la lista?");
        System.out.println("SI");
        System.out.println("Cualquier opcion para NO");
        res = scanner.nextLine();
        if(res.equalsIgnoreCase("si")){
            System.out.println("ingrese numero del libro que desea guardar");
            String opcion = scanner.nextLine();
            libroService.guardarLibro(result.get(Integer.parseInt(opcion)-1));
            System.out.println("Libro guardado exitosamente");
        }
    }

    private void listarTodosLosLibros(){
        List<Libro> libros = libroService.buscarTodosLosLibros();
        if(libros.isEmpty()){
            System.out.println("No se encontraron libros en la base de datos");
        } else{
            libros.forEach(System.out::println);
        }
    }
}
