package com.academico.application.port.in;
import com.academico.domain.model.Estudiante;

import java.util.Iterator;

public interface EstudianteUseCase {

    public void registrar (Estudiante estudiante);

    public Iterator<Estudiante> listar();

    public boolean  actualizar(Estudiante estudiante);

    public boolean eliminar (int id);
}
