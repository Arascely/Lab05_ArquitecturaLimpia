
# Laboratorio de Arquitectura de Software

> **Asignatura:** IS-488 Arquitectura de Software  
> **Semestre:** 2025-I  
> **Docente:** Ing. Luis Adderlin Ruiz Huaman

## Sesión: 05 – Diseño inicial en 3 capas y N capas

## Sistema de Gestion Academica

### Estructura del trabajo

### Estructura del Repositorio y Ramas

```text
SistemaGestionAcademica
├── Ramas de Desarrollo (Patrones)
│   ├── abstract-factory
│   └── iterator
└── README.md
```

## 1. Rama Abstract Factory

La rama `AbstractFactory` conserva la arquitectura hexagonal y agrega una
fabrica central para crear el repositorio de estudiantes sin que la clase de
entrada conozca directamente la implementacion de persistencia.

## 1.2. Estructura del sistema

#### Motor de arranque

- `Main` es el punto de entrada. Solicita el repositorio a
	`RepositoryFactory` y crea `EstudianteService` y `EstudianteUI`.

### Capa de Presentacion

- `EstudianteUI` interactua con el usuario mediante consola y `Scanner`.
	Sus operaciones `mostrarMenu`, `registrar` y `listar` utilizan solamente
	`EstudianteUseCase`, nunca una conexion a la base de datos.

#### Capa de Logica de Negocio

- `EstudianteUseCase` define las operaciones `registrar`, `listar`,
	`actualizar` y `eliminar`.
- `EstudianteService` implementa ese contrato y delega el almacenamiento en
	`EstudianteRepository`.

#### Capa de Creacion

- `RepositoryFactory` centraliza `crear()` y decide si devuelve
	`EstudianteRepositoryJson` o `EstudianteRepositoryPostgres` bajo el tipo
	`EstudianteRepository`.

#### Capa de Acceso a Datos

- `EstudianteRepository` estandariza `guardar`, `listar`, `buscarPorId`,
	`actualizar` y `eliminar`.
- `EstudianteRepositoryJson` persiste en archivos locales con `Path` y Gson.
- `EstudianteRepositoryPostgres` persiste en PostgreSQL mediante URL, usuario
	y password.

#### Entidad de Dominio

- `Estudiante` contiene `id`, `nombre` y `correo`, con constructor y getters.
	No expone setters para conservar el modelo como entidad de solo lectura.

### Cambios realizados

1. Se actualizo `RepositoryFactory` para centralizar la creacion de los
   repositorios JSON y PostgreSQL mediante `crear()`.
2. Se actualizo `Main` para solicitar el repositorio a `RepositoryFactory`.
3. Se mantuvo PostgreSQL como comportamiento predeterminado y se agrego la
   seleccion de JSON mediante `REPOSITORY_TYPE`.
4. Se ajusto `Estudiante` para exponer atributos mediante constructor y
   getters, sin setters.
5. Se documento en este archivo la estructura y el uso de la fabrica.

## Verificacion del funcionamiento del sistema
<img width="891" height="420" alt="Captura de pantalla 2026-10-28 232052" src="https://github.com/user-attachments/assets/cabaa388-18fa-4b5f-9275-3ac41ceeea95" />

