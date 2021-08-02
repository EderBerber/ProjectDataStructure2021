import java.util.Scanner;
/**
 * Programa que simula una caché
 * @author Eder Samuel Berber Gutiérrez
 * @version 1.0 Julio 2021
 * @since proyecto global, ED 2021-2
 */
 public class Prueba<T> extends DoubleLinkedList<T>{

 	public static class Cache<T>{
 		/*Atributo de clase*/
 		static DoubleLinkedList lista;

 		/**
 		 * Método que agregue datos a una lista
 		 * @param elemento el elemento a agregar a una lista
 		 */
 		public DoubleLinkedList agregaCache(T elemento){
 			DoubleLinkedList listaNueva = new DoubleLinkedList();
 			listaNueva.add(0, elemento);
 			return listaNueva;
 		}

 		/**
 		 * Agrega un elemento a una lista
 		 * @param lista la lista a agregar
 		 * @param elemento el elemento a agregar
 		 * @param posicion la posición en la cual se va a agregar
 		 */
 		public void agregaLista(DoubleLinkedList lista, T elemento, int posicion){
 			lista.add(posicion, elemento);
 			return;
 		}
 	}

/**
	 * Método que voltea una cadena. Verifica que sea una ruta y no sea un tipo de archivo
	 * @param ruta la supuesta ruta.
	 */
	public boolean isRoute(String ruta){
		String reverse = new StringBuffer(ruta).reverse().toString();
		int posicion = reverse.indexOf("/");
		String route = reverse.substring(0, posicion);
		for (int i = 0; i<route.length(); i++) {
			if(route.charAt(i) == '.')
				return false;
		}
		return true;
	}

 	public static void main(String[] args) {

 		Cache<DoubleLinkedList>[] cache = new Cache[10];
 		Cache cacheObj = new Cache();
 		Prueba prueba = new Prueba();
 		Scanner sc = new Scanner(System.in);

 		cache[2].lista = cacheObj.agregaCache("Hola");
 		cacheObj.agregaLista(cache[2].lista, "mundo", 0);
 		cacheObj.agregaLista(cache[2].lista, "cruel", 0);
 		cache[2].lista.show();
 		//System.out.println("El tamaño de la lista es: " + cache[2].lista.size());
 		String ruta = "/archivo.pdf";
 		String ruta2 = "/jajajaja";
 		//System.out.println("¿Es ruta? " + prueba.isRoute(ruta));
 		//System.out.println("¿Es ruta? " + prueba.isRoute(ruta2));

 		
 		System.out.println("Ingresa la ruta en la que estarán los archivos: ");
		String rutaPalabra = sc.nextLine();
		//boolean esRuta = prueba.isRoute(ruta);
		while(prueba.isRoute(rutaPalabra) == false){
		if(!prueba.isRoute(rutaPalabra)){
			System.out.println("Error. No has ingresado una ruta. Ingrese una ruta correctamente.");
			rutaPalabra = sc.nextLine();
		}
			//esRuta = prueba.isRoute(ruta2);

		}
 	}
 }