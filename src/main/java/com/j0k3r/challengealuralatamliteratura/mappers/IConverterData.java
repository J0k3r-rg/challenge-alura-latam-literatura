package com.j0k3r.challengealuralatamliteratura.mappers;

public interface IConverterData {

    <T> T getData(String json, Class<T> clas);

}
