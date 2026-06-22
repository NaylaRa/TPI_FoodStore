/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Producto;

public class ProductoService {

    private static Long idCounter = 1L;

    public void crear(Producto producto) {

        producto.setId(idCounter++);
        DatabaseMock.productos.add(producto);

    }

    public void listar() {

        for (Producto p : DatabaseMock.productos) {

            System.out.println(
                    p.getId() + " - "
                    + p.getNombre() + " - $"
                    + p.getPrecio()
            );

        }

    }

    public Producto buscarPorId(Long id) {

        for (Producto p : DatabaseMock.productos) {

            if (p.getId().equals(id)) {
                return p;
            }

        }

        return null;
    }

}