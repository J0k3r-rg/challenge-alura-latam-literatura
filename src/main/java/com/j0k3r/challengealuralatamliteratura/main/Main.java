package com.j0k3r.challengealuralatamliteratura.main;

import com.j0k3r.challengealuralatamliteratura.apis.Gutendex;
import com.j0k3r.challengealuralatamliteratura.mappers.MapperData;
import com.j0k3r.challengealuralatamliteratura.models.Autor;
import com.j0k3r.challengealuralatamliteratura.models.Lang;
import com.j0k3r.challengealuralatamliteratura.models.Libro;
import com.j0k3r.challengealuralatamliteratura.response.LibroResponse;
import com.j0k3r.challengealuralatamliteratura.response.ResponseResult;
import com.j0k3r.challengealuralatamliteratura.services.AutorService;
import com.j0k3r.challengealuralatamliteratura.services.LangService;
import com.j0k3r.challengealuralatamliteratura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Main {

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private Gutendex gutendex;

    @Autowired
    private MapperData mapperData;

    @Autowired
    private LibroService libroService;

    @Autowired
    private LangService langService;

    @Autowired
    private AutorService autorService;

    public void init(){
        String opcion = "-1";
        while (!opcion.equals("0")){
            opcion = menuPrincipal();
            switch (opcion){
                case "1":
                    uscarLibroPorTituloEnGutendex();
                    break;
                case "2":
                    listarTodosLosAutores();
                    break;
                case "3":
                    listarTodosLosLibros();
                    break;
                case "4":
                    buscarLibroPorLenguaje();
                    break;
                case "5":
                    buscarLibroPorAutor();
                    break;
                case "6":
                    buscarLibroPorAutorAnio();
                    break;
                case "7":
                    mostrarTop10();
                    break;
                case "8":
                    buscarAutorPorNombre();
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
                1- Buscar libro por titulo en Gutendex
                2. Listar todos los autores de la base de datos
                3- Listar todos los libros de la base de datos
                4- Buscar libro por lenguaje
                5- Buscar libro por autor
                6- Buscar libro por año de autor (vivo)
                7- Top 10 mas descargados de la base de datos
                8- Buscar Autor por nombre en la base de datos
                
                0- Salir de la aplicacion
                Ingrese opcion de la operacion que desea realizar""");
        return scanner.nextLine();
    }

    private void uscarLibroPorTituloEnGutendex() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        String title = scanner.nextLine();
        List<LibroResponse> result = mapperData.getData(gutendex.searchForTitle(title),ResponseResult.class).results().stream().limit(10).toList();
        if(result.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo");
            return;
        }
        System.out.println("Resultados de la busqueda");
        int i=1;
        for(LibroResponse libro : result){
            System.out.printf("%d - %s \n",i,libro.toString());
            i++;
        }
        String res;
        System.out.println("Quiere guardar algun libro de la lista?");
        System.out.println("SI - Guardar");
        System.out.println("Cualquier opcion = No guardar");
        System.out.println("TODOS - guardar todos los libros mostrados");
        res = scanner.nextLine();
        if(res.equalsIgnoreCase("si")){
            System.out.println("ingrese numero del libro que desea guardar");
            String opcion = scanner.nextLine();
            libroService.guardarLibro(result.get(Integer.parseInt(opcion)-1));
        }
        if(res.equalsIgnoreCase("todos")){
            result.forEach(resul -> libroService.guardarLibro(resul));
        }
    }

    private void listarTodosLosAutores(){
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        if(autores.isEmpty()){
            System.out.println("No se encontraron autores en la base de datos");
        } else{
            autores.forEach(System.out::println);
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

    private void buscarLibroPorLenguaje(){
        System.out.println("Seleccione el lenguaje para buscar en la base de datos:");
        AtomicInteger i = new AtomicInteger(1);
        List<Lang> langs = langService.obtenerTodosLosLenguajes();
        langs.forEach(
                lang -> System.out.println(i.getAndIncrement() + " - "+lang.getLang())
        );
        int res = scanner.nextInt(); scanner.nextLine();
        List<Libro> libros = libroService.buscarPorLenguaje(langs.get(res-1).getLang());
        if (libros.isEmpty()){
            System.out.println("No se encontraron libros con ese lenguaje");
            return;
        }
        libros.forEach(System.out::println);
    }

    private void buscarLibroPorAutor(){
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        AtomicInteger i = new AtomicInteger(1);
        System.out.println("Seleccione el autor para buscar en la base de datos:");
        autores.forEach(autor -> System.out.println(i.getAndIncrement() + " - " + autor.getName()));
        int res = scanner.nextInt(); scanner.nextLine();
        List<Libro> libros = libroService.buscarPorAutor(autores.get(res-1).getName());
        if (libros.isEmpty()){
            System.out.println("No se encontraron libros con ese autor");
            return;
        }
        libros.forEach(System.out::println);
    }

    private void buscarLibroPorAutorAnio(){
        System.out.println("Ingrese año para buscar libro");
        int anio = scanner.nextInt();scanner.nextLine();
        List<Autor> autores = autorService.obtenerTodosLosAutores().stream().filter( autor -> autor.getDeath_year() == null || autor.getDeath_year() > anio ).toList();
        List<Libro> libros = new ArrayList<>();
        autores.forEach(autor -> libros.addAll(libroService.buscarPorAutor(autor.getName())));
        libros.forEach(System.out::println);
    }

    private void mostrarTop10(){
        libroService.mostrarTop10Descargados().forEach(System.out::println);
    }

    private void buscarAutorPorNombre(){
        System.out.println("Ingrese nombre del autor que desea buscar en la base de datos");
        List<Autor> autores = autorService.listarAutoresPorNombre(scanner.nextLine());
        if(autores.isEmpty()){
            System.out.println("****** No se encontraron autores con ese nombre en la base de datos ******");
            return;
        }
        autores.forEach(System.out::println);
    }
}
