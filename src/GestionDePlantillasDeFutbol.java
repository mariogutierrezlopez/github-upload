import java.util.Scanner;
import java.text.Normalizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestionDePlantillasDeFutbol {
	//Estas 2 constantes van a ser necesarias para poder leer los archivos CSV
	public static final String SEPARADOR = ";";
	public static final String COMILLAS  = "\"";

	public static boolean texto(String texto) {
		boolean resultado = true;
		char [] caracteres = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z','á','é','í','ó','ú','ä','ë','ï','ö','ü',' '};
		for(int i = 0; i < texto.length() && resultado; i++){
			boolean encontrado = false;
			for(int j = 0; j < caracteres.length && !encontrado; j++) { //EJECUTA EL CODIGO COMPROBANDO MINÚSCULAS
				if(texto.charAt(i) == caracteres[j])
					encontrado = true;
			}
			for(int j = 0; j < caracteres.length && !encontrado; j++) { //EJECUTA EL CODIGO COMPROBANDO MAYÚSCULAS
				//FUNCIÓN EXPLICADA EN LA DOCUMENTACIÓN
				if(texto.charAt(i) == java.lang.Character.toUpperCase(caracteres[j]))
					encontrado = true;
			}
			if(!encontrado)
				resultado = false;
		}
		return resultado;
	}
	
	public static boolean numeros(String texto) {
		boolean resultado = true;
		char [] numeros = {'0','1','2','3','4','5','6','7','8','9'};
		for(int i = 0; i < texto.length() && resultado; i++){
			boolean encontrado = false;
			for(int j = 0; j < numeros.length && !encontrado; j++) { //EJECUTA EL CODIGO COMPROBANDO NÚMEROS
				if(texto.charAt(i) == numeros[j])
					encontrado = true;
			}
			if(!encontrado)
				resultado = false;
		}
		return resultado;
	}
	public static String normalizar(String cadena) {
		//Esta función elimina todas las tildes del string introducido, fuente en la bibliografía
		String resultado = Normalizer.normalize(cadena, Normalizer.Form.NFD);
		return resultado.replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static class jugador{
		int dorsal;
		String nombre;
		String apellido;
		int edad;
		int altura; //numero entero expresado en cm
		int peso; //expresado en kilogramos
		String genero;
		String posiciones [] = new String [4];
		int posicionTabla [] = new int[2];
	} //RegistroJugador
	
	public static class equipo{
		String nombre;
		String estadio;
		jugador[] jugador = new jugador[11];	
		boolean [][] posicion = new boolean[6][5];
		
		public static equipo leerEquipo (equipo equipo){
			Scanner in = new Scanner(System.in);
			String textoIntroducido;
			BufferedReader br = null;
		    do {
		    	System.out.println("Escribe el nombre del archivo (ejemplo.csv)");
		    	textoIntroducido = in.nextLine();
		    }while(textoIntroducido == "");
			
		      try {
		         
		         br =new BufferedReader(new FileReader(textoIntroducido));
		         String line = br.readLine();
		         int contador = 1;
		         while (null!=line) {
		            String [] fields = line.split(";");
		            if(contador == 1)
		            	equipo.nombre = fields[0];
		            if(contador == 2)
		            	equipo.estadio = fields[0];
		            if(contador >= 3 && contador <= 13) {
		            	equipo.jugador[contador-3]=new jugador();
		            	equipo.jugador[contador-3].dorsal = Integer.parseInt(fields[0]);
		            	equipo.jugador[contador-3].nombre = normalizar(fields[1].toUpperCase());
		            	equipo.jugador[contador-3].apellido = normalizar(fields[2].toUpperCase());
		            	equipo.jugador[contador-3].edad = Integer.parseInt(fields[3]);
		            	equipo.jugador[contador-3].altura = Integer.parseInt(fields[4]);
		            	equipo.jugador[contador-3].peso = Integer.parseInt(fields[5]);
		            	equipo.jugador[contador-3].genero = normalizar(fields[6].toUpperCase());
		            	equipo.jugador[contador-3].posiciones[0] = normalizar(fields[7]).toUpperCase();
		            }
		           
		            contador++;
		            line = br.readLine();
		         }
		         int contP = 0;
		         int contDef = 0;
		         int contM = 0;
		         int contDel = 0;
		         System.out.println("Escriba la posición en la que quieres guardar a los jugadores: ");
		         for(int i = 0; i < equipo.jugador.length; i++) {
		        	 if(equipo.jugador[i].posiciones[0].equals("PORTERIA"))
		        		 contP++;
		        	 if(equipo.jugador[i].posiciones[0].equals("DEFENSA"))
		        		 contDef++;
		        	 if(equipo.jugador[i].posiciones[0].equals("MEDIOCAMPO"))
		        		 contM++;
		        	 if(equipo.jugador[i].posiciones[0].equals("DELANTERA"))
		        		 contDel++;
		         }
		         
		         System.out.println(contP + " portero(s)\n" + contDef + " defensa(s)\n" + contM + " mediocentro(s)\n" + contDel + " delantero(s)\n");
		         System.out.println("AVISO: NO PUEDE HABER MÁS DE 1 PORTERO NI MÁS DE 5 JUGADORES POR FILA,\nSI METES A UN JUGADOR"
		         		+ "EN OTRA FILA FUERA DE SU POSICIÓN, ESTARÍA \"FUERA DE POSICIÓN\" ");
		         boolean posicionRegistrada = false;
		         for(int i = 0; i < equipo.jugador.length; i++) {
		        	 do {
		        		 posicionRegistrada = false;
		        		 System.out.println((i+1)+": Escribe las posiciones de: " + equipo.jugador[i].nombre + ", " + equipo.jugador[i].posiciones[0]);
			        	 
			        	 do {
			        		 System.out.println("Posición Y: 1- Delantero, 2-4 Mediocampo, 5- Defensa, 6- Portero(1-6):");
			        		 textoIntroducido = in.nextLine();
			        		 if(!numeros(textoIntroducido))
			        			 System.out.println("Introduce números");
			        		 
			        		 else {
			        			 equipo.jugador[i].posicionTabla[0]= Integer.parseInt(textoIntroducido);
			        			 if( equipo.jugador[i].posicionTabla[0] < 1 ||  equipo.jugador[i].posicionTabla[0] > 6)
			        				 System.out.println("Valor fuera de rango");
			        		 }
			        	 }while(!numeros(textoIntroducido)|| equipo.jugador[i].posicionTabla[0] < 0 || equipo.jugador[i].posicionTabla[1] > 6);
			        	 
			        	 do {
			        		 if(equipo.jugador[i].posicionTabla[0] == 6) {
			        			 System.out.println("La coordenada X va a ser 3 porque es portero");
			        			 equipo.jugador[i].posicionTabla[1] = 3;
			        		 }else {
			        			 System.out.println("Posición X: 1-5");
				        		 textoIntroducido = in.nextLine();
				        		 if(!numeros(textoIntroducido))
				        			 System.out.println("Introduce números");
				        		 
				        		 else {
				        			 equipo.jugador[i].posicionTabla[1]= Integer.parseInt(textoIntroducido);
				        			 if( equipo.jugador[i].posicionTabla[1] < 1 ||  equipo.jugador[i].posicionTabla[1] > 5)
				        				 System.out.println("Valor fuera de rango");
				        		 }
			        		 }
			        		 
			        	 }while(!numeros(textoIntroducido)|| equipo.jugador[i].posicionTabla[0] < 0 || equipo.jugador[i].posicionTabla[1] > 5);
			        	 if(equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1]) {
			        		 System.out.println("Ya hay un jugador guardado en esa posición");
			        		 posicionRegistrada = true;
			        	 }
			        	 if(!equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1]) {
			        		 equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1] = true;
			        		 System.out.println("Posición guardada correctamente");
			        		 
			        	 }
			        	 
		        	 }while(posicionRegistrada);
		        	 
		        	
		         }
		         System.out.println("Ajustes guardados correctamente");
		      } catch (Exception e) {
		         System.out.println("Error: " + e.getMessage());
		      } finally {
		         if (null!=br) {
		            try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		         }
		      }
		      return equipo;
		}
		
		public static void guardarEquipo(equipo equipo) {
			Scanner in = new Scanner(System.in);
			System.out.println("Escriba el nombre del fichero para guardarlo: (ejemplo.csv)");
			String textoIntroducido = in.nextLine();
			try {
				File yourFile = new File(textoIntroducido);
				yourFile.createNewFile(); // if file already exists will do nothing 
			}catch(Exception ex){
				System.out.println("Has introducido un nombre de archivo erróneo");
				System.out.println(ex.getMessage());
			}
			
			FileWriter escribir = null;
			try {
				escribir = new FileWriter(textoIntroducido);
				
				//Guardamos la alineación
				escribir.write(equipo.nombre + "\n");
				escribir.write(equipo.estadio + "\n");
				
				for(int i = 0; i < equipo.jugador.length; i++) {
					escribir.write(equipo.jugador[i].dorsal + ";");
					escribir.write(equipo.jugador[i].nombre + ";");
					escribir.write(equipo.jugador[i].apellido + ";");
					escribir.write(equipo.jugador[i].edad + ";");
					escribir.write(equipo.jugador[i].altura+ ";");
					escribir.write(equipo.jugador[i].peso + ";");
					escribir.write(equipo.jugador[i].genero + ";");
					escribir.write(equipo.jugador[i].posiciones[0]);
					
					escribir.write("\n");
				
				}
				escribir.close();
			}
			catch(Exception ex) {
				System.out.println("No hay ninguna alineación guardada en memoria");
				System.out.println("Error: " + ex.getMessage());
			}
			
		}
	
		public static void imprimirEquipo(equipo equipo) {
			try {
				System.out.println("El equipo almacenado en memoria es el siguiente: ");
				for(int i = 0; i < equipo.posicion.length; i++) {
					for(int j = 0; j < equipo.posicion[0].length; j++) {
			
						if(equipo.posicion[i][j]) {
							for(int k = 0; k < equipo.jugador.length; k++) {
								if(equipo.jugador[k].posicionTabla[0]-1 == i && equipo.jugador[k].posicionTabla[1]-1 == j)
									System.out.print(equipo.jugador[k].dorsal);
								
							}
						}
						else
							System.out.print(" ");
						System.out.print("\t");
					}
					System.out.println();
				}
				
			System.out.println("Nombres y dorsales: ");	
			for(int i = 0; i < equipo.jugador.length; i++) {
				System.out.println("Dorsal: " + equipo.jugador[i].dorsal + ", Nombre: " + equipo.jugador[i].nombre + " " + equipo.jugador[i].apellido);
				
			}
			}catch(Exception ex) {
				System.out.println("Error: " + ex.getMessage() + "\nFaltan datos por introducir");
			}
		}
		
		public static equipo crearEquipo(equipo equipo){
			String textoIntroducido;
			Scanner in = new Scanner(System.in); //Abre el teclado
			
			System.out.println("Opción 3: Crear una alineaciÃ³n desde teclado");
			do {//NOMBRE EQUIPO
				System.out.println("Escribe el nombre del equipo: ");
				textoIntroducido = in.nextLine();
				equipo.nombre = normalizar((textoIntroducido).toUpperCase());
				if(!texto(textoIntroducido))
					System.out.println("Error, vuelve a introducir el dato");
			}while(!texto(textoIntroducido));
			
			do {//NOMBRE EQUIPO
				System.out.println("Escribe el nombre del estadio: ");
				textoIntroducido = in.nextLine();
				equipo.estadio = normalizar((textoIntroducido).toUpperCase());
				if(!texto(textoIntroducido))
					System.out.println("Error, vuelve a introducir el dato");
			}while(!texto(textoIntroducido));
			
			for(int i = 0; i < equipo.jugador.length; i++) {
				equipo.jugador[i] = new jugador();
				do {//DORSAL JUGADOR
					System.out.println("Escribe el dorsal del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					if(numeros(textoIntroducido))
						equipo.jugador[i].dorsal = Integer.parseInt(textoIntroducido);
					if(!numeros(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!numeros(textoIntroducido));
				
				do {//NOMBRE JUGADOR
					System.out.println("Escribe el nombre del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					equipo.jugador[i].nombre = normalizar((textoIntroducido).toUpperCase());
					if(!texto(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!texto(textoIntroducido));
				
				do {//APELLIDO JUGADOR
					System.out.println("Escribe el primer apellido del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					equipo.jugador[i].apellido = normalizar((textoIntroducido).toUpperCase());
					if(!texto(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!texto(textoIntroducido));

				do {//EDAD JUGADOR
					System.out.println("Escribe la edad del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					if(numeros(textoIntroducido))
						equipo.jugador[i].edad = Integer.parseInt(textoIntroducido);
					if(!numeros(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!numeros(textoIntroducido));
				
				do {//PESO JUGADOR
					System.out.println("Escribe el peso del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					if(numeros(textoIntroducido))
						equipo.jugador[i].peso = Integer.parseInt(textoIntroducido);
					if(!numeros(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!numeros(textoIntroducido));
				
				do {//GÉNERO JUGADOR
					System.out.println("Escribe el género del jugador " + (i+1) + "(HOMBRE) o (MUJER) ");
					textoIntroducido = in.nextLine();
					equipo.jugador[i].genero = normalizar((textoIntroducido).toUpperCase());
					if(!texto(textoIntroducido))
						System.out.println("Error, tipo de dato incorrecto");
					if(!(equipo.jugador[i].genero.equals("HOMBRE") || equipo.jugador[i].genero.equals("MUJER"))) {
						System.out.println("Error, has introducido otra palabra");
					}
				}while(texto(textoIntroducido)==false || !(normalizar((textoIntroducido).toUpperCase()).equals("HOMBRE") || normalizar((textoIntroducido).toUpperCase()).equals("MUJER")));
				boolean posicionRegistrada;
				do {//ALTURA JUGADOR
					System.out.println("Escribe la altura del jugador " + (i+1) + ": ");
					textoIntroducido = in.nextLine();
					if(numeros(textoIntroducido))
						equipo.jugador[i].altura = Integer.parseInt(textoIntroducido);
					if(!numeros(textoIntroducido))
						System.out.println("Error, vuelve a introducir el dato");
				}while(!numeros(textoIntroducido));
				
				/*Introduce todos los valores de equipo.posicion en false y cambia las distintas posiciones a true
				 * Hacemos esto para que a la hora de crear distintas posiciones, no metamos a 2 jugadores en la misma posición
				 */
				do {
					posicionRegistrada = false;
					do {//REGISTRAR DECISIÓN	
						System.out.println("Escribe la posición PRINCIPAL del jugador " + (i+1) +  ": PORTERÍA, DEFENSA, MEDIOCAMPO, DELANTERA");
						textoIntroducido = in.nextLine();
						equipo.jugador[i].posiciones[0] = normalizar((textoIntroducido).toUpperCase());
						if(!texto(textoIntroducido))
							System.out.println("Error en el tipo de dato introducido");
						if(!(equipo.jugador[i].posiciones[0].equals("PORTERIA") || equipo.jugador[i].posiciones[0].equals("DEFENSA") || equipo.jugador[i].posiciones[0].equals("MEDIOCAMPO") || equipo.jugador[i].posiciones[0].equals("DELANTERA")) && texto(textoIntroducido)){
							System.out.println("Has introducido la palabra de manera errónea");
						}
					}while(!texto(textoIntroducido) || (!(equipo.jugador[i].posiciones[0].equals("PORTERIA") || equipo.jugador[i].posiciones[0].equals("DEFENSA") || equipo.jugador[i].posiciones[0].equals("MEDIOCAMPO") || equipo.jugador[i].posiciones[0].equals("DELANTERA"))));
					//Convertimos a mayúsculas
					if(equipo.jugador[i].posiciones[0].equals("PORTERIA")) {
						equipo.jugador[i].posicionTabla[0] = 6;
						equipo.jugador[i].posicionTabla[1] = 3;
						if(equipo.posicion[5][2] == true)
							posicionRegistrada = true;
					}
					if(equipo.jugador[i].posiciones[0].equals("DEFENSA")) {
						equipo.jugador[i].posicionTabla[0] = 5;
						do {
							System.out.println("Fila 5, Escribe la columna [1,5]: ");
							equipo.jugador[i].posicionTabla[1] = Integer.parseInt(in.nextLine());
						}while(!((equipo.jugador[i].posicionTabla[1] >= 1)&&(equipo.jugador[i].posicionTabla[1] <= 5)));
						if(equipo.posicion[4][equipo.jugador[i].posicionTabla[1]-1] == true)
							posicionRegistrada = true;
					}
					if(equipo.jugador[i].posiciones[0].equals("MEDIOCAMPO")) {
						equipo.jugador[i].posicionTabla[0] = 3;
						do {
							System.out.println("Fila 3, Escribe la columna [1,5]: ");
							equipo.jugador[i].posicionTabla[1] = Integer.parseInt(in.nextLine());
						}while(!((equipo.jugador[i].posicionTabla[1] >= 2)&&(equipo.jugador[i].posicionTabla[1] <= 4)));
						if(equipo.posicion[2][equipo.jugador[i].posicionTabla[1]-1] == true)
							posicionRegistrada = true;
					}
					if(equipo.jugador[i].posiciones[0].equals("DELANTERA")) {
						equipo.jugador[i].posicionTabla[0] = 1;
						do {
							System.out.println("Fila 1, Escribe la columna [1,5]: ");
							equipo.jugador[i].posicionTabla[1] = Integer.parseInt(in.nextLine());
						}while(!((equipo.jugador[i].posicionTabla[1] > 0)&&(equipo.jugador[i].posicionTabla[1] < 6)));
						if(equipo.posicion[0][equipo.jugador[i].posicionTabla[1]-1] == true)
							posicionRegistrada = true;
					}
					

					if(posicionRegistrada) {
						System.out.println("Esta posición ya ha sido cogida por otro jugador, vuelve a repetir el proceso");
					}else {
						equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1] = true;
						System.out.println("Posición guardada de manera satisfactoria");
					}
					System.out.println();
				}while(posicionRegistrada); //Final dowhile posiciones
				
				System.out.println("*** Jugador " + (i+1) + " creado con éxito ***");
				//Cierra el teclado
				
			} //bucle for case 3
			return equipo;
		} //crearEquipo()
		
		public static void validarEquipo(equipo equipo) { //validar equipo()
			try{
				System.out.println("Para que un equipo sea valido tiene que cumplir las siguientes condiciones:");
				System.out.println("1- Solo puede haber un portero en la posición 6,3");
				System.out.println("2- El máximo de individuos por línea es 5");
				System.out.println("3- Debe haber 11 jugadores\n");
				System.out.println("4- El dorsal y la edad de los jugadores tiene que estar entre 1 y 99");
				System.out.println("5- No se repite ningún dorsal");
				boolean equipoCorrecto = true;
				/*Condición 1: Tiene que haber un solo portero en la posición 6,3
				 * Posibles errores:
				 * 		1 El portero no está en la posición 6,3
				 * 		2 Hay más de un portero en el equipo
				*/
				int contPorteros = 0;
				int i = 0;
				do {
					if(equipo.jugador[i].posiciones[0] == "PORTERIA") {
						contPorteros++;
						//Este problema no va a suceder ya que asignamos la posiciónTabla de portero directamente cuando se crea el equipo. NO AL CARGARLO
						if(equipo.jugador[i].posicionTabla[0] != 6 && equipo.jugador[i].posicionTabla[1] != 3) {
							System.out.println("PROBLEMA: El portero " + equipo.jugador[i].nombre + " no está en la posición 6 , 3");
							equipoCorrecto = false;
						}
							
					}
					i++;
				}while(contPorteros < 2 && i < 11);
				if(contPorteros > 1) {
					System.out.println("PROBLEMA: Se encontró más de un portero");
					equipoCorrecto = false;
				}
				//Condición 2:
				/*No hay más de un jugador en la misma posición, este error nunca va a suceder ya que a la hora de crear por teclado o de 
				 * importar por fichero, nos va a pedir que añadamos una posición que no esté siendo utilizada por otros jugadores
				 * 
				 * */
				for(i = 0; i < equipo.posicion.length; i++) {
					for(int j = 0; j < equipo.posicion[0].length; j++) {
						equipo.posicion[i][j] = false;
					}
				}
				for(i = 0; i < equipo.jugador.length; i++) {
					if(equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1]) {
						equipoCorrecto = false;
						System.out.println("Hay un fallo con el jugador " + equipo.jugador[i].nombre + "compartiendo la posicion " + equipo.jugador[i].posicionTabla[0] + " " + equipo.jugador[i].posicionTabla[1]);
					}
					equipo.posicion[equipo.jugador[i].posicionTabla[0]-1][equipo.jugador[i].posicionTabla[1]-1] = true;
						
				}
				
				
				//Condición 3: Esta condición se va a cumplir siempre ya que el array jugadores va a almacenar solo a 11 jugadores
				//el código para comprobarlo sería el siguiente:
				if(equipo.jugador.length != 11) {
					System.out.println("Hay más de 11 jugadores en el equipo");
					equipoCorrecto = false;
				}
				
				//Condición 4: Edad y dorsal menor que 1 o mayor que 99
				for(int j = 0; j < equipo.jugador.length; j++) {
					if(equipo.jugador[j].dorsal < 1 || equipo.jugador[j].dorsal > 99)
						System.out.println("El jugador " + equipo.jugador[j].nombre + " tiene un dorsal inválido: " + equipo.jugador[j].dorsal);
					if(equipo.jugador[j].edad < 1 || equipo.jugador[j].edad > 99)
						System.out.println("El jugador " + equipo.jugador[j].nombre + " tiene una edad inválida: " + equipo.jugador[j].edad);
				}
				
				//Condición 5: No se repite ningún dorsal
				boolean [] dorsales = new boolean[100];
				for(int j = 0; j < equipo.jugador.length; j++) {
					if(!dorsales[equipo.jugador[j].dorsal-1])
						dorsales[equipo.jugador[j].dorsal-1] = true;
					else {
						equipoCorrecto = false;
						System.out.println("El dorsal " + equipo.jugador[j].dorsal + " está repetido");
					}
						
				}
				
				//Jugadores fuera de posicion
				for(int k = 0; k < equipo.jugador.length; k++) {
					if(equipo.jugador[k].posiciones[0].equals("PORTERIA") && equipo.jugador[k].posicionTabla[0] != 6) {
						System.out.println(equipo.jugador[k].nombre + " " + equipo.jugador[k].apellido + " está fuera de posición");
						equipoCorrecto = false;
					}
						
					if(equipo.jugador[k].posiciones[0].equals("DEFENSA") && equipo.jugador[k].posicionTabla[0] != 5) {
						System.out.println(equipo.jugador[k].nombre + " " + equipo.jugador[k].apellido + " está fuera de posición");
						equipoCorrecto = false;
					}
						
					if(equipo.jugador[k].posiciones[0].equals("MEDIOCAMPO") && (equipo.jugador[k].posicionTabla[0] > 4 || equipo.jugador[k].posicionTabla[0] < 2)) {
						System.out.println(equipo.jugador[k].nombre + " " + equipo.jugador[k].apellido + " está fuera de posición");
						equipoCorrecto = false;
					}
						
					if(equipo.jugador[k].posiciones[0].equals("DELANTERA") && equipo.jugador[k].posicionTabla[0] != 1) {
						System.out.println(equipo.jugador[k].nombre + " " + equipo.jugador[k].apellido + " está fuera de posición");
						equipoCorrecto = false;
					}
						
				}
				
				//Si el equipo es correcto, se imprime que lo es
				if(equipoCorrecto)
					System.out.println("El equipo es correcto, no se encuentran fallos");
				else
					System.out.println("Se han encontrado fallos en la alineación");
			}catch(Exception ex) {
				System.out.println("Faltan datos por introducir");
			}
		}//validar equipo()
		
		public static void estadisticasEquipo(equipo equipo) {
			Scanner in = new Scanner(System.in);
			try {
				int decision = -1;
				String textoIntroducido;
				do {
					
					do {
						System.out.println("Las funciones posibles son las siguientes: ");
						System.out.println("1- Calcular la edad media");
						System.out.println("2- Jugador más joven");
						System.out.println("3- Calcular la relación entre hombres y mujeres");
						System.out.println("4- Buscar un jugador a partir del nombre");
						System.out.println("5- Nombres posibles fuera de posición");
						System.out.println("6- Ver la alineación del equipo");
						System.out.println("0- Salir al menú principal");
						//EDAD MEDIA
						textoIntroducido = in.nextLine();
						if(numeros(textoIntroducido))
							decision = Integer.parseInt(textoIntroducido);
						else {
							System.out.println("Introduce un número correcto");
						}
					}while(!numeros(textoIntroducido) || decision < 0 || decision > 7);
					
					
					switch(decision) {
						case 1:
							float edadMedia = 0;
							for(int i = 0; i < equipo.jugador.length; i++) {
								edadMedia = edadMedia + equipo.jugador[i].edad;
							}
							edadMedia = (float) edadMedia / equipo.jugador.length;
							System.out.println("La edad media del equipo es de " + edadMedia);
							break;
						case 2: //Jugador/a más joven
							int contI = 0;
							for(int i = 0; i < equipo.jugador.length; i++) {
								if(equipo.jugador[i].edad < equipo.jugador[contI].edad) {
									contI = i;
								}
							}
							System.out.println("El jugador más joven es " + equipo.jugador[contI].nombre + " " + equipo.jugador[contI].apellido);
							break;
						case 3: //Ratio hombres-mujeres en el equipo
							int contHombres = 0;
							for(int i = 0; i < equipo.jugador.length; i++) {
								if(equipo.jugador[i].genero.equals("HOMBRE"))
									contHombres++;
							}
							float relacionHM = (float)contHombres/equipo.jugador.length;
							System.out.println("El " + (relacionHM*100) + "% son hombres y el " + (100-(relacionHM*100)) + "% son mujeres");
							break;
						case 4: //Buscar jugador a partir de nombre dado
							String [] nombreJugador = new String [2];
							System.out.println("Escribe el nombre del jugador: ");
							nombreJugador[0] = normalizar((in.nextLine()).toUpperCase()); //Cambiamos todo a mayúsculas y eliminamos tildes
							System.out.println("Escribe el apellido del jugador: ");
							nombreJugador[1] = normalizar((in.nextLine()).toUpperCase()); //Cambiamos todo a mayúsculas y eliminamos tildes
							boolean encontrado = false;
							for(int i = 0; i < equipo.jugador.length; i++) {
								if((nombreJugador[0].equals(equipo.jugador[i].nombre)) && (nombreJugador[1].equals(equipo.jugador[i].apellido))) {
									System.out.println("Se ha encontrado a un jugador en la alineación");
									System.out.println("Posición: " + equipo.jugador[i].posiciones[0]);
									System.out.println("Dorsal: " + equipo.jugador[i].dorsal);
									encontrado = true;
								}
							}
							if(!encontrado) {
								System.out.println("No se ha encontrado al jugador: " + nombreJugador[0] + " " + nombreJugador[1]);
							}
							break;
						case 5: //NO TERMINO DE ENTENDER QUE ES ESTO Nombres de posibles jugadores fuera de posición -----> significa que todos los jugadores tienen una posicion principal y pueden tener otra en la que tambien juega pero no es natural--> Ejemplo: Fernando --> Defensa y mediocentro si en la alineacion sale de mediocentro, el jugador esta fuera de posicion
							for(int i = 0; i < equipo.jugador.length; i++) {
								if(equipo.jugador[i].posiciones[0].equals("PORTERIA") && equipo.jugador[i].posicionTabla[0] != 6)
									System.out.println(equipo.jugador[i].nombre + " " + equipo.jugador[i].apellido + " está fuera de posición");
								if(equipo.jugador[i].posiciones[0].equals("DEFENSA") && equipo.jugador[i].posicionTabla[0] != 5)
									System.out.println(equipo.jugador[i].nombre + " " + equipo.jugador[i].apellido + " está fuera de posición");
								if(equipo.jugador[i].posiciones[0].equals("MEDIOCAMPO") && (equipo.jugador[i].posicionTabla[0] > 4 || equipo.jugador[i].posicionTabla[0] < 2))
									System.out.println(equipo.jugador[i].nombre + " " + equipo.jugador[i].apellido + " está fuera de posición");
								if(equipo.jugador[i].posiciones[0].equals("DELANTERA") && equipo.jugador[i].posicionTabla[0] != 1)
									System.out.println(equipo.jugador[i].nombre + " " + equipo.jugador[i].apellido + " está fuera de posición");
							}
							break;
						case 6: //Ver la alineación
							System.out.println("Alineación del equipo");
							int contPortero = 0; //Se da por hecho que puede haber error de alineación, para eso está la cuarta función del programa
							int contDefensa = 0;
							int contMediocampo = 0;
							int contDelantero = 0;
							for(int i = 0; i < equipo.jugador.length; i++) {
								if(equipo.jugador[i].posiciones[0] == "PORTERIA")
									contPortero++;
								if(equipo.jugador[i].posiciones[0] == "DEFENSA")
									contDefensa++;
								if(equipo.jugador[i].posiciones[0] == "MEDIOCAMPO")
									contMediocampo++;
								if(equipo.jugador[i].posiciones[0] == "DELANTERA")
									contDelantero++;
							}
							System.out.println("Portero: " + contPortero + ", Defensa: " + contDefensa +
												", Mediocampo: " + contMediocampo + ", Delantero: " + contDelantero);
							
							System.out.println("El equipo guardado es el siguiente: ");
							GestionDePlantillasDeFutbol.equipo.imprimirEquipo(equipo);
							break;
					
					}//Final del switch

					


				}while(decision != 0);
			}catch(Exception ex) {
				System.out.println("Faltan datos por introducir");
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
			
		}
	} // clase Equipo
	

	public static void main(String args[]) {
	
		System.out.println("Bienvenido al simulador de gestión de plantillas creado por: Mario Gutilérrez y Luis Cabello.\n");
		

		equipo equipo = new equipo();
		
		while(true) {
			Scanner in = new Scanner(System.in);
			String textoEscrito;
			int decision;
			do {
				System.out.println("¿Que quieres hacer?");
				System.out.println("1- Cargar un equipo ");
				System.out.println("2- Guardar una alineación");
				System.out.println("3- Crear una alineación desde teclado");
				System.out.println("4- Validar una alineación cargada");
				System.out.println("5- Calcular estadísticas del equipo cargado");
				System.out.println("6- Imprimir el equipo actual");
				textoEscrito = in.nextLine();
				
				decision = 0;
				if(!numeros(textoEscrito) || textoEscrito.isBlank())
					System.out.println("Introduce un número correcto");
				else
				//	decision = Integer.parseInt(textoEscrito);
					decision = Integer.parseInt(textoEscrito);
			
			}while(!numeros(textoEscrito) || Integer.parseInt(textoEscrito) < 1 || Integer.parseInt(textoEscrito) > 6);
			
			
			switch(decision){
			
			case 1:
				System.out.println("Opción 1: Cargar un equipo");
				equipo = GestionDePlantillasDeFutbol.equipo.leerEquipo(equipo);
				break;
				
			case 2:
				System.out.println("Opción 2: Guardar una alineación");
				GestionDePlantillasDeFutbol.equipo.guardarEquipo(equipo);
				break;
				
			case 3:
				System.out.println("Opción 3: Crear alineación por teclado");
				equipo = GestionDePlantillasDeFutbol.equipo.crearEquipo(equipo);
				break;
				
			case 4:
				System.out.println("Opción 4: Validar una alineación cargada");
				GestionDePlantillasDeFutbol.equipo.validarEquipo(equipo);
				break;
				
			case 5:
				System.out.println("Opción 5: Calcular estadísticas del equipo cargado");
				GestionDePlantillasDeFutbol.equipo.estadisticasEquipo(equipo);
				break;
			case 6:
				System.out.println("Opción 6: Imprimir el equipo actual");
				GestionDePlantillasDeFutbol.equipo.imprimirEquipo(equipo);
			} //switch
			
			System.out.println();
		}
		//bucle while true
		
	} //Static void main
	
} //GestionDePlantillasDeFutbol
