/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Pedido;
import integrado.prog2.exception.ReglaNegocioException;

public class PedidoService {

    private static Long idCounter = 1L;

    public void crear(Pedido pedido) throws ReglaNegocioException {
        if (pedido.getUsuario() == null || pedido.getUsuario().isEliminado()) {
            throw new ReglaNegocioException("Error: No se puede crear un pedido sin un usuario valido asociado.");
        }
        pedido.setId(idCounter++);
        DatabaseMock.pedidos.add(pedido);
    }

    public void listar() {
        boolean hayRegistros = false;
        for (Pedido p : DatabaseMock.pedidos) {
            if (!p.isEliminado()) {
                System.out.println("\nPedido ID: " + p.getId());
                System.out.println("Cliente: " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido());
                System.out.println("Estado: " + p.getEstado());
                System.out.println("Forma de Pago: " + p.getFormaPago());
                System.out.println("Fecha: " + p.getFecha());
                System.out.println("\nProductos:");
                for (var detalle : p.getDetalles()) {
                    System.out.println("- " + detalle.getProducto().getNombre() + " x" + detalle.getCantidad() + " = $" + detalle.getSubtotal());
                }
                System.out.println("Total: $" + p.getTotal());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay pedidos registrados.");
        }
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido p : DatabaseMock.pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }
}