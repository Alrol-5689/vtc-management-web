package com.vtc.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class Utilities {
	
	protected int pedirOpcion(Scanner sc, int min, int max, String mensaje) {
		System.out.print(mensaje);
		while (true) {
			try {
				int opcion = Integer.parseInt(sc.nextLine());
				if (opcion >= min && opcion <= max) {
					return opcion;
				}
				System.err.println("Por favor, introduce un número entre " + min + " y " + max + ".");
			} catch (NumberFormatException e) {
				System.err.println("Error: Ingresa solo números. ");
			}
			System.out.print(mensaje);
		}
	}
	
	protected int pedirInt(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (true) {
			try {
				int out = Integer.parseInt(sc.nextLine());
				return out;
			} catch (NumberFormatException e) {
				System.err.println("Error. Introduce solo números. ");
				System.out.print(mensaje);
			}
		}
	}
	
	public double pedirDouble(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (true) {
			try {
				double out = Double.parseDouble(sc.nextLine().replace(",", "."));
				return out;
			} catch (NumberFormatException e) {
				System.err.println("Error. Introduce solo números. ");
				System.out.print(mensaje);
			}
		}
	}
	
	protected boolean confirmacionSiNo(Scanner sc, String mensaje) {
		while (true) {
			System.out.print(mensaje);
			String entrada = sc.nextLine();
			if (entrada.equalsIgnoreCase("s")) {
				return true;
			}
			if (entrada.equalsIgnoreCase("N")) {
				return false;
			}
			System.err.println("\nError: Introduce 'S' o 'N'. ");
		}
	}
	
	protected LocalDate pedirFecha(Scanner sc, String mensaje) {
		System.out.println(mensaje);
		while (true) {
			try {
				int anio = pedirInt(sc, "Año: ");
				int mes = pedirInt(sc, "Mes: ");
				int dia = pedirInt(sc, "Día: ");
				return LocalDate.of(anio, mes, dia);
			} catch (java.time.DateTimeException e) {
				System.err.println("Fecha no válida: " + e.getMessage());
				System.out.println("Por favor, introdúcela de nuevo.\n");
			}
		}
	}

	//===>> Métodos auxiliares para gestionar los pluses de calidad <<===//
    
    public static boolean esMesTrimestral(int mes) {
        return mes == 3 || mes == 6 || mes == 9 || mes == 12;
    }
    
    public static boolean esMesTrimestral(YearMonth mes) {
        return esMesTrimestral(mes.getMonthValue());
    }
    
    public static boolean esMesTrimestral_masUno(int mes) {
        return mes == 4 || mes == 7 || mes == 10 || mes == 1;
    }
    
    public static boolean esMesTrimestral_masUno(YearMonth mes) {
        return esMesTrimestral_masUno(mes.getMonthValue());
    }


}
