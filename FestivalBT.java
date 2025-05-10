package ej3_pi4_manualBT;

import ej3_pi4.SolucionFestival;

public class FestivalBT {
	
	private static Double mejorValor;
	private static FestivalState estado;
	private static SolucionFestival solucion;
	
	public static void search () {
		solucion = null;
		mejorValor = Double.MAX_VALUE; // Minimizamos los costes
		estado = FestivalState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if (estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if (valorObtenido < mejorValor) {  // Estamos minimizando
				mejorValor = valorObtenido;
				solucion = estado.getSolucion();
			}
		} else if(!estado.esTerminal()){
			for (Integer a: estado.alternativas()) {
//				if (estado.cota(a) <= mejorValor) {  // Estamos minimizando
				if (estado.cota(a) < mejorValor) {  // Estamos minimizando
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	public static SolucionFestival getSolucion() {
		return solucion;
	}
	
	

}
