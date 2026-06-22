/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Producto;
import integrado.prog2.exception.ReglaNegocioException;

public class ProductoService {

    private static Long idCounter = 1L;

    public void crear(Producto producto) throws ReglaNegocioException {
        if (producto.getPrecio() < 0) {
            throw new ReglaNegocioException("Error: El precio no puede ser menor a 0.");
        }
        if (producto.getStock() < 0) {
            throw new ReglaNegocioException("Error: El stock no puede ser menor a 0.");
        }
        producto.setId(idCounter++);
        DatabaseMock.productos.add(producto);
    }

    public void listar() {
        boolean hayRegistros = false;
        for (Producto p : DatabaseMock.productos) {
            if (p.getId() != null && !p.isEliminado()) {
                System.out.println(p.toString());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay productos cargados.");
        }
    }

    public Producto buscarPorId(Long id) {
        for (Producto p : DatabaseMock.productos) {
            if (p.getId() != null && p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }
}