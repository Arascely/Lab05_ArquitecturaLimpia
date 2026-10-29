
# Laboratorio de Arquitectura de Software

> **Asignatura:** IS-488 Arquitectura de Software  
> **Semestre:** 2025-I  
> **Docente:** Ing. Luis Adderlin Ruiz Huaman

## Sesión: 05 – Diseño inicial en 3 capas y N capas

## Sistema de Gestion Academica

### Estructura del Repositorio y Ramas

```text
SistemaGestionAcademica
├── Ramas de Desarrollo (Patrones)
│   ├── abstract-factory
│   └── iterator
└── README.md
```

## 1. Rama Iterator

La rama `iterator` conserva la arquitectura hexagonal y agrega el patron
Iterator para recorrer estudiantes sin exponer la estructura interna usada
por el repositorio.

## 1.2. Estructura del sistema

### Motor de arranque

- `Main` es el punto de entrada. Solicita el repositorio a
	`RepositoryFactory` y crea `EstudianteService` y `EstudianteUI`.

### Capa de Presentacion

- `EstudianteUI` interactua con el usuario mediante consola y `Scanner`.
	Sus operaciones `mostrarMenu`, `registrar` y `listar` utilizan solamente
	`EstudianteUseCase`, nunca una conexion a la base de datos.

### Capa de Logica de Negocio

- `EstudianteUseCase` define las operaciones `registrar`, `listar`,
	`actualizar` y `eliminar`.
- `EstudianteService` implementa ese contrato y delega el almacenamiento en
	`EstudianteRepository`.

### Capa de Creacion

- `RepositoryFactory` centraliza `crear()` y decide si devuelve
	`EstudianteRepositoryJson` o `EstudianteRepositoryPostgres` bajo el tipo
	`EstudianteRepository`.

### Patron Iterator

- `EstudianteIterator` implementa `Iterator<Estudiante>` y controla la
	posicion actual del recorrido mediante `hasNext()` y `next()`.
- `EstudianteService` obtiene la lista desde `EstudianteRepository` y la
	entrega envuelta en `EstudianteIterator`.
- `EstudianteUI` recorre los estudiantes usando el iterador, sin depender de
	la implementacion concreta de la lista.

### Capa de Acceso a Datos

- `EstudianteRepository` estandariza `guardar`, `listar`, `buscarPorId`,
	`actualizar` y `eliminar`.
- `EstudianteRepositoryJson` persiste en archivos locales con `Path` y Gson.
- `EstudianteRepositoryPostgres` persiste en PostgreSQL mediante URL, usuario
	y password.

### Entidad de Dominio

- `Estudiante` contiene `id`, `nombre` y `correo`, con constructor y getters.
	No expone setters para conservar el modelo como entidad de solo lectura.

## Cambios realizados para Iterator

1. Se agrego `EstudianteIterator` como iterador concreto de estudiantes.
2. Se cambio `EstudianteUseCase.listar()` para devolver `Iterator<Estudiante>`.
3. Se actualizo `EstudianteService` para envolver la lista del repositorio en
	`EstudianteIterator`.
4. Se actualizo `EstudianteUI.listar()` para usar `hasNext()` y `next()`.
5. Se conservaron sin cambios los repositorios, la fabrica, `Main`, las
	operaciones de escritura y la conexion con PostgreSQL.

## ¿Por que Iterator?

El patrón Iterator fue adecuado porque el sistema necesita recorrer los
estudiantes, pero la interfaz de usuario no debería conocer si los datos
provienen de una lista, un archivo JSON o PostgreSQL. El iterador separa la
forma de recorrer los datos de la estructura que los almacena.

- Reduce el acoplamiento entre `EstudianteUI` y la colección concreta de
	estudiantes.
- Mantener `EstudianteService` como intermediario entre la UI y el repositorio.
- Cambiar la estructura interna de almacenamiento sin modificar el código de
	recorrido de la interfaz.
- Centralizar el avance del recorrido en `hasNext()` y `next()`.
- Agregar posteriormente otros recorridos, como filtrar o recorrer por orden,
	creando iteradores adicionales sin modificar los repositorios.

De esta manera, solo se modificaron el contrato de listado, el servicio y la operación `listar()` de la UI. Las operaciones de registro, actualización y eliminación, la persistencia y la
fábrica continuaron funcionando como antes.
