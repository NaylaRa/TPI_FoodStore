/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Usuario;

public class UsuarioService {

    private static Long idCounter = 1L;

    public void crear(Usuario usuario) {
        usuario.setId(idCounter++);
        DatabaseMock.usuarios.add(usuario);
    }

    public void listar() {
        for (Usuario u : DatabaseMock.usuarios) {
            System.out.println(
                    u.getId() + " - "
                    + u.getNombre() + " "
                    + u.getApellido()
            );
        }
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : DatabaseMock.usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }
}