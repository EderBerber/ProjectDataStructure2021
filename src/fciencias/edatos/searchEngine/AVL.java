package fciencias.edatos.searchEngine;
/**
 * Programa que representa un árbol AVL.
 * @author Eder Samuel Berber Gutiérrez
 * @author Esaú Martínez Pardo
 * @version 1.0 Julio 2021
 * @since Estructuras de Datos 2021-2
 */
 public class AVL<K extends Comparable, T> extends BinarySearchTree<K, T>{
 	
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
 		//ACABAR

 	}

 	/**
 	 * Rebalancea a partir de un nodo para árboles AVL.
 	 * @param actual el nodo a partir del cual será rebalanceado si es necesario.
 	 */
 	private void rebalancea(BinaryNode actual){
 		int hi = altura(actual.left);
 		int hd = altura(actual.right);

 		//Obtenemos el valor absoluto
 		int valor = hi-hd;
 		valor = valor < 0 ? valor*-1 : valor;

 		// Analizando el caso cuando debemos de rebalancear.
 		if(valor >= 2){
 			if(hi > hd)
 				desbalance(actual, true);
 			else
 				desbalance(actual, false);
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
 	 * @param side el lado a rebalancear. if true -> derecha, if false -> izquierda.
 	 */
 	public void desbalance(BinaryNode node, Boolean side){
 		//AHORITA LO HAGO
 	}


 	public void insert(T e, K k){
		//Si el árbol es vacío.
		if(root == null){
			root = new BinaryNode(k, e, null);
			return;
		}

		BinaryNode insertado = this.insertAux(root, k, e);

		rebalancea(insertado);
	}

	/**
	 * 
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