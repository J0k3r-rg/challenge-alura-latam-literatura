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
                    buscarLibroPorTituloEnGutendex();
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
                4- Buscar libro por lenguaje en la base de datos
                5- Buscar libro por autor en la base de datos
                6- Buscar libro por año de autor en la base de datos
                7- Top 10 mas descargados de la base de datos
                8- Buscar Autor por nombre en la base de datos
                
                0- Salir de la aplicacion
                Ingrese opcion de la operacion que desea realizar""");
        return scanner.nextLine();
    }

    private void buscarLibroPorTituloEnGutendex() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        String title = scanner.nextLine();
        List<LibroResponse> result = mapperData.getData(gutendex.searchForTitle(title),ResponseResult.class).results().stream().limit(10).toList();
        mostrarListaConIdice(result);
        System.out.println("Quiere guardar algun libro de la lista?");
        System.out.println("SI - Guardar");
        System.out.println("Cualquier opcion = No guardar");
        System.out.println("TODOS - guardar todos los libros mostrados");
        String res = scanner.nextLine();
        if(res.equalsIgnoreCase("si")){
            System.out.println("ingrese numero del libro que desea guardar");
            int index = leerInteger();
            libroService.guardarLibro((LibroResponse) obtenerElementoPorIndice(result,index));
        }
        if(res.equalsIgnoreCase("todos")){
            result.forEach(resul -> libroService.guardarLibro(resul));
        }
    }

    private void listarTodosLosAutores(){
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        mostrarLista(autores);
    }

    private void listarTodosLosLibros(){
        List<Libro> libros = libroService.buscarTodosLosLibros();
        mostrarLista(libros);
    }

    private void buscarLibroPorLenguaje(){
        System.out.println("Seleccione el lenguaje para buscar en la base de datos:");
        List<Lang> langs = langService.obtenerTodosLosLenguajes();
        mostrarListaConIdice(langs);
        int index = leerInteger();
        List<Libro> libros = libroService.buscarPorLenguaje(((Lang)obtenerElementoPorIndice(langs,index)).getLang());
        mostrarLista(libros);
    }

    private void buscarLibroPorAutor(){
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        AtomicInteger i = new AtomicInteger(1);
        System.out.println("Seleccione el autor para buscar en la base de datos:");
        autores.forEach(autor -> System.out.println(i.getAndIncrement() + " - " + autor.getName()));
        int index = leerInteger();
        List<Libro> libros = libroService.buscarPorAutor(((Autor) obtenerElementoPorIndice(autores,index)).getName());
        mostrarLista(libros);
    }

    private void buscarLibroPorAutorAnio(){
        System.out.println("Ingrese año para buscar libro");
        int anio = leerInteger();
        List<Autor> autores = autorService.obtenerTodosLosAutores().stream().filter( autor -> autor.getDeath_year() == null || autor.getDeath_year() > anio ).toList();
        List<Libro> libros = new ArrayList<>();
        autores.forEach(autor -> libros.addAll(libroService.buscarPorAutor(autor.getName())));
        mostrarLista(libros);
    }

    private void mostrarTop10(){
        List<Libro> libros = libroService.mostrarTop10Descargados();
        mostrarLista(libros);
    }

    private void buscarAutorPorNombre(){
        System.out.println("Ingrese nombre del autor que desea buscar en la base de datos");
        List<Autor> autores = autorService.listarAutoresPorNombre(scanner.nextLine());
        mostrarLista(autores);
    }

    private void mostrarLista(List<?> list){
        if (list.isEmpty()){
            System.out.println("****** no se encontraron resultados en la base de datos ******");
            return;
        }
        list.forEach(System.out::println);
    }

    private void mostrarListaConIdice(List<?> list){
        if (list.isEmpty()) {
            System.out.println("****** No hay resultados para mostrar ******");
            return;
        }
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(
                item -> {
                    if (item instanceof Lang) System.out.println(i.getAndIncrement() + " - "+((Lang)item).getLang());
                    if (item instanceof LibroResponse) System.out.println(i.getAndIncrement() + " - "+((LibroResponse)item).title());
                }
        );
    }

    private Integer leerInteger(){
        while(true){
            try {
                String value = scanner.nextLine();
                return Integer.valueOf(value);
            } catch (NumberFormatException e){
                System.out.println("Dato Ingresado invalido, solo se permiten numeros. Intete de nuevo:");
            }
        }
    }

    public Object obtenerElementoPorIndice(List<?> list, int index){
        while (true){
            try{
                return list.get(index-1);
            }catch (IndexOutOfBoundsException e){
                System.out.println("Indice fuera de rango, intente de nuevo:");
                index = leerInteger();
            };
        }
    }
}
