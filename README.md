# API Gestión de Pólizas

API REST desarrollada en **Spring Boot** para la gestión de pólizas y riesgos.
La solución implementa **arquitectura por capas (Controller → Service → Repository)** inspirada en principios de **Clean Architecture**, priorizando separación de responsabilidades, mantenibilidad y claridad del código.

Esta API permite gestionar pólizas, administrar sus riesgos asociados, realizar renovaciones, cancelaciones y simular la integración con un sistema CORE externo.

---

# Arquitectura

La aplicación sigue una arquitectura de capas que separa claramente la lógica de negocio de la infraestructura.

```
Controller (API REST)
        ↓
Service (Lógica de negocio)
        ↓
Repository (Acceso a datos)
        ↓
Base de datos H2 (memoria)
```

### Responsabilidades

**Controller**

* Expone endpoints REST
* Maneja requests y responses HTTP

**Service**

* Implementa reglas de negocio
* Coordina operaciones entre entidades y repositorios

**Repository**

* Acceso a datos usando Spring Data JPA

---

# Reglas de Negocio

La API implementa las siguientes validaciones:

### 1. Póliza individual

Una póliza de tipo **INDIVIDUAL solo puede tener un riesgo**.

### 2. Renovación

No es posible renovar una póliza que esté **CANCELADA**.

Al renovar una póliza:

* `canon` aumenta según **IPC**
* `prima` aumenta según **IPC**
* el estado cambia a **RENOVADA**

### 3. Cancelación de póliza

Cuando una póliza se cancela:

* el estado pasa a **CANCELADA**
* **todos los riesgos asociados se cancelan automáticamente**

### 4. Creación de riesgos

Solo se pueden agregar riesgos si la póliza es de tipo **COLECTIVA**.

---

# Seguridad

Los endpoints externos requieren el header:

```
x-api-key
```

Si el header no está presente o es incorrecto, la API responde:

```
401 Unauthorized
```

---

# Endpoints

## Listar pólizas

```
GET /polizas
```

Query params:

```
tipo
estado
```

Ejemplo:

```
GET /polizas?tipo=INDIVIDUAL&estado=ACTIVA
```

---

## Obtener riesgos de una póliza

```
GET /polizas/{id}/riesgos
```

Ejemplo:

```
GET /polizas/1/riesgos
```

---

## Renovar póliza

```
POST /polizas/{id}/renovar
```

Acciones:

* Incrementa canon y prima
* Cambia estado a RENOVADA

Ejemplo:

```
POST /polizas/1/renovar
```

---

## Cancelar póliza

```
POST /polizas/{id}/cancelar
```

Acciones:

* Cambia estado a CANCELADA
* Cancela todos los riesgos asociados

Ejemplo:

```
POST /polizas/1/cancelar
```

---

## Agregar riesgo a una póliza

```
POST /polizas/{id}/riesgos
```

Validación:

* Solo permitido si la póliza es **COLECTIVA**

Ejemplo:

```
POST /polizas/1/riesgos
```

---

## Cancelar riesgo

```
POST /riesgos/{id}/cancelar
```

Ejemplo:

```
POST /polizas/1/cancelar
```

---

# Mock de Sistema CORE

La API incluye un mock para simular la integración con un sistema externo.

Endpoint:

```
POST /core-mock/evento
```

Body:

```
{
  "evento": "ACTUALIZACION",
  "polizaId": 555
}
```

Comportamiento:

* No realiza integración real
* Registra en logs que el evento fue enviado al CORE

Ejemplo log:

```
Evento enviado al CORE -> ACTUALIZACION Poliza: 555
```

---

# Ejecución del Proyecto

### Requisitos

* Java 17+
* Maven

### Clonar repositorio

```
git clone <repo>
```

### Ejecutar aplicación

```
mvn spring-boot:run
```

La API se iniciará en:

```
http://localhost:8080
```

---

# Base de Datos

Se utiliza **H2 en memoria** para simplificar la prueba técnica.

Console H2:

```
http://localhost:8080/h2-console
```

---

# Testing

La arquitectura permite crear pruebas unitarias fácilmente usando:

* **JUnit 5**
* **Mockito**

Las pruebas se enfocan principalmente en:

* Lógica de negocio en `PolizaService`
* Validaciones de reglas
* Comportamiento de renovación y cancelación

---

# Uso de IA durante el desarrollo

Esta solución fue desarrollada utilizando el asistente de IA **ChatGPT** para acelerar la generación de código base.

Las responsabilidades del desarrollador incluyen:

* Diseño la arquitectura
* Validación del código generado
* Refactorización cuando la IA produce soluciones incorrectas
* Garantizar el cumplimiento de las reglas de negocio

---

# Posibles Mejoras

Si el sistema evolucionara a producción, se podrían incorporar:

* DTOs y mappers (MapStruct)
* validaciones con Bean Validation
* manejo global de excepciones (`@ControllerAdvice`)
* integración real con CORE mediante REST o mensajería
* autenticación con OAuth2 o JWT
* persistencia en PostgreSQL
* pruebas de integración
* documentación OpenAPI / Swagger

---

# Autor

Desarrollado como parte de una **prueba técnica de arquitectura y desarrollo backend** en Spring Boot.
