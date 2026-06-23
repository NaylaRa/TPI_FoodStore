/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entidades;

public class DetallePedido extends Base {

    private Integer cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido() {}

    public DetallePedido(Integer cantidad, Double subtotal, Producto producto) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public Integer getCantidad() { return cantidad; }
    public Double getSubtotal() { return subtotal; }
    public Producto getProducto() { return producto; }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " = $" + subtotal;
    }
}