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

	/*Colores*/
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String RESET = "\u001B[0m";

	/* Ruta en donde se encuentran los archivos */
	public static String rutaArchivos = "";
	/* Historial de búsquedas */
	public static LinkedList historial = new LinkedList();;
	/* Caché */
	public static Cache<LinkedList>[] cache = new Cache[10];

	/**
	 * Método que almacena un elemento en la cabeza del historial
	 * @param e el elemento a insertar
	 */
	public void insertaHistorial(T e){
		if(e == null)
			return;
		historial.add(0, e);
		return;
	}

	/**
	 * @param rutaArchivos la ruta que va a ser.
	 */
	public void setRuta(String rutaArchivos){
		rutaArchivos = rutaArchivos.replaceAll("^/+|/+$|(/)+", "/");
		this.rutaArchivos = rutaArchivos;
	}

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
	 * Método que dada una busqueda, la busca dentro de la caché. Si la encuentra dará el índice de la casilla en donde estuvo, y si no, regresa -1
	 * @param busqueda la busqueda que se buscará en la caché
	 * @return el índice de la casilla si lo encontró, y si no, regresa -1
	 */
	public int buscaCache(String busqueda){
		for (int i = 0; i<cache.length; i++) {
			if(cache[i] != null){
				if(busqueda.equals(cache[i].lista.obten(0)))
					return i;
			}
		}
		return -1;
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
		if(file.list() == null)
			return null;
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
	 * Método que calcula la similitud de una busqueda dada con un archivo.
	 * @param busquedaP la busqueda que hace el usuario.
	 * @param nombreArchivo el nombre del archivo.
	 * @return la similitud de ese archivo con la busqueda dada.
	 */
	public double similitud(String busquedaP, String nombreArchivo){
		BinarySearchTree arbol = arregloArchivo(nombreArchivo);	//Creando el árbol del archivo
		LinkedList lista = archivosRuta();	//Lista de todos los archivos txt de la ruta dada
		if(lista == null)
			return 0.0;
		double numerador = 0.0;
		double tfIdf = 0.0;
		String[] b = busquedaP.split(" ");	//Creando un arreglo llamadno b, que contiene las palabras del archivo
		for (int i = 0; i<b.length; i++) {	//Iterando en el arreglo de las palabras
			tfIdf = (getTf(b[i], arbol))*(getIDF(b[i], lista));	//Calculando el peso TD-IDF de las palabras
			numerador += tfIdf;	//Sumando todos los pesos TF-IDF
		}
		int contador = 0;
		for (int j=0; j<lista.size(); j++) {	//Iterando para ver el número del archivo, y así encontrarlo en el arreglo lista
			LinkedList.Nodo iterador = lista.cabeza;
			if(!nombreArchivo.equals(iterador.elemento))	
				contador++;
		}
		double sumando = 0.0;
		tfIdf = 0.0;
		for (int i = 0; i<lista.size(); i++) {
			LinkedList.Nodo iterador = lista.cabeza;
			String busqueda = (String) iterador.elemento;
			tfIdf = (getTf(busqueda, arbol))*(getIDF(busqueda, lista));	//Sacando el peso TF-IDF
			double cuadrado = tfIdf * tfIdf;
			sumando += cuadrado;	//Sumando los cuadrados de sus pesos TF-IDF
			iterador = iterador.siguiente;
		}
		double denominador = Math.sqrt(sumando);

		return (numerador) / (denominador);	//Aplicando la fórmula para calcular la similitud de una búsqueda con un archivo.
	}

	/**
	 * Método que dada una búsqueda, te regresa una lista con (a lo más) 10 archivos, ordenados de mayor a menor relevancia.
	 * @param busqueda la búsqueda que se realiza
	 * @return una lista con archivos ordenados de mayor a menor relevancia.
	 */
	public LinkedList listaRelevantes(String busqueda){
		LinkedList lista = archivosRuta();
		if(lista == null)
			return null;
		LinkedList<String> nuevo = new LinkedList<>();
		Double[] numeros = new Double[lista.size()];
		double sim = 0.0;
		for (int i = 0; i<lista.size(); i++) {
			String nombreArchivo = (String) lista.obten(i);
			sim = similitud(busqueda, nombreArchivo);
			numeros[i] = sim;
		}
		double mayor = numeros[0];
		int mayorEntero = 0;
		for (int x = 0; x<10; x++) {
			for (int y = 1; y<numeros.length; y++) {
				if(numeros[y] > mayor){
					mayor = numeros[y];
					mayorEntero = y;
				}
			}
			String palabra = (String) lista.obten(mayorEntero);
			if(palabra == null)
				continue;
			nuevo.add(0, palabra);
			numeros[mayorEntero] = 0.0;
			mayor = numeros[0];
			mayorEntero = 0;
		}
		nuevo = nuevo.reversa();
		nuevo.esRepetido();
		return nuevo;

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

	/**
	 * Método que voltea una cadena. Verifica que sea una ruta y no sea un tipo de archivo
	 * @param ruta la supuesta ruta.
	 */
	public boolean isRoute(String ruta){
		String reverse = new StringBuffer(ruta).reverse().toString();
		int posicion = reverse.indexOf("/");
		if(posicion	== -1)
			return false;
		String route = reverse.substring(0, posicion);
		for (int i = 0; i<route.length(); i++) {
			if(route.charAt(i) == '.')
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		SearchEngine u = new SearchEngine();
		Scanner sc = new Scanner(System.in);
		int z = 0;

		System.out.println(YELLOW + "    °***¡Bienvenido a éste motor de búsqueda, utilizando archivos!***°\n"+ CYAN +
									"A continuación, te mostraré un menú, del cuál puedes seleccionar la opción que más te guste.");
		System.out.println(GREEN + "Ingresa la ruta en la que estarán los archivos: " + YELLOW);
		String ruta = sc.nextLine();
		while(u.isRoute(ruta) == false){
			if(!u.isRoute(ruta)){	
				System.out.println(RED + "Error. No has ingresado una ruta. Ingrese una ruta correctamente." + YELLOW);
				ruta = sc.nextLine();
			}
		}
		u.setRuta(ruta);
	
    	do{
		    try{
		    	System.out.println();	
		    	System.out.println(YELLOW + "[1]" + CYAN + " Búsqueda." + GREEN + "Proporciona una búsqueda, y posteriormente te regresará los 10 archivos más relevantes de acuerdo a tu búsqueda.");
	            System.out.println(YELLOW + "[2]" + CYAN + " Historial." + GREEN + "Consulta tu historial de búsquedas recientes.");
	            System.out.println(YELLOW + "[3]" + CYAN + " Cambiar ruta." + GREEN + " Cambia la ruta de donde están los archivos.");
	            System.out.println(YELLOW + "[4]" + CYAN + " Archivos txt." + GREEN + " Muestra los archivos txt que hay en la carpeta de la ruta dada.");
	            System.out.println(YELLOW + "[0]" + CYAN + " Salir del programa." + GREEN + " ");
	            System.out.println(YELLOW + "\n\n °¿Que opción eliges? " + YELLOW);
	    		z = sc.nextInt();
	    		if(z < 0)
					z = 2147483647;
				switch(z){
		    		case 0:
						System.out.println(CYAN + "¡¡Espero haya sido de su agrado, estimado usuario!!" + YELLOW + "\n        ***Hasta la próxima***");
						break;
		    		case 1:
		    			System.out.println(CYAN + "\n°° Estimado usuario, has elegido la opción " + z + ":" + YELLOW + "\"Búsqueda\" °°");
		    			System.out.println(CYAN + "Ingresa la búsqueda que harás: " + YELLOW);
		    			sc.nextLine();
		    			String txt = sc.nextLine();
		    			txt = txt.replaceAll("\\t", " ");
						txt = txt.replaceAll("\\n", " ");
						txt = txt.replaceAll(" +| +|\t|\r|\n", " ");
		    			txt = txt + " ";
						while(txt.length() > 200){
							if(txt.length() > 200){	
								System.out.println(RED + "Error. Tú búsqueda sobrepasa los 200 palabras. Ingrese una nueva búsqueda." + YELLOW);
								sc.nextLine();
								txt = sc.nextLine();
								txt = txt.replaceAll("\\t", " ");
								txt = txt.replaceAll("\\n", " ");
								txt = txt.replaceAll(" +| +|\t|\r|\n", " ");
								txt = txt + " ";
							}
						}
						u.insertaHistorial(txt);
						LinkedList archivosRelevantes;
						int indice = u.buscaCache(txt);
						if(indice != -1)
							archivosRelevantes = cache[indice].lista;
						else{
							archivosRelevantes = u.listaRelevantes(txt);
							if(archivosRelevantes == null){
								System.out.println(RED + "No se encontró ningún archivo relevante con su búsqueda." + RESET);
								break;
							}
						u.llenaCache(archivosRelevantes, txt);
						}
						System.out.println(CYAN + "Los " + archivosRelevantes.size() + " archivos más relevantes son: " + YELLOW);
						archivosRelevantes.muestra();
		    			break;
		    		case 2:
		    			System.out.println(CYAN + "\n°° Estimado usuario, has elegido la opción " + z + ":" + YELLOW + "\"Historial\" °°");
		    			System.out.println(CYAN + "El historial de búsquedas recientes es: " + YELLOW);
		    			if(historial == null){
		    				System.out.println(RED + "Aún no hay búsquedas recientes." + RESET);
		    				break;
		    			}
		    			historial.muestra();
		    			System.out.println(RESET);
		    			break;
		    		case 3:
		    			System.out.println(GREEN + "\n°° Estimado usuario, has elegido la opción " + z + ":" + YELLOW + "\"Cambiar ruta\" °°");
		    			System.out.println(GREEN + "Ingresa la nueva ruta en la que estarán los archivos: " + YELLOW);
						sc.nextLine();
						String ruta2 = sc.nextLine();
						while(u.isRoute(ruta2) == false){
							if(!u.isRoute(ruta2)){	
								System.out.println(RED + "Error. No has ingresado una ruta. Ingrese una ruta correctamente." + YELLOW);
								ruta2 = sc.nextLine();
							}
						}
						u.setRuta(ruta2);
						cache = new Cache[10];
		    			break;
		    		case 4:
		    			System.out.println(GREEN + "\n°° Estimado usuario, has elegido la opción " + z + ":" + YELLOW + "\"Archivos txt\" °°");
		    			LinkedList arch = u.archivosRuta();
		    			if(arch == null){
		    				System.out.println(RED + " No hay elementos en la lista." + RESET);
		    				break;
		    			}
		    			System.out.print(CYAN + "Usted tiene " + YELLOW + arch.size() + CYAN+ " archivos.");
		    			System.out.println(CYAN + "Los archivos txt que hay son: " + YELLOW);
		    			arch.muestra();
		    			break;
		    		case 2147483647:
						System.out.println(RED + "¡¡¡ERRROR!!! Estimado usuario, no hay métodos negativos.");
						break;
					default:
						System.out.println(RED + "¡¡¡ERRROR!!! Estimado usuario, solo hay 4 operaciones.");
						break;
		    	}
	    	}
	    	catch(InputMismatchException ime){
			System.out.println(RED + "\nNo ingresaste un número:(");
			sc.nextLine();
			z = 2;
			}
	    } while(z > 0);
	}
}
