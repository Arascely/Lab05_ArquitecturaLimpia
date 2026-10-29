
# Laboratorio de Arquitectura de Software

> **Asignatura:** IS-488 Arquitectura de Software  
> **Semestre:** 2025-I  
> **Docente:** Ing. Luis Adderlin Ruiz Huaman
> **Estudiante:** Rodriguez Quispe Grissel Arascely
> **Fecha:** 28/10/2026

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
### Descripción de Componentes

| Rama (Branch) | Descripción del Patrón Implementado |
| :--- | :--- |
| **`feature/abstract-factory`** | Implementación del patrón creacional *Abstract Factory* para la gestión de repositorios (JSON/Postgres). |
| **`feature/iterator`** | Implementación del patrón de comportamiento *Iterator* para el recorrido de colecciones. |
| **`main`** | Rama principal estable del sistema académico. |

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

## 2. Rama Iterator

La rama `iterator` conserva la arquitectura hexagonal y agrega el patron
Iterator para recorrer estudiantes sin exponer la estructura interna usada
por el repositorio.

### 2.1. Estructura del sistema

#### Motor de arranque

- `Main` es el punto de entrada. Solicita el repositorio a
	`RepositoryFactory` y crea `EstudianteService` y `EstudianteUI`.

#### Capa de Presentacion

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

### Patron Iterator

- `EstudianteIterator` implementa `Iterator<Estudiante>` y controla la
	posicion actual del recorrido mediante `hasNext()` y `next()`.
- `EstudianteService` obtiene la lista desde `EstudianteRepository` y la
	entrega envuelta en `EstudianteIterator`.
- `EstudianteUI` recorre los estudiantes usando el iterador, sin depender de
	la implementacion concreta de la lista.

#### Capa de Acceso a Datos

- `EstudianteRepository` estandariza `guardar`, `listar`, `buscarPorId`,
	`actualizar` y `eliminar`.
- `EstudianteRepositoryJson` persiste en archivos locales con `Path` y Gson.
- `EstudianteRepositoryPostgres` persiste en PostgreSQL mediante URL, usuario
	y password.

#### Entidad de Dominio

- `Estudiante` contiene `id`, `nombre` y `correo`, con constructor y getters.
	No expone setters para conservar el modelo como entidad de solo lectura.

#### Cambios realizados para Iterator

1. Se agrego `EstudianteIterator` como iterador concreto de estudiantes.
2. Se cambio `EstudianteUseCase.listar()` para devolver `Iterator<Estudiante>`.
3. Se actualizo `EstudianteService` para envolver la lista del repositorio en
	`EstudianteIterator`.
4. Se actualizo `EstudianteUI.listar()` para usar `hasNext()` y `next()`.
5. Se conservaron sin cambios los repositorios, la fabrica, `Main`, las
	operaciones de escritura y la conexion con PostgreSQL.

### 2.2. ¿Por que Iterator?

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

### 3. Conclusiones y recomendaciones

#### 3.1. Conclusiones
La arquitectura hexagonal permitió separar la presentación, la lógica de negocio y la persistencia.
El patrón Iterator facilitó recorrer los estudiantes sin que EstudianteUI conozca la estructura interna de almacenamiento.
El cambio a Iterator fue localizado y no afectó la conexión PostgreSQL, los repositorios ni la fábrica.
EstudianteService mantiene su responsabilidad de coordinar las operaciones y evita que la UI acceda directamente a la base de datos.
El sistema conserva flexibilidad para trabajar con PostgreSQL o JSON mediante RepositoryFactory.
La aplicación compila y puede iniciar correctamente después de los cambios.
#### 3.2. Recomendaciones
Agregar pruebas unitarias para EstudianteIterator, especialmente para listas vacías y recorridos completos.
Validar las credenciales y la conexión PostgreSQL antes de ejecutar operaciones de persistencia.
Crear iteradores adicionales para filtros como estudiantes por ID, nombre o correo.
Mejorar el manejo de errores de entrada en la consola para evitar fallos cuando el usuario ingrese datos inválidos.
Mantener la interfaz EstudianteRepository como contrato para permitir nuevas formas de almacenamiento sin modificar la lógica de negocio.

