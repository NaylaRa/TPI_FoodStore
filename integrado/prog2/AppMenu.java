/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2;

import integrado.prog2.entidades.Categoria;
import integrado.prog2.entidades.Pedido;
import integrado.prog2.entidades.Producto;
import integrado.prog2.entidades.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.ReglaNegocioException;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;

import java.time.LocalDate;
import java.util.Scanner;

public class AppMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService();
        int opcion = -1;

        do {
            System.out.println("\n======================\n       MENU PRINCIPAL\n======================");
            System.out.println("1-Cat 2-L.Cat 3-Prod 4-L.Prod 5-Usu 6-L.Usu 7-Ped 8-L.Ped 0-Salir");
            System.out.print("Opcion: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Nombre: "); String n = sc.nextLine();
                        System.out.print("Desc: "); String d = sc.nextLine();
                        categoriaService.crear(new Categoria(n, d));
                        System.out.println("Creado.");
                    } catch (ReglaNegocioException e) { 
                        System.out.println(e.getMessage()); 
                    }
                    break;
                case 2: 
                    categoriaService.listar(); 
                    break;
                case 3:
                    try {
                        System.out.print("Nombre: "); String np = sc.nextLine();
                        System.out.print("Precio: "); double p = sc.nextDouble(); sc.nextLine();
                        System.out.print("Stock: "); int s = sc.nextInt(); sc.nextLine();
                        categoriaService.listar();
                        System.out.print("ID Cat: "); Long idC = sc.nextLong(); sc.nextLine();
                        Categoria cat = categoriaService.buscarPorId(idC);
                        if(cat == null) throw new ReglaNegocioException("Cat no existe.");
                        productoService.crear(new Producto(np, p, "Desc", s, "img", true, cat));
                        System.out.println("Creado.");
                    } catch (ReglaNegocioException e) { 
                        System.out.println(e.getMessage()); 
                    }
                    break;
                case 4: 
                    productoService.listar(); 
                    break;
                case 5:
                    try {
                        System.out.print("Nombre: "); String nom = sc.nextLine();
                        System.out.print("Apellido: "); String ape = sc.nextLine();
                        if (nom.isEmpty() || ape.isEmpty()) {
                            throw new ReglaNegocioException("Error: Nombre y Apellido son obligatorios.");
                        }
                        System.out.print("Mail: "); String mail = sc.nextLine();
                        usuarioService.crear(new Usuario(nom, ape, mail, "123", "pass", Rol.USUARIO));
                        System.out.println("Creado exitosamente.");
                    } catch (ReglaNegocioException e) { 
                        System.out.println(e.getMessage()); 
                    }
                    break;
                case 6: 
                    usuarioService.listar(); 
                    break;
                case 7:
                    try {
                        usuarioService.listar();
                        System.out.print("ID Usuario: "); Long idU = sc.nextLong(); sc.nextLine();
                        Usuario u = usuarioService.buscarPorId(idU);
                        if(u == null) throw new ReglaNegocioException("Usuario no existe.");
                        Pedido ped = new Pedido(); ped.setUsuario(u); ped.setFecha(LocalDate.now());
                        ped.setEstado(Estado.PENDIENTE); ped.setFormaPago(FormaPago.EFECTIVO);
                        int seguir;
                        do {
                            productoService.listar();
                            System.out.print("ID Prod: "); Long idP = sc.nextLong(); sc.nextLine();
                            Producto pr = productoService.buscarPorId(idP);
                            if(pr == null) throw new ReglaNegocioException("Prod no existe.");
                            System.out.print("Cant: "); int c = sc.nextInt(); sc.nextLine();
                            ped.addDetallePedido(c, pr.getPrecio() * c, pr);
                            System.out.print("Otro? 1-Si 0-No: "); seguir = sc.nextInt(); sc.nextLine();
                        } while(seguir != 0);
                        ped.calcularTotal(); pedidoService.crear(ped);
                        System.out.println("Pedido creado. Total: $" + ped.getTotal());
                    } catch (ReglaNegocioException e) { 
                        System.out.println(e.getMessage()); 
                    }
                    break;
                case 8: 
                    pedidoService.listar(); 
                    break;
                case 0: 
                    System.out.println("Saliendo..."); 
                    break;
                default: 
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
