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

        // Carga inicial para que tu sistema no esté vacío al ejecutarlo
        try {
            catS.crear(new Categoria("Bebidas", "Gaseosas"));
            usuS.crear(new Usuario("Usuario", "Sistema", "admin@foodstore.com", "123", "pass", Rol.USUARIO));
        } catch (Exception e) {}

        int op = -1;
        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías\n2. Productos\n3. Usuarios\n4. Pedidos\n0. Salir");
            System.out.print("Seleccione: ");
            
            if (sc.hasNextInt()) {
                op = sc.nextInt(); sc.nextLine();
            } else { sc.nextLine(); op = -1; }

            switch (op) {
                case 1: menuCRUD(sc, "CATEGORÍAS", catS, null, null, null); break;
                case 2: menuCRUD(sc, "PRODUCTOS", null, prodS, null, null); break;
                case 3: menuCRUD(sc, "USUARIOS", null, null, usuS, null); break;
                case 4: menuCRUD(sc, "PEDIDOS", null, null, null, pedS); break;
            }
        } while (op != 0);
        sc.close();
    }

    private static void menuCRUD(Scanner sc, String titulo, CategoriaService cs, ProductoService ps, UsuarioService us, PedidoService pedS) {
        int sub = -1;
        do {
            System.out.println("\n=== " + titulo + " ===");
            System.out.println("1. Listar\n2. Crear\n0. Volver");
            System.out.print("Seleccione: ");
            sub = sc.nextInt(); sc.nextLine();
            
            switch (sub) {
                case 1:
                    if (cs != null) cs.listar();
                    else if (ps != null) ps.listar();
                    else if (us != null) us.listar();
                    else if (pedS != null) pedS.listar();
                    break;
                case 2:
                    try {
                        if (cs != null) {
                            System.out.print("Nombre: "); String n = sc.nextLine();
                            System.out.print("Desc: "); String d = sc.nextLine();
                            cs.crear(new Categoria(n, d));
                        } else if (ps != null) {
                            System.out.print("Nombre: "); String n = sc.nextLine();
                            System.out.print("Precio: "); double p = sc.nextDouble(); sc.nextLine();
                            System.out.print("Stock: "); int s = sc.nextInt(); sc.nextLine();
                            ps.crear(new Producto(n, p, "Desc", s, "img", true, null));
                        } else if (us != null) {
                            System.out.print("Nombre: "); String nom = sc.nextLine();
                            System.out.print("Apellido: "); String ape = sc.nextLine();
                            System.out.print("Mail: "); String mail = sc.nextLine();
                            us.crear(new Usuario(nom, ape, mail, "123", "pass", Rol.USUARIO));
                        }
                        System.out.println("¡Creado con éxito!");
                    } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
                    break;
            }
        } while (sub != 0);
    }
}