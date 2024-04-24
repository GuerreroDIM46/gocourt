# Definición de Proyecto Individual

![](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/logo_gocourt.png)

## Descripción
La aplicación Gocourt almacenará los datos personales y técnicos de los jugadores tanto principiantes como federados de un campo de golf.  

Actualmente existe una tecnología radar bajo la denominación de TopTracer, capaz de registrar el recorrido de las bolas de golf desde el puesto de prácticas y trazar la trayectoria de esta.  

Además proporciona información de la desviación de un golpe respecto a un objetivo. Con todo ello constituye una herramienta de gran utilidad para los formadores del campo para tener una referencia de la velocidad del swing y la bola, el palo adecuado para cada golpe, la distancia de caída y la distancia que rueda la bola después del impacto.    

El punto de interés para esta aplicación es utilizar esos registros de práctica con un doble objetivo. En primer lugar incentivar la práctica en cancha para mejorar la precisión de los principiantes, que no ofrecen garantías para un juego seguro sobre hierba, y en segundo lugar fomentar que los principiantes con una puntuación mínima puedan federarse para competir contra otros jugadores federados con un hándicap similar en el mismo centro y seguir mejorando esta vez sobre el campo de juego.  

Como aclaración a las puntuaciones, las referencias de hándicap para los federados se sitúan entre 0 y 48 puntos. Y para recorrer un campo estándar de 18 hoyos se necesitan aproximandamente 72 golpes. Un ejemplo de como se resulta un partido podría ser un jugador con hándicap 10 que ha necesitado 82 golpes para lograr completar todos los hoyos, de igual manera un handicap 0 ha completado los hoyos en exactamente 72 golpes. Por lo que tomando las referencias arriba reseñadas, para poder federarse y salir al campo un principiante puede efectuar como máximo 120 golpes para comlpetar el recorrido.

Para el cómputo de la llamada "puntuacioń calculada", se tienen en cuenta datos habituales de juego como la proporción 1/3 de golpes largos/golpes cortos en un recorrido y las precisiones totales proporcionadas por el dispositivo de medición.

## Diagrama de clases de diseño

![Diagrama de Diseño MVP](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/diagramaClasesGocourt.drawio.png)

**Cumplimiento de requisitos**
1. **Herencia**: Se realizará sobre `Jugador` y sus subtipos.  
2. **Relación One-To-Many**: Relación entre `Campo` y `Jugador`. 
3. **Método personalizado**: pasando como parámtros un determinado jugador principiante, una puntuación mínima, un rango inferior, y un rango superior de puntos, obtener un listado de jugadores de su mismo campo federados y no profesionales contra los que pueda jugar.  
4. **Listado**: se mostrará un listado de jugadores de la Figura 1.  
5. **CRUD**: Desde el listado anterior se podrá gestionar el CRUD de los jugadores usando el formulario de la Figura 2.  
6. **URLs** del proyecto:  
   1. Repositorio proyecto: 
        - [GitLab](https://git.institutomilitar.com/menadim46/gocourt)  
        - [GitHub](https://github.com/GuerreroDIM46/gocourt.git)  
   2. Librería: pendiente developer

7. **Despliegue en Internet:**  
    * API: 
        - https://gocourt-5ef625746984.herokuapp.com/api
        - https://gocourtapi.manabo.org/api
    * Web: 
        - https://gocourt.netlify.app  
        - https://gocourt.manabo.org


## Interfaz de usuario

Figura 1:  
![Figura 1](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/interfaz1.png)


Figura 2:  
![Figura 2](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/interfaz2.png)
