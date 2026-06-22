/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2;

import integrado.prog2.entidades.*;
import integrado.prog2.enums.*;
import integrado.prog2.service.*;
import java.util.Scanner;

public class AppMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CategoriaService catS = new CategoriaService();
        ProductoService prodS = new ProductoService();
        UsuarioService usuS = new UsuarioService();
        PedidoService pedS = new PedidoService();

        try {
            Categoria bebidas = new Categoria("Bebidas", "Gaseosas");
            catS.crear(bebidas);
            usuS.crear(new Usuario("Usuario", "Sistema", "admin@foodstore.com", "123", "pass", Rol.USUARIO));
            prodS.crear(new Producto("Coca Cola", 1500.0, "Gaseosa", 20, "coca.jpg", true, bebidas));
        } catch (Exception e) { System.out.println("Error inicial: " + e.getMessage()); }

        int op = -1;
        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Listar Pedidos");
            System.out.println("5. Crear Pedido");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            
            if (sc.hasNextInt()) { op = sc.nextInt(); sc.nextLine(); } else { sc.nextLine(); op = -1; }

            switch (op) {
                case 1: menuCRUD(sc, "CATEGORÍAS", catS, null, null, null); break;
                case 2: menuCRUD(sc, "PRODUCTOS", null, prodS, null, null); break;
                case 3: menuCRUD(sc, "USUARIOS", null, null, usuS, null); break;
                case 4: pedS.listar(); break;
                case 5: crearPedido(sc, usuS, prodS, pedS); break;
            }
        } while (op != 0);
        sc.close();
    }

    private static void crearPedido(Scanner sc, UsuarioService usuS, ProductoService prodS, PedidoService pedS) {
        usuS.listar();
        System.out.print("ID Usuario: ");
        Long id = sc.nextLong(); sc.nextLine();
        Usuario u = usuS.buscarPorId(id);
        if (u == null) { System.out.println("Usuario inválido."); return; }

        Pedido p = new Pedido(java.time.LocalDate.now(), Estado.PENDIENTE, FormaPago.EFECTIVO, u);
        String seguir = "S";
        do {
            prodS.listar();
            System.out.print("ID Producto: ");
            Long idP = sc.nextLong(); sc.nextLine();
            Producto prod = prodS.buscarPorId(idP);
            if (prod == null) { System.out.println("Producto inexistente."); continue; }
            System.out.print("Cantidad: ");
            int cant = sc.nextInt(); sc.nextLine();
            
            p.addDetallePedido(cant, prod);
            
            System.out.print("¿Agregar otro? (S/N): ");
            seguir = sc.nextLine();
        } while (seguir.equalsIgnoreCase("S"));

        try { 
            pedS.crear(p); 
            System.out.println("Pedido creado con éxito."); 
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage()); 
        }
    }

    private static void menuCRUD(Scanner sc, String titulo, CategoriaService cs, ProductoService ps, UsuarioService us, PedidoService pedS) {
        int sub = -1;
        do {
            System.out.println("\n=== " + titulo + " ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            sub = sc.nextInt(); sc.nextLine();
            switch (sub) {
                case 1: 
                    if(cs!=null) cs.listar(); 
                    else if(ps!=null) ps.listar(); 
                    else if(us!=null) us.listar(); 
                    break;
                case 2:
                    try {
                        if (cs != null) { 
                            System.out.print("Nombre: "); String n = sc.nextLine(); 
                            cs.crear(new Categoria(n, "Desc")); 
                        }
                        else if (ps != null) { 
                            System.out.print("Nombre: "); String n = sc.nextLine(); 
                            System.out.print("Precio: "); Double p = sc.nextDouble(); sc.nextLine();
                            System.out.print("Stock: "); Integer s = sc.nextInt(); sc.nextLine();
                            ps.crear(new Producto(n, p, "Desc", s, "img", true, new Categoria("General", "Gral"))); 
                        }
                        else if (us != null) { 
                            System.out.print("Nombre: "); String n = sc.nextLine(); 
                            System.out.print("Apellido: "); String a = sc.nextLine(); 
                            System.out.print("Mail: "); String m = sc.nextLine(); 
                            us.crear(new Usuario(n, a, m, "123", "pass", Rol.USUARIO)); 
                        }
                        System.out.println("¡Creado con éxito!");
                    } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
                    break;
            }
        } while (sub != 0);
    }
}