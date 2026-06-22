/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.DatabaseMock;
import integrado.prog2.entidades.Usuario;
import integrado.prog2.exception.ReglaNegocioException;

public class UsuarioService {

    private static Long idCounter = 1L;

    public void crear(Usuario usuario) throws ReglaNegocioException {
        for (Usuario u : DatabaseMock.usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(usuario.getMail())) {
                throw new ReglaNegocioException("Error: El mail " + usuario.getMail() + " ya se encuentra registrado.");
            }
        }
        usuario.setId(idCounter++);
        DatabaseMock.usuarios.add(usuario);
    }

    public void listar() {
        boolean hayRegistros = false;
        for (Usuario u : DatabaseMock.usuarios) {
            if (!u.isEliminado()) {
                System.out.println(u.toString());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay usuarios cargados.");
        }
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : DatabaseMock.usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        return null;
    }
}