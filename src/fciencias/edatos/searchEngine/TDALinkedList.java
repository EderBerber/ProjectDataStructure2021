package fciencias.edatos.searchEngine;

/**
 * Interfaz que provee varios métodos referentes a listas.
 * @author Cervantes Gonzales Pedro Ulises
 * @version 1.0 03/04/17
 * @since Estructuras de datos, semestre 2021-2
 */
public interface TDALinkedList<T>{
    
    /**
     * Devuelve el tamaño de la lista.
     * 
     * @return El tamaño de la lista. Si está vacía devuelve cero.
     */
    public int size();
    
    /**
     * Devuelve la cola de la lista.
     * 
     * @return Una lista con todos los elementos de le invocante menos el primero.
     */
    public LinkedList cola();
    
    
    /**
     * Inserta un elemento en la posición especificada de la lista, desplazando 
     * todos los demás elementos.
     * 
     * @param indice La posición donde se insertará el objeto. La posición debe
     *               ser mayor o igual a cero, y menor o igual al tamaño de la lista.
     * @param e el elemento a insertar.
     * @throws IndexOutOfBoundException si el índice está fuera de rango.

     */
    public void add(int indice, T e);
    
    /**
     * Devuelve el primer elemento de la lista.
     * 
     * @return El primer elemento de la lista. Si la lista está vacía, devuelve null.
     */
    public T obtenPrimero();
    
    /**
     * Devuleve el elemento en la posición especificada de la lista.
     * 
     * @param indice La posición donde se encuentra el objeto. La posición debe
     *               ser mayor o igual a cero, y menor al tamaño de la lista.
     * @return El elemento en la posición especificada. Si la posición es inválida
     *         devuelve null.
     * @throws IndexOutOfBoundException si el índice está fuera de rango.

     */
    public T obten(int indice);
    
    /**
     * Elimina el primer elemento de la lista y actualiza la cabeza de la lista
     */
    public void eliminaPrimero();
    
    /**
     * Elimina un elemento en la posición especificada de la lista, desplazando 
     * todos los demás elementos.
     * 
     * @param indice La posición donde se eliminará el objeto. La posición debe
     *               ser mayor o igual a cero, y menor al tamaño de la lista.
     * @throws IndexOutOfBoundException si el índice está fuera de rango.

     */
    public void elimina(int indice);
    
    /**
     * Busca un elemento en la lista y devuelve la primera posición donde aparece.
     * 
     * @return El índice del objeto. Si no lo encuentra, devuelve -1.
     */
    public int indice(T objeto);
    
    /**
     * Menciona si la lista no tiene elementos
     * 
     * @return true si la lista no tiene elementos, false en otro caso.
     */
    public boolean estaVacia();
    
    /**
     * Concatena la lista invocante con una nueva lista.
     * 
     * @param otra La lista con la que se concatenará.
     * @return La lista que resulta de la concatenación. Esta lista es independiente
     *         a las otras dos.
     */
    public LinkedList concatena(LinkedList otra);

    /**
     * Vacia la lista. Elimina todos los elementos.
     */
    public void limpia();

    /**
     * Muestra los elementos de una lista.
     */
    public void muestra();
}