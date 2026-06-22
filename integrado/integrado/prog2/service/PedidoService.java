/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.DetallePedido;
import integrado.prog2.entidades.Pedido;
import integrado.prog2.entidades.Producto;
import integrado.prog2.exception.ReglaNegocioException;

public class PedidoService {
    private static Long idCounter = 1L;

    public void crear(Pedido pedido) throws ReglaNegocioException {
        if (pedido.getUsuario() == null || pedido.getUsuario().isEliminado()) {
            throw new ReglaNegocioException("Error: Usuario inválido.");
        }
        if (pedido.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("El pedido debe tener al menos un producto.");
        }
        for (DetallePedido detalle : pedido.getDetalles()) {
            if (detalle.getCantidad() > detalle.getProducto().getStock()) {
                throw new ReglaNegocioException("Stock insuficiente para: " + detalle.getProducto().getNombre());
            }
        }
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto p = detalle.getProducto();
            p.setStock(p.getStock() - detalle.getCantidad());
        }
        pedido.calcularTotal();
        pedido.setId(idCounter++);
        DatabaseMock.pedidos.add(pedido);
    }

    public void listar() {
        boolean hayRegistros = false;
        System.out.println("\n=== LISTADO DE PEDIDOS ===");
        for (Pedido p : DatabaseMock.pedidos) {
            if (p.getId() != null && !p.isEliminado()) {
                System.out.println(p);
                for (DetallePedido detalle : p.getDetalles()) { System.out.println("  " + detalle); }
                hayRegistros = true;
            }
        }
        if (!hayRegistros) System.out.println("No hay pedidos registrados.");
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido p : DatabaseMock.pedidos) {
            if (p.getId() != null && p.getId().equals(id) && !p.isEliminado()) return p;
        }
        return null;
    }
}