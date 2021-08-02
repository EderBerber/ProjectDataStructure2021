package fciencias.edatos.searchEngine;
/**
 * Programa que representa un árbol AVL.
 * @author Eder Samuel Berber Gutiérrez
 * @author Esaú Martínez Pardo
 * @version 1.0 Julio 2021
 * @since Estructuras de Datos 2021-2
 */
 public class AVL<K extends Comparable, T> extends BinarySearchTree<K, T>{

 	/*Raíz del árbol*/
 	BinaryNode root;
 	
 	/**
 	 * Regresa la altura de un nodo.
 	 * @param node el nodo del cual conocer su altura.
 	 * @return la altura del árbol.
 	 */
 	public int altura(BinaryNode node){
 		if(node == null)
 			return -1;

 		// Caso para hojas
 		if(node.right==null && node.left==null)
 			return 0;
 		
 		/*El valor máximo de sus hijos*/
 		int max = node.left.alturaNodo > node.right.alturaNodo ? node.left.alturaNodo : node.right.alturaNodo;

 		return max + 1;


 	}

 	/**
 	 * Rebalancea a partir de un nodo para árboles AVL.
 	 * @param actual el nodo a partir del cual será rebalanceado si es necesario.
 	 */
 	private void rebalancea(BinaryNode actual){
 		actual.alturaNodo = altura(actual);
 		int hi = actual.left.alturaNodo;
 		int hd = actual.right.alturaNodo;

 		//Obtenemos el valor absoluto
 		int valor = hi-hd;
 		valor = valor < 0 ? valor*-1 : valor;

 		// Analizando el caso cuando debemos de rebalancear.
 		if(valor >= 2){
 			if(hi > hd){
 				desbalance(actual, false);
 				rebalancea(actual.parent);
 			}

 			else{
 				desbalance(actual, true);
 				rebalancea(actual.parent);
 			}

 		}

 		//Caso base, cuando el nodo es la raíz
 		if(actual == root)
 			return;

 		//Llamamos de nuevo el método para que verifique el rebalanceo del padre del nodo actual.
 		if(valor < 2)
 			rebalancea(actual.parent);
 	}

 	/**
 	 * Rebalancea a partir de un subárbol.
 	 * @param node el nodo a rebalancear.
 	 * @param side el lado a rebalancear: if true -> derecha / if false -> izquierda
 	 */
 	public void desbalance(BinaryNode node, Boolean side){
 		int wi = node.left.left.alturaNodo;
 		int wd = node.right.right.alturaNodo;

 		if(side){	//Si es true, entonces vamos a rebalancear a la derecha.
 			if(wd == (node.left.alturaNodo)+1){
 				rotaDerecha(node);
 			}
 			if(wd == node.left.alturaNodo){
 				rotaIzquierda(node.right);
 				rotaDerecha(node);
 			}
 		}
 		if(!side){	//Si es false, entonces vamos a rebalancear a la izquierda.
 			if(wi == ((node.right.alturaNodo)+1)){
 				rotaIzquierda(node);
 			}
 			if(wi == (node.right.alturaNodo)){
 				rotaDerecha(node.left);
 				rotaIzquierda(node);
 			}
 		}
 	}

 	/**
 	 * Método auxiliar que rebalancea a la derecha, en el caso1. El nodo que le pasamos va a rotar a la izquierda.
 	 * @param node el nodo a rotar hacia la derecha.
 	 */
 	public void rotaDerecha(BinaryNode node){
 		if(node.parent != null){

 			BinaryNode padreNodo = node.parent;
 			boolean referenciaPadre = hijoN(node);

	 		node.parent = node.right;
	 		node = node.parent;
	 		node.parent.right = node.left;
	 		node.left = node.parent;
	 		node.parent = padreNodo;
	 		if(referenciaPadre)
	 			node.parent.right = node;
	 		if(!referenciaPadre)
	 			node.parent.left = node;

	 		/*Recalculando las alturas de los nodos que cambian su altura*/
	 		node.alturaNodo = altura(node);
	 		node.left.alturaNodo = altura(node.left);
	 		node.left.right.alturaNodo = altura(node.left.right);
	 	}
	 	if(node.parent == null){

	 		node.parent = node.right;
	 		node = node.parent;
	 		node.parent.right = node.left;
	 		node.left = node.parent;
	 		node.parent = null;

	 		/*Recalculando las alturas de los nodos que cambian su altura*/
	 		node.alturaNodo = altura(node);
	 		node.left.alturaNodo = altura(node.left);
	 		node.left.right.alturaNodo = altura(node.left.right);
	 	}
 	}

 	/**
 	 * Método auxiliar que rebalancea a la izquierda, en el caso1. El nodo que le pasamos va a rotar a la derecha.
 	 * @param node el nodo a rotar hacia la izquierda.
 	 */
 	public void rotaIzquierda(BinaryNode node){
 		if(node.parent != null){

 			BinaryNode padreNodo = node.parent;
 			boolean referenciaPadre = hijoN(node);

	 		node.parent = node.left;
	 		node = node.parent;
	 		node.parent.left = node.right;
	 		node.right = node.parent;
	 		node.parent = padreNodo;
	 		if(referenciaPadre)
	 			node.parent.right = node;
	 		if(!referenciaPadre)
	 			node.parent.left = node;

	 		/*Recalculando las alturas de los nodos que cambian su altura*/
	 		node.alturaNodo = altura(node);
	 		node.right.alturaNodo = altura(node.right);
	 		node.right.left.alturaNodo = altura(node.right.left);
 		}
 		if(node.parent == null){

 			node.parent = node.left;
	 		node = node.parent;
	 		node.parent.left = node.right;
	 		node.right = node.parent;
	 		node.parent = null;

	 		/*Recalculando las alturas de los nodos que cambian su altura*/
	 		node.alturaNodo = altura(node);
	 		node.right.alturaNodo = altura(node.right);
	 		node.right.left.alturaNodo = altura(node.right.left);
 		}
 	}

 	/**
 	 * Método auxiliar que te dice si un nodo es la hijo izquierdo o derecho de su padre.
	 * @param node el nodo a examinar
	 * @return True -> hijo derecho / False -> hijo izquierdo
	 */
 	public boolean hijoN(BinaryNode node){
 		BinaryNode padre = node.parent;
 		return padre.right == node;
 	}

 	/**
 	 * Inserta un nuevo nodo.
 	 * @param e el elemento del nodo
 	 * @param k la clave del nodo
 	 */
 	public void insert(T e, K k){
		//Si el árbol es vacío.
		if(root == null){
			root = new BinaryNode(k, e, null);
			root.alturaNodo = 0;
			return;
		}

		BinaryNode insertado = this.insertAux(root, k, e);
		/*Cada que se inserte un nuevo nodo, éste tendrá altura 0*/
		insertado.alturaNodo = 0;

		rebalancea(insertado.parent); //Comienza a rebalancear el árbol desde el nodo insertado
	}

	/**
	 * Método auxiliar que ayuda a insertar un Nodo, dependiendo de su clave. Si es mayor inserta en la derecha, si es menor inserta en la izquierda.
	 * @param actual el nodo con el que se va a comparar la llave, para decidir en que lado va a ir bajando (izquierda si es menor, derecha si es mayor)
	 * @param key la llave del nodo a insertar
	 * @param e el elemento del nodo a insertar
	 * @return el nodo insertado
	 */
	private BinaryNode insertAux(BinaryNode actual, K key, T e){
		if(key.compareTo(actual.key) < 0){ //La clave es menor.
			if(actual.left == null){
				BinaryNode agregado = new BinaryNode(key, e, actual);
				actual.left = agregado;
				return agregado;
			} else{
				return insertAux(actual.left, key, e);
			}
		} else{ //La clave es mayor.
			if(actual.right == null){
				BinaryNode agregado = new BinaryNode(key, e, actual);
				actual.right = agregado;
				return agregado;
			} else{
				return insertAux(actual.right, key, e);
			}
		}
	}

	/**
	 * Elimina un nodo
	 * @param k la clave del nodo que queremos eliminar
	 * @return el elemento del nodo eliminado
	 */
	public T delete(K k){
		//Obtenemos el nodo que queremos eliminar.
		BinaryNode eliminado = super.retrieveAux(root, k);
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
			BinaryNode max = super.findMax(eliminado.left);
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

		rebalancea(eliminado.parent);

		return regreso;
	}

 }