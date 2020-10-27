package it.corso.accenture.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.Predicate;

import it.corso.accenture.entities.Contatto;


public class Main {

	static String filepath = "C:\\Users\\ignaa\\Desktop\\contatti.txt";
	static ObjectOutputStream objectOutput;
	static ObjectInputStream objectInput;
	static HashSet<Contatto> contatti = new HashSet<Contatto>();

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		int scelta;
		
		Scanner scanner = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		Main main = new Main();
		aggiornaListaIniziale();

		do {
			System.out.println("\nRUBRICA\n "
					+ "\n1 - Visualizza elenco contatti."
					+ "\n2 - Inserisci contatto."
					+ "\n3 - Rimuovi contatto."
					+ "\n4 - Modifica contatto."
					+ "\n5 - Ricerca per nome"
					+ "\n6 - Ricerca per numero"
					+ "\n7 - ESCI!");
			
			scelta = scanner.nextInt();
			
			switch (scelta) {
			
				case 1: {
					readFile();
				}
				break;
				
				case 2: {
					System.out.println("\nInserisci nome:");
					String nome = scanner2.nextLine();
					System.out.println("Inserisci numero: ");
					String numero = scanner2.nextLine();
					Contatto contatto = new Contatto(nome, numero);
					contatti.add(contatto);
					writeToFile(contatti);					
				}
				break;
				
				case 3: {
					readFile();
					System.out.println("Inserisci il nome del contatto che vorresti eliminare: ");
					String nomeContatto = scanner2.nextLine();	
					main.eliminaContatto(contatti, c -> c.getNome().equals(nomeContatto));
					System.out.println("Il contatto " + nomeContatto + " è stato eliminato!!");
				}
				break;
				
				case 4: {
					readFile();
					System.out.println("Inserisci il nome del contatto che vorresti modificare: ");
					String nomeString = scanner2.nextLine();
					System.out.println("Inserisci il nuovo nome: ");
					String nome = scanner2.nextLine();
					System.out.println("Inserisci il nuovo numero: ");
					String numero = scanner2.nextLine();
								
					main.modificaContatto(contatti, c -> c.getNome().equals(nomeString), nome, numero);	
					
					System.out.println("Il contatto è stato modificato!" );
				}
				break;
				
				case 5: {
					System.out.println("\nInserisci il nome del contatto che vorresti cercare: ");
					String nome = scanner2.nextLine();
					main.ricercaNome(contatti, c -> c.getNome().equals(nome));					
				}
				break;
				
				case 6: {
					System.out.println("\nInserisci il numero del contatto che vorresti cercare: ");
					String numero = scanner2.nextLine();
					main.ricercaNome(contatti, c -> c.getNumero().equals(numero));					
				}
				break;
				
			}
			
		} while (scelta != 7);

		scanner.close();
		scanner2.close();
	}

	//Metodo che mi permette di scrivere gli oggetti su file
	public static void writeToFile(HashSet<Contatto> contatti) throws IOException {
		objectOutput = new ObjectOutputStream(new FileOutputStream(filepath));
		objectOutput.writeObject(contatti);
		objectOutput.close();
	}

	//Metodo che mi permette di leggere gli oggetti nel file
	public static void readFile() throws IOException, ClassNotFoundException {

		try {
			objectInput = new ObjectInputStream(new FileInputStream(filepath));
		} catch (Exception e) {
			System.out.println("Nessun contatto ancora memorizzato");
		}

		if(objectInput != null) {
			@SuppressWarnings("unchecked")
			HashSet<Contatto> inputList = (HashSet<Contatto>) objectInput.readObject();
			System.out.println("\nELENCO");
			
			for (Contatto cont : inputList) {
				Contatto contatto = (Contatto) cont;
				System.out.println("Nome: " + contatto.getNome() + ". Numero: " + contatto.getNumero());
			}
			objectInput.close();
		}

	}

	//Metodo per eliminare un contatto
	public HashSet<Contatto> eliminaContatto(HashSet<Contatto> contatti, Predicate<Contatto> c) throws IOException{
		
		for (Contatto contatto : contatti) {
			if(c.test(contatto)) {
				contatti.remove(contatto);
				writeToFile(contatti);
				break; //QUESTO DANNATO BREAK MI HA SALVATO LA VITA, 3 ore per capire un'eccezione...
			}
		}
		return contatti;
	}
	
	//Metodo per modificare un contatto
		public HashSet<Contatto> modificaContatto(HashSet<Contatto> contatti, Predicate<Contatto> c, String nome, String numero) throws IOException{
			
			for (Contatto contatto : contatti) {
				if(c.test(contatto)) {
					contatto.setNome(nome);
					contatto.setNumero(numero);
					contatti.add(contatto);
					writeToFile(contatti);
					break; 
				}
			}
			return contatti;
		}
	
	//QUESTO METODO è IDENTICO AL METODO READFILE, SOLAMENTE DIFFERISCE DI UNA ISTRUZIONE IN PIù
	public static void aggiornaListaIniziale() throws IOException, ClassNotFoundException {

		try {
			objectInput = new ObjectInputStream(new FileInputStream(filepath));
		} catch (Exception e) {
			System.out.println("Nessun contatto presente!");
		}

		if(objectInput != null) {
			@SuppressWarnings("unchecked")
			HashSet<Contatto> inputList = (HashSet<Contatto>) objectInput.readObject();
			for (Contatto cont : inputList) {
				Contatto contatto = (Contatto) cont;
				
				//TRAMITE QUESTA ISTRUZIONE VADO A POPOLARE LA LISTA INIZALE CON GLI OGGETTI PRESENTI NEL FILE, SEMPRE SE SONO PRESENTI
				contatti.add(contatto);
			}
			objectInput.close();
		}

	}
	
	//Metodo che ricerca per nome il contatto
	public HashSet<Contatto> ricercaNome(HashSet<Contatto> contatti, Predicate<Contatto> c) throws IOException{
		
		for (Contatto contatto : contatti) {
			if(c.test(contatto)) {
				System.out.println("Nome: " + contatto.getNome() + "Numero: " + contatto.getNumero());
				break; 
			}
		}
		return contatti;
	}
	
	
	//Metodo che ricerca per numero il contatto
		public HashSet<Contatto> ricercaNumero(HashSet<Contatto> contatti, Predicate<Contatto> c) throws IOException{
			
			for (Contatto contatto : contatti) {
				if(c.test(contatto)) {
					System.out.println("Nome: " + contatto.getNome() + "Numero: " + contatto.getNumero());
					break; 
				}
			}
			return contatti;
		}
	

}
