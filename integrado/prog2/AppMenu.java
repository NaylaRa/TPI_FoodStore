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

        int opcion;

        do {
            System.out.println("\n======================");
            System.out.println("       MENU PRINCIPAL");
            System.out.println("======================");
            System.out.println("1 - Crear categoria");
            System.out.println("2 - Listar categorias");
            System.out.println("3 - Crear producto");
            System.out.println("4 - Listar productos");
            System.out.println("5 - Crear usuario");
            System.out.println("6 - Listar usuarios");
            System.out.println("7 - Crear pedido");
            System.out.println("8 - Listar pedidos");
            System.out.println("--- GESTION AVANZADA ---");
            System.out.println("9 - Editar Producto (Precio/Stock)");
            System.out.println("10 - Eliminar Producto (Baja Logica)");
            System.out.println("11 - Eliminar Usuario (Baja Logica)");
            System.out.println("12 - Cambiar Estado de Pedido");
            System.out.println("13 - Eliminar Pedido (Baja Logica)");
            System.out.println("0 - Salir");
            System.out.print("Opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    try {
                        System.out.print("Nombre categoria: ");
                        String nombreCat = sc.nextLine();
                        System.out.print("Descripcion: ");
                        String descCat = sc.nextLine();
                        Categoria categoria = new Categoria(nombreCat, descCat);
                        categoriaService.crear(categoria);
                        System.out.println("Categoria creada exitosamente.");
                    } catch (ReglaNegocioException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n=== CATEGORIAS ===");
                    categoriaService.listar();
                    break;

                case 3:
                    try {
                        if (DatabaseMock.categorias.isEmpty()) {
                            System.out.println("Error: Debe crear una categoria primero.");
                            break;
                        }
                        System.out.print("Nombre producto: ");
                        String nombreProd = sc.nextLine();
                        System.out.print("Descripcion: ");
                        String descProd = sc.nextLine();
                        System.out.print("Precio: ");
                        double precio = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Stock: ");
                        Integer stock = sc.nextInt();
                        sc.nextLine();

                        System.out.println("\nCategorias disponibles:");
                        categoriaService.listar();
                        System.out.print("Ingrese ID de categoria: ");
                        Long idCategoria = sc.nextLong();
                        sc.nextLine();

                        Categoria cat = categoriaService.buscarPorId(idCategoria);
                        if (cat == null) {
                            System.out.println("Error: Categoria inexistente o dada de baja.");
                            break;
                        }

                        Producto producto = new Producto(
                                nombreProd, precio, descProd, stock, "sin-imagen.jpg", true, cat
                        );
                        productoService.crear(producto);
                        System.out.println("Producto creado exitosamente.");
                    } catch (ReglaNegocioException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\n=== PRODUCTOS ===");
                    productoService.listar();
                    break;

                case 5:
                    try {
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = sc.nextLine();
                        System.out.print("Mail: ");
                        String mail = sc.nextLine();
                        System.out.print("Celular: ");
                        String celular = sc.nextLine();
                        System.out.print("Contraseña: ");
                        String contrasena = sc.nextLine();

                        Usuario usuario = new Usuario(
                                nombre, apellido, mail, celular, contrasena, Rol.USUARIO
                        );
                        usuarioService.crear(usuario);
                        System.out.println("Usuario creado exitosamente.");
                    } catch (ReglaNegocioException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("\n=== USUARIOS ===");
                    usuarioService.listar();
                    break;

                case 7:
                    try {
                        if (DatabaseMock.usuarios.isEmpty() || DatabaseMock.productos.isEmpty()) {
                            System.out.println("Error: Necesita al menos un usuario y un producto cargados para armar un pedido.");
                            break;
                        }

                        System.out.println("\n=== SELECCIONAR CLIENTE ===");
                        usuarioService.listar();
                        System.out.print("Ingrese ID del usuario: ");
                        Long idUsu = sc.nextLong();
                        sc.nextLine();

                        Usuario usu = usuarioService.buscarPorId(idUsu);
                        if (usu == null) {
                            System.out.println("Error: Usuario inexistente o dado de baja.");
                            break;
                        }

                        Pedido pedido = new Pedido();
                        pedido.setFecha(LocalDate.now());
                        pedido.setEstado(Estado.PENDIENTE);
                        pedido.setUsuario(usu);

                        System.out.println("\n=== FORMA DE PAGO ===");
                        System.out.println("1 - EFECTIVO");
                        System.out.println("2 - TRANSFERENCIA");
                        System.out.println("3 - TARJETA");
                        System.out.print("Elija opcion: ");
                        int opPago = sc.nextInt();
                        sc.nextLine();

                        if (opPago == 1) pedido.setFormaPago(FormaPago.EFECTIVO);
                        else if (opPago == 2) pedido.setFormaPago(FormaPago.TRANSFERENCIA);
                        else pedido.setFormaPago(FormaPago.TARJETA);

                        int seguir;
                        do {
                            System.out.println("\n=== AGREGAR PRODUCTOS ===");
                            productoService.listar();
                            System.out.print("Ingrese ID del producto: ");
                            Long idProd = sc.nextLong();
                            sc.nextLine();

                            Producto prod = productoService.buscarPorId(idProd);
                            if (prod != null) {
                                System.out.print("Cantidad a llevar: ");
                                int cant = sc.nextInt();
                                sc.nextLine();
                                
                                // Validación rápida en UI para evitar cantidades inválidas
                                if (cant <= 0) {
                                    System.out.println("Error: Al crear un detalle, la cantidad debe ser mayor a 0.");
                                    continue;
                                }
                                
                                pedido.addDetallePedido(cant, prod.getPrecio() * cant, prod);
                                System.out.println("¡Producto sumado al carrito!");
                            } else {
                                System.out.println("Error: Producto inexistente o dado de baja.");
                            }

                            System.out.print("¿Desea agregar otro producto al pedido? (1-Si / 0-No): ");
                            seguir = sc.nextInt();
                            sc.nextLine();

                        } while (seguir != 0);

                        pedido.calcularTotal();
                        pedidoService.crear(pedido);
                        System.out.println("\nPedido registrado exitosamente.");
                        System.out.println("Total a cobrar: $" + pedido.getTotal());
                    } catch (ReglaNegocioException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    System.out.println("\n=== PEDIDOS REGISTRADOS ===");
                    pedidoService.listar();
                    break;

                case 9:
                    System.out.println("\n=== EDITAR PRODUCTO ===");
                    productoService.listar();
                    System.out.print("Ingrese ID del producto a editar: ");
                    Long idProdEdit = sc.nextLong();
                    sc.nextLine();
                    Producto prodEdit = productoService.buscarPorId(idProdEdit);
                    
                    if (prodEdit != null) {
                        System.out.print("Nuevo precio (actual: $" + prodEdit.getPrecio() + "): ");
                        prodEdit.setPrecio(sc.nextDouble());
                        sc.nextLine();
                        System.out.print("Nuevo stock (actual: " + prodEdit.getStock() + "): ");
                        prodEdit.setStock(sc.nextInt());
                        sc.nextLine();
                        System.out.println("Producto actualizado exitosamente.");
                    } else {
                        System.out.println("Error: Producto inexistente o dado de baja.");
                    }
                    break;

                case 10:
                    System.out.println("\n=== ELIMINAR PRODUCTO ===");
                    productoService.listar();
                    System.out.print("Ingrese ID del producto a dar de baja: ");
                    Long idProdDel = sc.nextLong();
                    sc.nextLine();
                    Producto prodDel = productoService.buscarPorId(idProdDel);
                    
                    if (prodDel != null) {
                        prodDel.setEliminado(true);
                        System.out.println("Producto dado de baja lógicamente.");
                    } else {
                        System.out.println("Error: Producto inexistente o ya dado de baja.");
                    }
                    break;

                case 11:
                    System.out.println("\n=== ELIMINAR USUARIO ===");
                    usuarioService.listar();
                    System.out.print("Ingrese ID del usuario a dar de baja: ");
                    Long idUsuDel = sc.nextLong();
                    sc.nextLine();
                    Usuario usuDel = usuarioService.buscarPorId(idUsuDel);
                    
                    if (usuDel != null) {
                        usuDel.setEliminado(true);
                        System.out.println("Usuario dado de baja lógicamente.");
                    } else {
                        System.out.println("Error: Usuario inexistente o ya dado de baja.");
                    }
                    break;

                case 12:
                    System.out.println("\n=== CAMBIAR ESTADO DE PEDIDO ===");
                    pedidoService.listar();
                    System.out.print("Ingrese ID del pedido a modificar: ");
                    Long idPedEst = sc.nextLong();
                    sc.nextLine();
                    Pedido pedEst = pedidoService.buscarPorId(idPedEst);
                    
                    if (pedEst != null) {
                        System.out.println("1 - PENDIENTE | 2 - CONFIRMADO | 3 - TERMINADO | 4 - CANCELADO");
                        System.out.print("Elija el nuevo estado: ");
                        int opEst = sc.nextInt();
                        sc.nextLine();
                        if (opEst == 1) pedEst.setEstado(Estado.PENDIENTE);
                        else if (opEst == 2) pedEst.setEstado(Estado.CONFIRMADO);
                        else if (opEst == 3) pedEst.setEstado(Estado.TERMINADO);
                        else if (opEst == 4) pedEst.setEstado(Estado.CANCELADO);
                        System.out.println("Estado del pedido actualizado.");
                    } else {
                        System.out.println("Error: Pedido inexistente o dado de baja.");
                    }
                    break;

                case 13:
                    System.out.println("\n=== ELIMINAR PEDIDO ===");
                    pedidoService.listar();
                    System.out.print("Ingrese ID del pedido a dar de baja: ");
                    Long idPedDel = sc.nextLong();
                    sc.nextLine();
                    Pedido pedDel = pedidoService.buscarPorId(idPedDel);
                    
                    if (pedDel != null) {
                        pedDel.setEliminado(true);
                        System.out.println("Pedido dado de baja lógicamente.");
                    } else {
                        System.out.println("Error: Pedido inexistente o ya dado de baja.");
                    }
                    break;

                case 0:
                    System.out.println("Cerrando el sistema... ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 0);

        sc.close();
    }
}