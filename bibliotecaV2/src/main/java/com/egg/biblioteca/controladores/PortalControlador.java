package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index(HttpSession session) {
        Usuario logueado =  (Usuario) session.getAttribute("usuariosession");

        if (logueado != null) {
            if ("ADMIN".equals(logueado.getRol().toString())) {
                return "redirect:/admin/dashboard";
            } else if ("USER".equals(logueado.getRol().toString())) {
                return "redirect:/inicio";
            }
        }

        return "index.html";

    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo, MultipartFile archivo) {

        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente");

            return "index.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos");
        }
        return "login.html";
    }


    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado =  (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }


    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }


    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
                             @RequestParam String password, @RequestParam String password2, ModelMap modelo, HttpSession session) {

        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            Usuario logueado =  (Usuario) session.getAttribute("usuariosession");

            if (logueado.getRol().toString().equals("ADMIN")) {
                return "redirect:/admin/dashboard";
            }

            return "inicio.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin/modificar/{id}")
    public String Adminactualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre, @RequestParam String email,
                             @RequestParam String password, @RequestParam String password2, ModelMap modelo, HttpSession session) {

        try {
            usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            Usuario logueado =  (Usuario) session.getAttribute("usuariosession");

            if (logueado.getRol().toString().equals("ADMIN")) {
                return "redirect:/admin/dashboard";
            }

            return "inicio.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";
        }

    }
}
