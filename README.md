# Motor de búsqueda.

## Autor: Esaú Martínez Pardo
   Número de cuenta: 317055681
## Autor: Eder Samuel Berber Gutiérrez
   Número de cuenta: 317075292

----

### Descripción
El proyecto consta de un tipo "Google", en el que tenemos un botón de búsqueda, escribimos una consulta y recibimos los 10 resultados de búsqueda más relevantes(buscando primero en la caché por si la consulta ya entró en el programa antes y ya fueron devueltos sus 10 archivos correspondientes y ya no recalcular otra vez) es decir,  en los que la o las palabras que escribimos en el botón de búsqueda hayan aparecido en el documento un número mayor de veces. Utilizamos un árbol Binario de Búsqueda. 
TDABinarySearchTree con la descripción de los métodos característicos de un árbol binario de búsqueda. En la presentación [Árboles.pdf](https://github.com/EmmanuelCruz/MaterialED_2021-2/blob/master/10.%20%C3%81rboles%20generales/%C3%81rboles.pdf) se muestran algunos conceptos y representaciones de las operaciones.

----

### Implementaciones

Utilizamos un árbol binario de búsqueda para la entrega de los documentos más relevantes con respecto a una consuta. 
En la caché usamos un arreglo con listas simplemente ligadas. 

* [Operaciones sobre un árbol binario de búsqueda](https://docs.google.com/presentation/d/1HmNzesj-fDTbKLVSRg9mb0oZ5ZDH4IkSAP4J3SYPl84/edit?usp=sharing)

----

### Correr y compilar

Para ejecutar el proyecto se usan los siguientes comandos:

```
ant build
ant jar
ant run
```

Se recomienda que al finalizar la ejecución del programa se use el comando siguiente:

```
ant clean
```
