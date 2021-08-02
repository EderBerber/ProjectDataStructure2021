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
public class SearchEngine<K extends Comparable, T>{

	/**
	 * Clase que simula la Caché.
	 */
	public static class Cache<T>{
 		/*Atributo de clase*/
 		static LinkedList lista;

 		/**
 		 * Método que agregue datos a una lista
 		 * @param elemento el elemento a agregar a una lista
 		 */
 		public LinkedList agregaCache(T elemento){
 			LinkedList listaNueva = new LinkedList();
 			listaNueva.add(0, elemento);
 			return listaNueva;
 		}

 		/**
 		 * Agrega un elemento a una lista
 		 * @param lista la lista a agregar
 		 * @param elemento el elemento a agregar
 		 * @param posicion la posición en la cual se va a agregar
 		 */
 		public void agregaLista(LinkedList lista, T elemento, int posicion){
 			lista.add(posicion, elemento);
 			return;
 		}
 	}

	/* Ruta en donde se encuentran los archivos */
	public static String rutaArchivos;
	/* Historial de búsquedas */
	public static LinkedList historial;
	/* Valores de TF-IDF de cada archivo. Cada casilla es un archivo */
	public static Double[] listaValores;
	/* Caché */
	public static Cache<LinkedList>[] cache = new Cache[10];

	/**
	 * Método que almacena la caché.
	 * @param lista la lista a almacenar
	 * @param busqueda la busqueda hecha que retornó la lista con los resultados
	 */
	public void llenaCache(LinkedList lista, String busqueda){
		if(lista.size() == 0)
            return;
		Cache objeto = new Cache();
		for (int i = 0; i<cache.length; i++) {
			if(cache[i] == null){
				cache[i].lista = objeto.agregaCache(busqueda);
				cache[i].lista = cache[i].lista.concatena(lista);
				return;
			}
		}
		cache[0].lista = objeto.agregaCache(busqueda);
		cache[0].lista = cache[0].lista.concatena(lista);
		return;
	}

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
		//cadena = cadena.replaceAll("^\\s*", " ");
		cadena = cadena.trim();
		BinarySearchTree<String, Integer> arbol = new BinarySearchTree<>();
		int posicion = 0;
		int i = 0;
		int num = 0;
		
