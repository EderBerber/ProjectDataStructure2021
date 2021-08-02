package fciencias.edatos.searchEngine;
import java.lang.*;

/**
 * Programa que crea una lista, e implementa la interfaz ListaInterfaz
 * @author Berber Gutiérrez Eder Samuel
 * @author Martínez Pardo Esaú
 * @version 2.0, Julio 2021
 * @since EStructuras de datos, semestre 2021-2
 */
public class LinkedList<T> implements TDALinkedList<T>{

    /**
     * Clase Nodo para poder implementar una lista.
     */
    public class Nodo{
    
        /** Elemento a almacenar en el nodo. */
        public T elemento;

        /** Nodo siguiente. */
        public Nodo siguiente;

        /**
         * Crea un nuevo nodo.
         * @param elemento el elemento a almacenar en el nodo.
         */
        public Nodo(T elemento){
            this.elemento = elemento;
        }

        /**
         * Accede al valor almacenado en un nodo.
         * @return el valr almacenado.
         */
        public T getElemento(){
            return elemento;
        }

        /**
         * Accede al nodo siguiente de un nodo.
         * @return el nodo siguente.
         */
        public Nodo getSiguiente(){
            return siguiente;
        }

        /**
         * Modifica el valor del nodo siguiente.
         * @param nuevoSiguiente el nodo nuevo a asignar al siguiente.
         */
        public void setSiguiente(Nodo nuevoSiguiente){
            siguiente = nuevoSiguiente;
        }
    }
 	
    /** Nodo cabeza, esencial en cada lista*/
    public Nodo cabeza;

    /** Cantidad de elementos agregados*/
    public int tamanio;

    @Override
    public int size(){
 		return tamanio;
    }

    @Override
    public LinkedList cola(){
        if(tamanio == 0)
            return null;

        LinkedList sinPrimero = new LinkedList();
        Nodo iterador = cabeza;

    	for (int i = 0; i<tamanio; i++) {
            sinPrimero.add(0, iterador.elemento);
    		iterador = iterador.siguiente;
    	}
        sinPrimero = sinPrimero.reversa();
        sinPrimero.eliminaPrimero();
    	return sinPrimero;
    }

    @Override
    public void add(int indice, T e){
        if(indice < 0 || indice > size()){
            System.out.println("Fuera de rango");
            return;
        }
            

        Nodo nuevo = new Nodo(e);

        // Caso para lista vacia
        if(cabeza == null){
            cabeza = nuevo;
            tamanio++;
            return;
        }

        // Caso para primero
        if(indice == 0){
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            tamanio++;
            return;
        }

        Nodo iterador = cabeza;

        for (int i = 0;i < indice-1; i++) {
            iterador = iterador.siguiente;
        }
        nuevo.siguiente = iterador.siguiente;
        iterador.siguiente = nuevo;

        tamanio++;
    }

    @Override
    public T obtenPrimero(){
    	if(tamanio == 0)
    		return null;
    	return cabeza.elemento;
    }

    @Override
    public T obten(int indice){
        if(indice < 0 || indice > size()-1){
            System.out.println("Fuera de rango");
            return null;
        }

    	Nodo iterador = cabeza;
    	int contador = 0;

    	while(contador != indice) {
    		iterador = iterador.siguiente;
    		contador++;
    	}
    	return iterador.elemento;
    }

    @Override
    public void eliminaPrimero(){
    	if(tamanio == 0)
            return;
        
    	cabeza = cabeza.siguiente;
        tamanio--;
    }

    @Override
    public void elimina(int indice){
        if(indice < 0 || indice >= size()){
            System.out.println("Fuera de rango");
            return;
        }
            

        if(indice == 0){
            cabeza = cabeza.siguiente;
            tamanio--;
            return;
        }
        Nodo iterador = cabeza;
        int contador = 0;

        while(contador < indice-1){
            iterador = iterador.siguiente;
            contador++;
        }
        Nodo auxiliar = iterador.getSiguiente().getSiguiente();
        iterador.setSiguiente(auxiliar);
        tamanio--;
    }

	@Override
	public int indice(T objeto){
        Nodo iterador = cabeza;
        int indiceEncontrado = -1;
        boolean encontrar = false;
        for (int i = 0; i<tamanio; i++) {
            if(iterador.elemento.equals(objeto)){
                indiceEncontrado = i;
                encontrar = true;
                break;
            }
            iterador = iterador.siguiente;
        }
		return indiceEncontrado;
	}

	@Override
	public boolean estaVacia(){
		return size() == 0;
	}

	@Override
	public LinkedList concatena(LinkedList otra){
        LinkedList original = new LinkedList();
        for (int i = 0; i<tamanio; i++) 
            original.add(0, obten(i)); 
        for (int  j= 0; j<otra.tamanio; j++) 
            original.add(0, otra.obten(j));
        return original.reversa();  
	}

	@Override
	public void limpia(){
        tamanio = 0;
		cabeza = null;
	}

	@Override
 	public void muestra(){
        if(tamanio == 0){
            System.out.println("La lista es vacía.");
        }
        Nodo iterador = cabeza;
 		for (int i = 0; i<size(); i++) {
 			System.out.println(iterador.elemento);
 			iterador = iterador.siguiente;
 		}
 	}

 	/**
 	 * Método que regresa la reversa de una lista.
     * @return la misma lista, pero de reversa.
 	 */
 	public LinkedList reversa(){
 		LinkedList reversa = new LinkedList();
 		Nodo iterador = cabeza;
 		for (int i = 0; i<tamanio; i++) {
 			reversa.add(0, iterador.elemento);
 			iterador = iterador.siguiente;
 		}
 		return reversa;
 	}
 }