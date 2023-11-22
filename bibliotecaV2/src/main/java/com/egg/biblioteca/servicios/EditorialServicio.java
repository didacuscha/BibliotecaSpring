package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        List <Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException {
        validar(nombre);
        System.out.println("id:" + id);
        System.out.println("nombre:" + nombre);
        // la respuesta es = a findById(nombre) en vez de id ya que de alguna manera se invirtio el orden de los parametros
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        System.out.println("respuesta: " + respuesta);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            // editorial.setNombre() se le pasa el id porque de alguna manera se invirtieron en el metodo
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    @Transactional(readOnly = true)
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }

    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre de la editorial no puede ser nulo o estar vacio");
        }
    }
}
