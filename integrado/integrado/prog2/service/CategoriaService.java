/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Categoria;
import integrado.prog2.exception.ReglaNegocioException;

public class CategoriaService {

    private static Long idCounter = 1L;

    public void crear(Categoria categoria) throws ReglaNegocioException {
        for (Categoria c : DatabaseMock.categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(categoria.getNombre())) {
                throw new ReglaNegocioException("Error: Ya existe una categoria con el nombre '" + categoria.getNombre() + "'.");
            }
        }
        categoria.setId(idCounter++);
        DatabaseMock.categorias.add(categoria);
    }

    public void listar() {
        boolean hayRegistros = false;
        for (Categoria c : DatabaseMock.categorias) {
            if (!c.isEliminado()) {
                System.out.println(c.toString());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay categorias cargadas.");
        }
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria c : DatabaseMock.categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        return null;
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);
        if (categoria != null) {
            categoria.setEliminado(true);
        }
    }
}