		for (int j=0; j<cadena.length(); j++) {	//Te dice cuántas palabras tendremos, y cuantas iteraciones vamos a hacer.
			if(cadena.charAt(j) == limite.charAt(0))
				num++;
		}
		do{		//Iterando en la cadena y agregando las palabras al árbol.
			posicion = cadena.indexOf(limite);	
			String palabra = cadena.substring(0, posicion);
			cadena = cadena.substring(posicion+1);

			if(arbol.retrieve(palabra) != null){
				arbol.actualiza(palabra, arbol.retrieve(palabra)+1);
			}
			else
				arbol.insert(1, palabra);
			i++;
		}while(i < num);
		return arbol;
	}

	/**
	 * Método que devuelve un árbol binario de búsqueda (Binary Search Tree) con todas las palabras de un archivo.
	 * Cada nodo contiene la frecuencia con la que la palabra aparece en dicho archivo.
	 * @param nombreArchivo el nombre del archivo el cuál será convertido a un arreglo de cadenas.
	 * @return el árbol binario de búsqueda con todas las palabras del archivo.
	 */
	public BinarySearchTree arregloArchivo(String nombreArchivo){
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
			/*Tenemos un árbol binario de búsqueda con todas las palabras del archivo.*/
			BinarySearchTree arbolPalabras = toBST(palabras, " ");
			return arbolPalabras;
		}
		catch(IOException e) {
			System.out.println("No se encontró el archivo.");
			BinarySearchTree noEncontrado = new BinarySearchTree();
			return noEncontrado;	//Regresa un arbol vacío.
		}
	}

	/**
	 * Método que calcula el TF de una palabra.
	 * @param busquedaP la palabra la cual se regresará el TF.
	 * @param archivo el árbol en donde están todas las palabras del archivo.
	 * @return el TF de esa palabra en el archivo seleccionado.
	 */
	public double getTf(String busquedaP, BinarySearchTree archivo){
		/*Poniendo a la busqueda en minúsculas y quitandole acentos y caracteres especiales.*/
		busquedaP = quitaAcentos(busquedaP);
		busquedaP = busquedaP.toLowerCase();
		int frecuencia = 0;
		if(archivo.retrieve(busquedaP) != null)
			frecuencia = (Integer) archivo.retrieveNodo(busquedaP).element;
		/*Si encontró al menos una vez la palabra, entonces va a calcular el TF con la fórmula dada. En caso de que no haya encontrado nada, regresa 0.*/
		if(frecuencia > 0)
			return (Math.log(frecuencia) / Math.log(2)) + 1;	//Calculando la fórmula log2(frecuencua) + 1
		return 0;
	}

	/**
	 * Método que calcula el IDF de una palabra.
	 * @param busquedaP la palabra a buscar en los archivos.
	 * @param listaArchivos la lista que contiene el nombre de los archivos.
	 * @return el IDF de la palabra en los archivos.
	 */
	public double getIDF(String busquedaP, LinkedList listaArchivos){
		if(listaArchivos.size() == 0)
			return 0.0;
		/*Poniendo a la busqueda en minúsculas y quitandole acentos y caracteres especiales.*/		
		busquedaP = quitaAcentos(busquedaP);
		busquedaP = busquedaP.toLowerCase();
		int contador = 0;
		String doc = "";
		/*Buscando la palabra archivo por archivo. Primero, teniendo la ruta llamamos al método arregloArchivo(String doc) para obtenerla lista de todos
		los archivos txt que hay. Después, convertimos cada archivo en una String e iteramos en éste para saber si se encuentra la palabra o no. Por último,
		se calcula el IDF.*/
		for (int i = 0; i<listaArchivos.size(); i++) {
			doc = (String)listaArchivos.obten(i);	//Se obtiene el nombre del archivo i-ésimo
			BinarySearchTree file = arregloArchivo(doc);	//Se crea el árbol con ese archivo
			if(file.retrieve(busquedaP) != null)
				contador++;
			doc = "";
		}
		if(contador == 0)
			return 0.0;
		return (Math.log((listaArchivos.size()+1) / contador) / Math.log(2));	//Calculando la fórmula log2(N+1 / N)
	}

	/**
	 * Método que calcula el peso TF-IDF de todas las palabras de un archivo, y aplica la fórmula: suma el cuadrado de todos estos pesos y
	 * le saca raíz cuadrada al resultado. 
	 * @param arbol el arbol en donde están todas las palabras del archivo.
	 * @param lista la lista de todos los archivos txt en la ruta específicada (Se obtiene con el método rutaArchivos()).
	 * @return la parte del denominador de la división final para saber la similitud del archivo dado.
	 */
	public double calculaArchivo(BinarySearchTree arbol, LinkedList lista){
		LinkedList<String> nuevo = new LinkedList<>();
		nuevo.add(0, "root");	//Se añade una palabra para evitar NullPointerException. La palabra estará al final de la lista.
		treeToList(arbol.root, nuevo);	//Se agregan los elementos de un árbol a una lista.
		nuevo.elimina(nuevo.size());	//Se elimina el elemento que habíamos metido de más.
		double sumando = 0.0;
		double tfIdf = 0.0;
		for (int i = 0; i<nuevo.size(); i++) {
			LinkedList.Nodo iterador = nuevo.cabeza;
			String busqueda = (String) iterador.elemento;
			tfIdf = (getTf(busqueda, arbol))*(getIDF(busqueda, lista));	//Sacando el peso TF-IDF
			double cuadrado = tfIdf * tfIdf;
			sumando += cuadrado;	//Sumando los cuadrados de sus pesos TF-IDF
			iterador = iterador.siguiente;
		}
		return Math.sqrt(sumando);	//Sé saca la raíz para completar la fórmula.
	}

	/**
	 * Método que llena el arreglo que tenemos como atributo (listaValores), y éste almacena de cada archivo el valor de aplicar la formula para todas las 
	 * palabras en un archivo: la raíz cuadrada de la suma de los pesos TF-IDF al cuadrado.
	 */
	public void palabrasArchivo(){
		LinkedList lista = archivosRuta();	//Obteniendo la lista de los archivos txt de la ruta dada.
		BinarySearchTree arbol;
		double valor = 0.0;
		for (int i = 0; i<lista.size(); i++) {
			LinkedList.Nodo iterador = lista.cabeza;
			String archivo = (String) iterador.elemento;
			arbol = arregloArchivo(archivo);	//Creando el árbol binario de búsqueda del archivo.
			valor = calculaArchivo(arbol, lista);	//Calculando el valor raíz cuadrada de la suma de los cuadrados de los pesos TF-IDF de las palabras de un archivo.
			listaValores[i] = valor;	//Insertando en la i-ésima posición del arreglo, el valor de ese archivo
		}
		return;
	}

	/**
	 * Método que calcula la similitud de una busqueda dada con un archivo.
	 * @param busquedaP la busqueda que hace el usuario.
	 * @param nombreArchivo el nombre del archivo.
	 * @return la similitud de ese archivo con la busqueda dada.
	 */
	public double similitud(String busquedaP, String nombreArchivo){
		BinarySearchTree arbol = arregloArchivo(nombreArchivo);	//Creando el árbol del archivo
		LinkedList lista = archivosRuta();	//Lista de todos los archivos txt de la ruta dada
		double totalContador = 0.0;
		double tfIdf = 0.0;
		String[] b = busquedaP.split(" ");	//Creando un arreglo llamadno b, que contiene las palabras del archivo
		for (int i = 0; i<b.length; i++) {	//Iterando en el arreglo de las palabras
			tfIdf = (getTf(b[i], arbol))*(getIDF(b[i], lista));	//Calculando el peso TD-IDF de las palabras
			totalContador += tfIdf;	//Sumando todos los pesos TF-IDF
		}
		int contador = 0;
		for (int j=0; j<lista.size(); j++) {	//Iterando para ver el número del archivo, y así encontrarlo en el arreglo listaValores
			LinkedList.Nodo iterador = lista.cabeza;
			if(!nombreArchivo.equals(iterador.elemento))	
				contador++;
		}
		return (totalContador)/(listaValores[contador]);	//Aplicando la fórmula para calcular la similitud de una búsqueda con un archivo.
	}

	/**
	 * Método que convierte un árbol binario de búsqueda en una lista.
	 * @param root la raíz del árbol binario de búsqueda a convertir.
	 * @param lista una lista vacía creada antes de llamar al método, para rellenarla con los elementos del árbol antes dado.
	 */
	public void treeToList(BinarySearchTree.BinaryNode root, LinkedList<String> lista){
		if(root == null)
			return;
		lista.add(0, root.element.toString());	//Se va llenando la lista.
		treeToList(root.right, lista);	//Itera en los elementos de la derecha de cada LinkedList.nodo.
		treeToList(root.left, lista); 	//Itera en los elementos de la izquierda de cada nodo.
	}

	public static void main(String[] args) {
		SearchEngine u = new SearchEngine();
		rutaArchivos = "/home/samuel_berber/Documentos";
		//System.out.println("La longitud del archivo es: " + prueba.length);
		//System.out.println("El TF de hola es: " + u.getTf("hola", prueba));
		/*for (int i = 0; i<prueba.length; i++) {
			System.out.println(prueba[i]);
		}*/
		String nuevo = "Hola Hola mundo cruel mundo mundo jejejeje sam sam ";	//A LA HORA DE LLAMAR, AGREGAR SOLO UN ESPACIO
		//BinarySearchTree<String, Integer> arbol = new BinarySearchTree<>();
		/*arbol.insert(20,"Hola");
		arbol.insert(30,"mundo");
		arbol.insert(84,"cruel");
		arbol.insert(28,"jeje");*/
		//u.toBST(nuevo, " ");
		//arbol.insert(1, "hola");
		//BinarySearchTree.BinaryNode actual = arbol.root;
		//arbol.preorden();
		BinarySearchTree arbol = u.toBST(nuevo, " ");
		//System.out.println(arbol.root.element);
		//if(arbol.root == null)
		LinkedList<String> lista = new LinkedList<>();
		u.treeToList(arbol.root, lista);
		lista.muestra();
	}
}
