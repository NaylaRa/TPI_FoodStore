/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Pedido;

public class PedidoService {

    private static Long idCounter = 1L;

    public void crear(Pedido pedido) {

        pedido.setId(idCounter++);
        DatabaseMock.pedidos.add(pedido);

    }

    public void listar() {

        for (Pedido p : DatabaseMock.pedidos) {

            System.out.println("\nPedido ID: " + p.getId());

            System.out.println(
                    "Cliente: "
                    + p.getUsuario().getNombre()
                    + " "
                    + p.getUsuario().getApellido()
            );

            System.out.println("Estado: " + p.getEstado());
            System.out.println("Forma de Pago: " + p.getFormaPago());

            System.out.println("\nProductos:");

            for (var detalle : p.getDetalles()) {

                System.out.println(
                        "- "
                        + detalle.getProducto().getNombre()
                        + " x"
                        + detalle.getCantidad()
                        + " = $"
                        + detalle.getSubtotal()
                );
            }

            System.out.println("\nTotal: $" + p.getTotal());
        }
    }

    public Pedido buscarPorId(Long id) {

        for (Pedido p : DatabaseMock.pedidos) {

            if (p.getId().equals(id)) {
                return p;
            }
        }

        return null;
    }
}