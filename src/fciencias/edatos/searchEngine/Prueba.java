package fciencias.edatos.searchEngine;
import java.lang.Math;
/**
 * Programa
 */
public class Prueba<K extends Comparable, T> extends LinkedList<T>{

	
	
	public static void main(String[] args) {
		/*String palabra1 = "sam";
		String palabra2 = "sem";
		String palabra3 = "ala";
		String palabra4 = "eje";
		int num1 = palabra1.compareTo(palabra2);
		System.out.println("El número 1 es: " + num1);
		int num2 = palabra3.compareTo(palabra4);
		System.out.println("El número 2 es: " + num2);
		*/
		/*BinarySearchTree<String, String> arbol = new BinarySearchTree<>();
		arbol.insert("sem", "sem");
		arbol.insert("sim", "sim");
		arbol.insert("sam", "sam");
		arbol.insert("som", "som");
		arbol.insert("sum", "sum");*/

		//LinkedList<String> arbol = (LinkedList<String>) new Node();

		LinkedList<String> lista = new LinkedList<>();
		LinkedList<Integer>	numeros = new LinkedList<>();
		LinkedList<Boolean> coco = new LinkedList<>();

		lista.add(0, "Hola");
		lista.add(0, "Adios");
		lista.add(0, "Hola mundo");
		lista.add(0, "samuel");
		lista.add(0, "Jejeje");
		lista.add(0, "repetido");
		numeros.add(0, 4);
		numeros.add(0, 3);
		numeros.add(0, 2);
		numeros.add(0, 1);
		numeros.add(0, 0);
		lista.muestra();
		System.out.println("El tamaño es: " + lista.size());
		numeros.muestra();
		//lista.cola();
		System.out.println("El indice es: " + numeros.indice(5));
		System.out.println("El 2 al cuadrado es: " + Math.pow(11, 2));
		double[] arreglo = new double[3];
		System.out.println("La longitud es: " + arreglo.length);
		//lista.cola().muestra();
		//lista.
		//lista.muestra();
		//System.out.println("¿Es vacía? " + numeros.estaVacia());
		//System.out.println("Tamaño: " + numeros.size());
		//lista.concatena(numeros).muestra();
		//numeros.limpia();
		//numeros.muestra();
		/*System.out.println("¿Es vacía? " + numeros.estaVacia());
		lista = lista.concatena(numeros);
		lista.muestra();*/

		/*for (int i = 0; i<lista.tamanio; i++) {
			System.out.println("El elemento " + i + " de la lista es: " + lista.getElemento());
		}*/


		//arbol.insert("spdo", 10);

		//arbol.preorden();

		

		/*BinarySearchTree.BinaryNode derecho1;
		derecho1 = arbol.root.right;
		BinarySearchTree.BinaryNode derecho2;
		derecho2 = derecho1.left;
		BinarySearchTree.BinaryNode derecho3;
		derecho3 = derecho2.right;*/
		//System.out.println(derecho3.element);
	}
}