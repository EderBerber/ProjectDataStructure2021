package fciencias.edatos.searchEngine;
import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.Math;

/**
 * Programa que simula un motor de búsqueda. Dada una búsqueda, te muestra los 10 archivos más similares a tu busqueda.
 * @author Berber Gutiérrez Eder Samuel
 * @author Martínez Pardo Esaú
 * @version 3.0 Julio 2021
 * @since proyecto final, Estructuras de Datos 2021-2
 */
public class SearchEngine<K extends Comparable, T> extends BinarySearchTree<K, T>{

	/* Ruta en donde se encuentran los archivos */
	public static String rutaArchivos;
	/* Historial de búsquedas */
	public static LinkedList historial;
	/* Valores de TF-IDF de cada archivo. Cada casilla es un archivo */
	public static Double[] listaValores;
	/*Variable estática que solo te ayuda a llamar a los métodos en toda la clase.*/
	//public static SearchEngine helper = new SearchEngine();
	//String[] void caché = new String[10]; LinkedList



	/**
	 * Método que almacena la caché.
	 * @param arregloCache el arreglo con la caché a buscar.
	 * @return el arreglo de cadenas
	 *
	public boolean buscarCache(String[] arregloCache){
		caché = new String[10];
		return true;
	}*/

	/**
     * Método auxiliar que quita acentos en el idioma español (á, é, í, ó, ú, ü), y algunos otros caracteres como (, . : ; ¿ ? ¡ !) de una cadena.
     * @param cadena la cadena a la que se le van a quitar los acentos
     * @return la cadena sin acentos
     */
    public String quitaAcentos(String cadena){
    	String cadenaSinAcentos = "";
    	for (int i = 0; i<cadena.length(); i++) {
    		char z = cadena.charAt(i);
    		switch(z){
    			case 'á':
    				z = 'a';
    				break;
    			case 'é':
    				z = 'e';
    				break;
    			case 'í':
    				z = 'i';
    				break;
    			case 'ó':
    				z = 'o';
    				break;
    			case 'ú' :
    			case 'ü' :
    				z = 'u';
    				break;
    			case ',':
    				z = ' ';
    				break;
    			case '.':
    				z = ' ';
    				break;
    			case ':':
    				z = ' ';
    				break;
    			case ';':
    				z = ' ';
    				break;
    			case '?':
    				z = ' ';
    				break;
    			case '¿':
    				z = ' ';
    				break;
    			case '¡':
    				z = ' ';
    				break;
    			case '!':
    				z = ' ';
    				break;
    			default:
    				z = z;
    		}
    		cadenaSinAcentos += z;
    	}
    	return cadenaSinAcentos;
    }

	/**
	 * Método que te devuelve una lista de todos los archivos txt que hay en la carpeta de la ruta dada.
	 * @param ruta la ruta de la carpeta en donde se encuentran los archivos.
	 * @return una lista con el nombre de todos los archivos contenidos en la carpeta de la ruta dada.
	 */
	public LinkedList archivosRuta(){	//Mandar una excepción cuando la ruta sea a un archivo en específico.
		String ruta = rutaArchivos;
		File file = new File(ruta);
		String[] archivos = file.list();	//Muestra la lista de archivos que hay en una carpeta
		LinkedList<String> listaArchivos = new LinkedList<>();

		for (int i = 0; i<archivos.length; i++) {
			String terminacion = archivos[i].substring(archivos[i].length()-4, archivos[i].length());	//Verifica que solo se tomen en cuenta los archivos txt
			if(terminacion.equals(".txt"))
				listaArchivos.add(0, archivos[i]);	//Creando la lista con puros archivos txt de esa carpeta
		}
		return listaArchivos;
	}

	/**
	 * Método que dado una cadena String, te devuelve un árbol binario de búsqueda.
	 * Cada nodo contiene como elemento la frecuencia con la que esa misma palabra se repite en toda la cadena.
	 * @param cadena la cadena a convertir en el árbol binario de búsqueda.
	 * @param limite la cadena en donde va a hacer los cortes.
	 * @return el árbol binario de búsqueda con las palabras de la cadena String.
	 */
	public BinarySearchTree toBST(String cadena, String limite){
		if(cadena == null)
			return null;
		int posicion = 0;
		int i = 0;
		int num = 0;
		BinarySearchTree<String, Integer> arbol = new BinarySearchTree<>();
		for (int j=0; j<cadena.length(); j++) {
			if(cadena.charAt(j) == limite.charAt(0))
				num++;
		}
		while(i < num){
			posicion = cadena.indexOf(limite);
			String palabra = cadena.substring(0, posicion);
			cadena = cadena.substring(posicion+1);
			if(arbol.retrieve(palabra) != null)
				arbol.insert(arbol.delete(palabra)+1, palabra);
			else
				arbol.insert(1, palabra);
			i++;
		}
		return arbol;
	}

