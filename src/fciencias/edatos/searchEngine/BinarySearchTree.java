package fciencias.edatos.searchEngine;

/**
* Implementación de un árbol binario de busqueda.
* @author Berber Gutiérrez Eder Samuel
* @author Martínez Pardo Esaú
* @version 3.0 Julio 2021.
* @since Estructuras de Datos 2021-2.
*/
public class BinarySearchTree<K extends Comparable, T> implements TDABinarySearchTree<K, T>{
	
	/**
	 * Nodo para un árbol binario de búsqueda.
	 */
	public class BinaryNode{
		/** Clave */
		public K key;

		/** Elemento */
		public T element;

		/** Padre del nodo. */
		public BinaryNode parent;

		/** Hijo izquierdo. */
		public BinaryNode left;

		/** Hijo derecho. */
		public BinaryNode right;

		/** Altura del nodo */
		public int alturaNodo;

		/**
		 * Crea un nuevo nodo.
		 * @param key la clave del nodo.
		 * @param element el elemento a almacenar.
		 * @param parent el padre del nodo.
		 */
		public BinaryNode(K key, T element, BinaryNode parent){
			this.key = key;
			this.element = element;
			this.parent = parent;
		}
	}

	/** Raíz del árbol. */
	public BinaryNode root;

	@Override
	public T retrieve(K k){
		BinaryNode buscado = retrieveAux(root, k);
		if(buscado == null)
			return null;
		else
			return buscado.element;
	}

	/**
	 * Método auxiliar que regresa el nodo si existe.
	 * @param actual el nodo a buscar
	 * @param k la clave a buscar
	 * @return el nodo encontrado si se encuentra, null si no se encuentra.
	 */
	public BinaryNode retrieveAux(BinaryNode actual, K k){
		if(actual == null) //Es cuando la clave k no se encuentra.
			return null;
		//Cuando las clases son iguales.
		if(actual.key.compareTo(k) == 0){
			if(k.equals(actual.key))
				return actual;
		}
		if(k.compareTo(actual.key) < 0){ //Fue menor, buscamos en el izquierdo.
			return retrieveAux(actual.left, k);
		} else{		//Fue mayor, entonces busca en el derecho.
			return retrieveAux(actual.right, k);	
		}
	}

	@Override
	public void insert(T e, K k){
		//Si el árbol es vacío.
		if(root == null){
			root = new BinaryNode(k, e, null);
			return;
		}

		insertAux(root, k, e);
	}

	/**
	 * Método auxiliar que ayuda a insertar un Nodo, dependiendo de su clave. Si es mayor inserta en la derecha, si es menor inserta en la izquierda.
	 * @param actual el nodo con el que se va a comparar la llave, para decidir en que lado va a ir bajando (izquierda si es menor, derecha si es mayor)
	 * @param key la llave del nodo a insertar
	 * @param e el elemento del nodo a insertar
	 * @return el nodo insertado
	 */
	private void insertAux(BinaryNode actual, K key, T e){
		if(key.compareTo(actual.key) < 0){ //La clave es menor.
			if(actual.left == null){
				BinaryNode agregado = new BinaryNode(key, e, actual);
				actual.left = agregado;
				return;
			} else{
				insertAux(actual.left, key, e);
			}
		} else{ 	//La clave es mayor.
			if(actual.right == null){
				BinaryNode agregado = new BinaryNode(key, e, actual);
				actual.right = agregado;
				return;
			} else{
				insertAux(actual.right, key, e);
			}
		}
	}

	@Override
	public T delete(K k){
		//Obtenemos el nodo que queremos eliminar.
		BinaryNode eliminado = retrieveAux(root, k);
		if(eliminado == null)	//Ver si está en el árbol
			return null;
		BinaryNode padre = eliminado.parent;
		//Caso 1: si tiene 0 hijos -> Es una hoja
		if(eliminado.right == null && eliminado.left == null){
			//Verificar si es hijo izquierdo o derecho.
			if(padre.left == eliminado)
				padre.left = null;
			else
				padre.right = null;
			return eliminado.element;
		}

		//Caso 2: si tiene 2 hijos
		T regreso = eliminado.element;
		if(eliminado.right != null && eliminado.left != null){
			BinaryNode max = findMax(eliminado.left);
			delete(max.key);
			eliminado.key = max.key;
			eliminado.element = max.element;
			return regreso;
		}

		//Caso 3: si tiene un solo hijo (izquierdo o derecho)
		boolean izquierdo = padre.left == eliminado;
		if(eliminado.right != null) //Subir el derecho
			eliminado = eliminado.right;
		else //Subir el izquierdo
			eliminado = eliminado.left;
		eliminado.parent = padre;

		if(izquierdo)
			padre.left = eliminado;
		else
			padre.right = eliminado;
		return regreso;
	}

	@Override
	public T findMin(){
		BinaryNode min = findMin(root);
		if(min == null)
			return null;
		return min.element;
	}

	/**
	 * Encuentra el nodo con clave mínima a partir de un nodo actual.
	 * @param actual el nodo actual.
	 * @return el nodo con clave menor a partir del actual.
	 */
	public BinaryNode findMin(BinaryNode actual){
		if(actual == null)
			return null;

		BinaryNode min = actual;
		while(min.left != null)
			min = min.left;
		return min;
	} 

	@Override
	public T findMax(){
		BinaryNode max = findMax(root);
		if(max == null)
			return null;
		return max.element;
	}

	/**
	 * Encuentra el nodo con clave máxima a partir de un nodo actual.
	 * @param actual el nodo actual.
	 * @return el nodo con clave mayor a partir del actual.
	 */
	public BinaryNode findMax(BinaryNode actual){
		if(actual == null)
			return null;

		BinaryNode max = actual;
		while(max.right != null)
			max = max.right;
		return max;
	} 

	@Override
	public void preorden(){
		preordenAux(root);
		return;
	}

	/**
	 * Método auxiliar que muestra preorden de un árbol aleatorio.
	 */
	private void preordenAux(BinaryNode actual){
		if(actual == null)
			return;
		System.out.println(actual.element);	//Visitar la raíz
		preordenAux(actual.left);	//Visitar el hijo izquierdo.
		preordenAux(actual.right);	//Visitar el hijo derecho.

	}

	@Override
	public void inorden(){
		inordenAux(root);
		return;
	}

	/**
	 * Método auxiliar que muestra inorden de un árbol aleatorio.
	 */
	private void inordenAux(BinaryNode actual){
		if(actual == null)
			return;
		preordenAux(actual.left);	//Visitar el hijo izquierdo.
		System.out.println(actual.element);	//Visitar la raíz
		preordenAux(actual.right);	//Visitar el hijo derecho.

	}

	@Override
	public void postorden(){
		postordenAux(root);
		return;
	}

	/**
	 * Método auxiliar que muestra postorden de un árbol aleatorio.
	 */
	private void postordenAux(BinaryNode actual){
		if(actual == null)
			return;
		preordenAux(actual.left);	//Visitar el hijo izquierdo.
		preordenAux(actual.right);	//Visitar el hijo derecho.
		System.out.println(actual.element);	//Visitar la raíz

	}

	public static void main(String[] args) {
		BinarySearchTree<Integer, String> arbol = new BinarySearchTree<>();
	}
}