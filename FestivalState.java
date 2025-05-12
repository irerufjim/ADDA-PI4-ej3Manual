package ej3_pi4_manualBT;

import java.util.List;

import ej3_pi4.DatosFestival;
import ej3_pi4.SolucionFestival;
import us.lsi.common.List2;

public class FestivalState {
	
	FestivalProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<FestivalProblem> anteriores;
	
	private FestivalState (FestivalProblem p, Double a, List<Integer> ls1, List<FestivalProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones=ls1;
		anteriores=ls2;
	}
	
	public static FestivalState initial() {
		FestivalProblem pi = FestivalProblem.initial();
		return of(pi,0.,List2.empty(),List2.empty());
	}
	
	public static FestivalState of (FestivalProblem p, Double a, List<Integer> ls1, List<FestivalProblem> ls2) {
		return new FestivalState(p,a,ls1,ls2);
	}
	
	public void forward(Integer a) {
		Integer area = actual.index() % DatosFestival.getNumAreas();
		Integer curTicket = actual.index() / DatosFestival.getNumAreas();
		acumulado += a * DatosFestival.getCosteAsignacion(curTicket, area); // Minimize implementation costs
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}
	
	public void back() {
		int last = acciones.size()-1; // last is the index of the last element 
		var prob_ant = anteriores.get(last); // last problem
		
		Integer lastArea = last % DatosFestival.getNumAreas();
		Integer lastTicket = last / DatosFestival.getNumAreas();
		acumulado -= acciones.get(last)* DatosFestival.getCosteAsignacion(lastTicket, lastArea);
		
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;
	}
	
	public List<Integer> alternativas(){
		return actual.actions();
	}
	
	public Double cota(Integer a) {
		Integer area = actual.index() % DatosFestival.getNumAreas();
		Integer ticket = actual.index() / DatosFestival.getNumAreas();
		Double weight  = a>0? DatosFestival.getCosteAsignacion(ticket, area):0.;
		// return acumulado + weight + actual.neighbor(a).heuristic();
		return Double.MIN_VALUE;
	}
	
	public Boolean esSolucion() {
		for (int min: actual.ticketsPerType()) {
			if (min!=0) {
				return false;
			}
		}
		return true;
		
	}
	
	public boolean esTerminal() {
		return actual.index()==DatosFestival.getNumAreas()*DatosFestival.getNumTiposEntrada();
	}
	
	public SolucionFestival getSolucion() {
		return SolucionFestival.of(acciones);
	}

}
