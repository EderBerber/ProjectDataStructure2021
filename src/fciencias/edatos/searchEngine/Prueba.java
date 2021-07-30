package fciencias.edatos.searchEngine;
/**
 * Programa
 */
public class Prueba<K extends Comparable, T> extends BinarySearchTree<K, T>{
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

		BinarySearchTree<Integer, String> arbol = new BinarySearchTree<>();

		arbol.insert("Hola", 20);
		arbol.insert("Adios", 25);
		arbol.insert("Hola mundo", 25);
		arbol.insert("samuel", 25);
		arbol.insert("Jejeje", 20);
		arbol.insert("repetido", 20);
		//arbol.insert("spdo", 10);

		//arbol.preorden();
		BinarySearchTree.BinaryNode derecho1;
		derecho1 = arbol.root.right;
		BinarySearchTree.BinaryNode derecho2;
		derecho2 = derecho1.left;
		BinarySearchTree.BinaryNode derecho3;
		derecho3 = derecho2.right;
		System.out.println(derecho3.element);

	}
}