	/**
	 * Método que devuelve un árbol binario de búsqueda (Binary Search Tree) con todas las palabras de un archivo.
	 * Cada nodo contiene la frecuencia con la que la palabra aparece en dicho archivo.
	 * @param nombreArchivo el nombre del archivo el cuál será convertido a un arreglo de cadenas.
	 * @return el árbol binario de búsqueda con todas las palabras del archivo.
	 */
	public String[] arregloArchivo(String nombreArchivo){
		/*En el programa, al inicio se pide la ruta. Ésta será la misma en todo el programa (a menos que se decida cambiarla),
		y la ruta se va a almacenar en la variable estática de la clase "rutaArchivos".*/
		String ruta = rutaArchivos+"/"+nombreArchivo;
		try{
			FileReader nuevo = new FileReader(ruta);
			BufferedReader lector = new BufferedReader(nuevo); //Creando un buffer para leer el archivo txt.
			String linea = "";
			String palabras = "";
			/*Vamos agregando a una variable String todo lo que contiene el archivo.*/
			while(linea != null){
				linea = lector.readLine();
				if(linea != null){
					linea = linea.toLowerCase();
					linea = quitaAcentos(linea);
					palabras += linea + " ";
				}
			}
			nuevo.close();
			/*Tenemos un arreglo con todas las palabras del archivo.*/
			String[] arregloPalabras = palabras.split(" ");	//INTENTAR SUSITUIR .split() POR UNO QUE LO ALMACENE EN UN BST, ES MEJOR PARA EL getTF()
			return arregloPalabras;
		}
		catch(IOException e) {
			System.out.println("No se encontró el archivo.");
			return new String[0];	//Regresa un arreglo vacío, pues es lo que regresa el método.
		}
	}

	/**
	 * Método que calcula el TF de una palabra.
	 * @param busquedaP la palabra la cual se regresará el TF.
	 * @param archivo el arreglo en donde están todas las palabras del archivo.
	 * @return el TF de esa palabra en el archivo seleccionado.
	 */
	public double getTF(String busquedaP, String[] archivo){
		/*Poniendo a la busqueda en minúsculas y quitandole acentos y caracteres especiales.*/
		busquedaP = quitaAcentos(busquedaP);
		busquedaP = busquedaP.toLowerCase();
		int contador = 0;
		/*Recorriendo el arreglo en el que se va a buscar cuantas veces se repite la palabra busquedaP*/
		for (int i = 0; i<archivo.length; i++) {
			if(archivo[i].equals(busquedaP))
				contador++;	//Se va aumentando el contador cada vez que encuentre la palabra.
		}
		/*Si encontró al menos una vez la palabra, entonces va a calcular el TF con la fórmula dada. En caso de que no haya encontrado nada, regresa 0.*/
		if(contador > 0)
			return (Math.log(contador) / Math.log(2)) + 1;
		return 0;
	}

	/**
	 * Método que calcula el IDF de una palabra.
	 * @param busquedaP la palabra a buscar en los archivos.
	 * @param listaArchivos la lista que contiene el nombre de los archivos.
	 * @return el IDF de la palabra en los archivos.
	 */
	public double getIDF(String busquedaP, LinkedList listaArchivos){
		/*Poniendo a la busqueda en minúsculas y quitandole acentos y caracteres especiales.*/		
		busquedaP = quitaAcentos(busquedaP);
		busquedaP = busquedaP.toLowerCase();
		Boolean[] arregloBooleano = new Boolean[listaArchivos.size()];	//Este arreglo nos va a ayudar a saber en cuántos archivos encontró la palabra.
		String doc = "";
		/*Buscando la palabra archivo por archivo. Primero, teniendo la ruta llamamos al método arregloArchivo(String doc) para obtenerla lista de todos
		los archivos txt que hay. Después, convertimos cada archivo en una String e iteramos en éste para saber si se encuentra la palabra o no.
		Posteriormente, vamos cambiando cada casilla del arreglo booleano por archivo, y así sabremos en cuales se encontró y en cuales no. Por último,
		se calcula el IDF.*/
		for (int i = 0; i<listaArchivos.size(); i++) {
			doc = (String)listaArchivos.obten(i);
			String[] file = arregloArchivo(doc);
			for (int j = 0; j<file.length; j++) {
				if(busquedaP.equals(file[j])){
					arregloBooleano[i] = true;	//Si se encuentra, entonces esa casilla del arreglo se pone en true. AUN NO SE ACABA.
					break;
				}
			}
			doc = "";
		}

		return 0.0;
	}


	public static void main(String[] args) {
		SearchEngine u = new SearchEngine();
		rutaArchivos = "/home/samuel_berber/Documentos";
		String[] prueba = u.arregloArchivo("archivo.txt");
		//System.out.println("La longitud del archivo es: " + prueba.length);
		//System.out.println("El TF de hola es: " + u.getTF("hola", prueba));
		/*for (int i = 0; i<prueba.length; i++) {
			System.out.println(prueba[i]);
		}*/
		String nuevo = "Hola mundo cruel";
		BinarySearchTree<String, Integer> arbol = new BinarySearchTree<>();
		u.toBST(nuevo, " ");
		//BinaryNode actual = new BinaryNode();
		System.out.println(arbol.root.element);
	}
}
