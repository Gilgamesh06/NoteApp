![Logo](/Images/logo/noteapp.png)

---

## Historias de Usuario

* **US-01:** Como Usuario quiero poder registrarme en la plataforma.
* **US-02:** Como Usuario quiero poder logearme en la plataforma.
* **US-03:** Como Usuario quiero poder cerrar Sesion en la plataforma.
* **US-04:** Como usuario quiero poder listar mis notas activas y archivadas.
* **US-05:** Como usuario quiero poder crear nuevas nuevas notas.
* **US-06:** Como usuario quiero poder eliminar notas
* **US-07:** Como usuario quiero poder archivar o desarchivar una nota.
* **US-08:** Como usuario quiero poder actualizar notas.

## Casos de Uso

* Casos de Uso y Diagrama de Casos de Uso

  ---
  ### Caso de Uso 1: `Registro de Usuario`

  * **Actor:** Usuario
  * **Descripción:** Permite a un usuario registrarse en la plataforma.
  * **Precondiciones:** El usuario no debe estar registrado previamente.
  * **Flujo Principal:**
    * El usuario accede a la página de inicio.
    * El usuario da clic en Register
    * El usuario ingresa su información (nombre, correo electrónico,fecha nacimiento, nickname, contraseña).
    * El usuario envía el formulario de registro.
    * El sistema valida los datos y crea una nueva cuenta.
    * El sistema carga el Login.
  ---
  ### Caso de Uso 2: `Inicio de Sesión`

  * **Actor:** Usuario
  * **Descripción:** Permite a un usuario iniciar sesión en la plataforma.
  * **Precondiciones:** El usuario debe estar registrado.
  * **Flujo Principal:**
    * El usuario accede a la página de inicio de sesión.
    * El usuario ingresa su nickname y contraseña.
    * El usuario envía las credenciales.
    * El sistema valida las credenciales y carga la interfaz de gestion de Notas.
  ---
  ### Caso de Uso 3: `Cierre de Sesión`

  * **Actor:** Usuario
  * **Descripción:** Permite a un usuario cerrar sesión en la plataforma.
  * **Precondiciones:** El usuario debe estar autenticado.
  * **Flujo Principal:**
    * El usuario selecciona la opción de Salir.
    * El sistema cierra la sesión del usuario.
    * El sistema carga la página de inicio.
  ---
  ### Caso de Uso 4: `Listado de Notas`

  * **Actor:** Usuario
  * **Descripción:** Permite al usuario listar sus notas activas y archivadas.
  * **Precondiciones:** El usuario debe estar autenticado.
  * **Flujo Principal:**
    * El usuario da click en las Opciones {`Notas Activas`, `Notas Inactivas`}.
    * El sistema carga en el container las notas paginadas de a cuatro notas.
    * El sistema carga los botones {`Anterior`,`Siguiente`}
  ---
  ### Caso de Uso 5: `Creación de Nuevas Notas`

  * **Actor:** Usuario
  * **Descripción:** Permite al usuario crear nuevas notas.
  * **Precondiciones:** El usuario debe estar autenticado y la nota no debe existir valida por titulo
  * **Flujo Principal:**
    * El usuario accede a la opción de Crear Nota.
    * El usuario ingresa el contenido de la nota.
    * El usuario da clic en crear nota.
    * El sistema guarda y renderiza la nota si esta no existe.
  ---
  ### Caso de Uso 6: `Eliminación de Notas`

  * **Actor:** Usuario
  * **Descripción:** Permite al usuario eliminar notas existentes.
  * **Precondiciones:** El usuario debe estar autenticado y tener notas disponibles para eliminar.
  * **Flujo Principal:**
    * El usuario selecciona la nota a eliminar.
    * El usuario confirma la acción de eliminación.
    * El sistema elimina la nota y notifica al usuario.
  ---
  ### Caso de Uso 7: `Archivado y Desarchivado de Notas`

  * **Actor:** Usuario
  * **Descripción:** Permite al usuario archivar o desarchivar notas.
  * **Precondiciones:** El usuario debe estar autenticado y tener notas disponibles para archivar/desarchivar.
  * **Flujo Principal:**
    * El usuario selecciona la nota a archivar o desarchivar.
    * El usuario realiza la acción correspondiente.
    * El sistema actualiza el estado de la nota y notifica al usuario.
  ---
  ### Caso de Uso 8: `Actualizar Nota`
  * **Actor:** Usuario
  * **Descripción:** Permite al usuario actualizar el titulo y la descripción de una nota.
  * **Precondiciones:**
    * El usuario debe estar autenticado
    * El usuario debe tener notas disponibles
    * Si el usuario actualiza el titulo este debe ser diferente al titulo de las notas existentes
  * **Flujo Principal**
    * El usuario selecciona la nota.
    * El usuario selecciona actualizar.
    * El usuario modifica los campos titulo y Descripción.
    * El usuario da clic en Actualizar nota.
    * El sistema actualiza la nota y la renderiza.


  ### Diagrama de Casos de Uso

  ![Casos De Uso](/Diagramas/Casos%20De%20Uso/CasosDeUso.png)


## Diagrama de Arquitectura

![Arquitectura](/Diagramas/Arquitectura/Arquitectura.png)

## Diagrama de Actividades
