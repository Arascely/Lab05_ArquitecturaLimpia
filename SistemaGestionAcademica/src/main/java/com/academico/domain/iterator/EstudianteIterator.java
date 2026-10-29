package com.academico.domain.iterator;

import com.academico.domain.model.Estudiante;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class EstudianteIterator implements Iterator<Estudiante> {

    private final List<Estudiante> estudiantes;
    private int posicion;

    public EstudianteIterator(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    @Override
    public boolean hasNext() {
        return posicion < estudiantes.size();
    }

    @Override
    public Estudiante next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más estudiantes.");
        }
        return estudiantes.get(posicion++);
    }
}