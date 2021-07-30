package fciencias.edatos.searchEngine;
import java.util.Iterator;
/**
 * Programa que implementa una lista doblemente ligada
 * @author Esaú Martínez Pardo
 * @author Eder Samuel Berber Gutiérrez
 * @version 1.0 Julio 2021
 * @ since proyecto final, Estructuras de Datos 2021-2
 */ 
 public class DoubleLinkedList<T>{
 	/**
 	 * Nodo de una lista 
 	 */
 	private class Nodo{
 		/** Nodo anterior */
 		public Nodo anterior;

 		/** Nodo siguiente */
 		public Nodo siguiente;

 		/** Elemento a almacenar*/
 		public T elemento;

 		/**
 		 * Crea un nuevo Nodo con un elemento.
 		 * @param elemento el elemento a almacenar en el Nodo.
 		 */
 		public Nodo(T elemento){
 			this.elemento = elemento;
 		}
 	}

 	/**
 	 * Iterador en una lista doblemente ligada.
 	 */
 	public class IteradorLista implements Iterator<T>{
 		/**Punto de inicio del recorrido*/
 		private Nodo iteradorLista;

 		/**
 		 * Crea un nuevo iterador.
 		 * @param cabeza la cabeza de la lista para obtener todos los elementos.
 		 */
 		public IteradorLista(Nodo cabeza){
 			iteradorLista = new Nodo(null);
 			iteradorLista.siguiente = cabeza;
 		}

 		/**
 		 * Muestra si el siguiente del nodo actual existe o no.
 		 @return true si existe, false si es null.
 		 */
 		public boolean hasNext(){
 			return iteradorLista.siguiente != null;
 		}

 		/**
 		 * Muestra el elemento del nodo siguiente al actual.
 		 * @return el elemento del nodo siguiente.
 		 */
 		public T next(){
 			iteradorLista = iteradorLista.siguiente;
 			return iteradorLista.elemento;
 		}
 	}

 	/** Nodo cabeza. */
 	private Nodo cabeza;

 	/** Nodo cola. */
 	private Nodo cola;

 	/** Cantidad de elementos agregados*/
 	private int tamanio;

 	/**
	 * Inserta un nuevo elemento en la i-ésima posición.
	 * @param i la posición donde agregar el elemento.
	 * @param e el elemento a insertar.
	 * @throws IndexOutOfBoundException si el índice está fuera de rango.
	 */
    public void add(int i, T e) throws IndexOutOfBoundsException{
    	if(i < 0 || i > tamanio)
    		throw new IndexOutOfBoundsException("El índice dado está fuera del rango.");

    	Nodo nuevo = new Nodo(e);

    	// Caso para lista vacia
    	if(cabeza == null){
    		cabeza = nuevo;
    		cola = nuevo;
    		tamanio++;
    		return;
    	}

    	// Caso para primero y ultimo
    	if(i == 0){
    		nuevo.siguiente = cabeza;
    		cabeza.anterior = nuevo;
    		cabeza = nuevo;
    		tamanio++;
    		return;
    	} else if(i == tamanio){
    		nuevo.anterior = cola;
    		cola.siguiente = nuevo;
    		cola = nuevo;
    		tamanio++;
    		return;
    	}

    	int pos = tamanio/2;

    	boolean derecha = i<=pos;
    	
        // Derecha
    	if(derecha){
    		Nodo iterador = cabeza;

    		for(int j = 0; j < i-1; j++)
    			iterador = iterador.siguiente;

    		nuevo.siguiente = iterador.siguiente;
    		nuevo.anterior = iterador;
    		iterador.siguiente.anterior = nuevo;
    		iterador.siguiente = nuevo;
    	} else{ // Izquierda
    		Nodo iterador = cola;
    		for(int j = tamanio-1 ; j > i ; j--)
                iterador = iterador.anterior;

            nuevo.siguiente = iterador;
            nuevo.anterior = iterador.anterior;
            iterador.anterior.siguiente = nuevo;
            iterador.anterior = nuevo;
    	}
        tamanio++;
    } // Si tenemos n elementos, en el peor caso recorremos n/2.

    /**
     * Método que elimina todos los elementos de una lista.
     */
    public void clear(){
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    /**
	 * Verifica si un elemento está contenido en la lista.
	 * @param e el elemento a verificar si está contenido.
	 * @return true si el elemento está contenid, false en otro caso.
	 */
    public boolean contains(T e){
        // Cuando es vacia
        if(isEmpty())
            return false;

        // Solo 1 elementos
        if(size() == 1)
            return e.equals(cabeza.elemento);

        Nodo iteradorCabeza = cabeza;
        Nodo iteradorCola = cola;
        for(int i = 0; i <= tamanio/2 ; i++){
            if(e.equals(iteradorCabeza.elemento) || e.equals(iteradorCola.elemento))
                return true;
            iteradorCabeza = iteradorCabeza.siguiente;
            iteradorCola = iteradorCola.anterior;
        }

        return false;
    } // Si tenemos n elementos, se recorre en el peor caso (n/2)+1

    /**
	 * Obtiene el elemento en la i-ésima posición.
	 * @param i el índice a obtener elemento.
	 * @throws IndexOutOfBoundException si el índice está fuera de rango.
	 */
    public T get(int i) throws IndexOutOfBoundsException{
        if(i < 0 || i >= tamanio)
            throw new IndexOutOfBoundsException("El índice está fuera de rango.");

        boolean derecha = i < tamanio/2;
        Nodo iterador = null;
        if(derecha){
            iterador = cabeza;
            for(int j = 0; j < i; j++)
                iterador = iterador.siguiente;
        } else{
            iterador = cola;
            for(int j = tamanio-1; j > i ; j--)
                iterador = iterador.anterior;
        }

        return iterador.elemento;
    }

    /**
     * Método que verifica si la lista está vacía.
     */
    public boolean isEmpty(){
        return tamanio == 0;
    }

    /**
	 * Elimina el elemento en la i-ésima posición.
	 * @param i el índice del elemento a eliminar.
	 * @return el elemento eliminado.
	 * @throws IndexOutOfBoundException si el índice está fuera de rango.
	 */
    public T remove(int i) throws IndexOutOfBoundsException{
        if(i < 0 || i >= tamanio)
            throw new IndexOutOfBoundsException("El índice está fuera de rango.");

        T eliminado = null;

        // Solo un elemento en la lista.
        if(size() == 1){
            eliminado = cabeza.elemento;
            cabeza = null;
            cola = null;
            tamanio--;
            return eliminado;
        }

        // Eliminar el primer elemento.
        if(i == 0){
            eliminado = cabeza.elemento;
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            tamanio--;
            return eliminado;
        } else if(i == tamanio-1){ // Eliminar el ultimo
            eliminado = cola.elemento;
            cola = cola.anterior;
            cola.siguiente = null;
            tamanio--;
            return eliminado;
        }

        boolean derecha = i<tamanio/2;
        Nodo iterador = null;
        if(derecha){
            iterador = cabeza;
            for(int j = 0; j < i ; j++)
                iterador = iterador.siguiente;
        } else{
            iterador = cola;
            for(int j = tamanio-1 ; j > i; j--)
                iterador = iterador.anterior;
        }

        eliminado = iterador.elemento;
        iterador.anterior.siguiente = iterador.siguiente;
        iterador.siguiente.anterior = iterador.anterior;
        tamanio--;

        return eliminado;
    }

    /**
	 * Regresa la cantidad de elementos contenidos en la lista.
	 * @return la cantidad de elementos contenidos.
	 */
    public int size(){
        return tamanio;
    }

    /**
     * Muestra los elementos de una lista.
     */
    public void show(){
    	Nodo iterador = cabeza;
    	while(iterador != null){
    		System.out.println(iterador.elemento);
    		iterador = iterador.siguiente;
    	}
    }

    public Iterator iterador(){
        return new IteradorLista(this.cabeza);
    }
 }