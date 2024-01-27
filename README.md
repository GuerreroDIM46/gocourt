# Definición de Proyecto Individual

![](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/logo_gocourt.png)

## Descripción
La aplicación Gocourt almacenará los datos de los jugadores tanto principiantes como federados de un campo de golf.

Permitirá gestionar sus datos, así como sus puntuaciones y categoría, de manera que pueda verificar a través de una puntuación simulada las garantías de un principiante que practica en la cancha su aptitud para salir al campo de juego. A través de ello podrán crearse parejas de juego para el campo, de manera que la puntuación media entre ellas sea similar. De esta manera el juego de los principiantes recien federados, puede progresar junto a los más experimentados. 

## Diagrama de clases de diseño

![Diagrama de Diseño MVP](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/diagramaClasesGocourt.drawio.png)

**Cumplimiento de requisitos**
1. **Herencia**: Se realizará sobre `Jugador` y sus subtipos.
2. **Relación One-To-Many**: Relación entre `Campo` y `Jugador`.
3. **Método personalizado**: aptitud de un principiante para federarse en función de su precisión en cancha.
4. **Listado**: se mostrará un listado de jugadores que son aptos para federar Figura 1
5. **CRUD**: Desde el listado anterior se podrá gestionar el CRUD de los jugadores usando el formulario de la Figura 2.
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/menadim46/gocourt
   1. Librería: https://github.com/menadim46/gocourt
7. **Despliegue** en Internet:
   1. API: https://gocourt-api.herokuapp.com/api
   1. Web: https://gocourt.netlify.app/ 

## Interfaz de usuario

Figura 1:  
![Figura 1](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/interfaz1.png)


Figura 2:  
![Figura 2](https://git.institutomilitar.com/menadim46/gocourt/-/wikis/imgs/interfaz2.png)
