/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Categoria;

public class CategoriaService {

    private static Long idCounter = 1L;

    public void crear(Categoria categoria) {

        categoria.setId(idCounter++);
        DatabaseMock.categorias.add(categoria);

    }

    public void listar() {

        for (Categoria c : DatabaseMock.categorias) {

            System.out.println(
                    c.getId() + " - "
                    + c.getNombre() + " - "
                    + c.getDescripcion()
            );

        }

    }

    public Categoria buscarPorId(Long id) {

        for (Categoria c : DatabaseMock.categorias) {

            if (c.getId().equals(id)) {
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