package com.academico.infrastructure.adapter.out.factory;

import com.academico.domain.repository.EstudianteRepository;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryJson;
import com.academico.infrastructure.adapter.out.persistence.EstudianteRepositoryPostgres;

public class RepositoryFactory {

    private final String tipoRepositorio;

    public RepositoryFactory() {
        this(System.getenv().getOrDefault("REPOSITORY_TYPE", "POSTGRES"));
    }

    public RepositoryFactory(String tipoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
    }

    public EstudianteRepository crear() {
        if ("JSON".equalsIgnoreCase(tipoRepositorio)) {
            return new EstudianteRepositoryJson();
        }
        if ("POSTGRES".equalsIgnoreCase(tipoRepositorio)) {
            return new EstudianteRepositoryPostgres();
        }
        throw new IllegalArgumentException(
                "Tipo de repositorio no soportado: " + tipoRepositorio
        );
    }
}