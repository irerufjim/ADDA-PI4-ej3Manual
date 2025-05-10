package ej3_pi4_manualBT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import ej3_pi4.DatosFestival;
import us.lsi.common.List2;

// Tenemos minimo de tickets 
// Y máximo por area
public record FestivalProblem(Integer index, List<Integer> ticketsPerType, List<Integer> remSpaceAreas, List<Integer> tickets) {
	
	public static FestivalProblem initial() {
		List<Integer> tpt = new ArrayList<>();
		List<Integer> rsa = new ArrayList<>();
		List<Integer> t = new ArrayList<>();
		
		for (int ticketType=0; ticketType<DatosFestival.getNumTiposEntrada();ticketType++) {
			tpt.add(DatosFestival.getCuotaMinima(ticketType));
			for (int area=0; area<DatosFestival.getNumAreas();area++) {

				if (ticketType==0) {
					rsa.add(DatosFestival.getAforoMaximoArea(area));
					
				}
				t.add(0);
			}
		}
		System.out.println(tpt+" " +rsa+ " " + t);
		return of(0,tpt,rsa,t);
		
		
	}
	
	public static FestivalProblem of(Integer i, List<Integer> tpt, List<Integer> rsa, List<Integer> t) {
		return new FestivalProblem(i,tpt,rsa,t);	
	}
	
	public List<Integer> actions(){
		Integer curTicket = index / DatosFestival.getNumAreas();
		if (index >= DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada()) {
	        return List2.empty();
	    } else if (index==DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada()-1) {
	    	return List.of(ticketsPerType.get(curTicket));
	    } else {
	    	// System.out.print(IntStream.range(0,ticketsPerType.get(curTicket)).boxed().toList());
	    	
	    	return IntStream.range(0,ticketsPerType.get(curTicket)+1)
	    			.filter(x->x<=remSpaceAreas.get(index % DatosFestival.getNumAreas()))
	    			.boxed().toList();
	    }
	}

	
	public FestivalProblem neighbor(Integer a) {
		List<Integer> tpt = new ArrayList<>(ticketsPerType);
		List<Integer> rsa = new ArrayList<>(remSpaceAreas);
		List<Integer> t = new ArrayList<>(tickets);
		Integer area = index % DatosFestival.getNumAreas();
		Integer curTicket = index / DatosFestival.getNumAreas();
		
		Integer tptValue = tpt.get(curTicket)-a;
		tpt.set(curTicket, tptValue);
		
		Integer rsaValue = rsa.get(area)-a;
		rsa.set(area, rsaValue);
		
		t.set(index, a);
		
		return of (index+1, tpt,rsa,t);
	}
	
	public Double heuristic() { // The optimal option is where the cost doesn't increment (MINIMIZE COST)
		double h = 0.0;
	    for (int i = index; i < DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada(); i++) {
	        int area = i % DatosFestival.getNumAreas();
	        int ticket = i / DatosFestival.getNumAreas();
	        int remaining = ticketsPerType.get(ticket);
	        if (remaining > 0) {
	            h += remaining * DatosFestival.getCosteAsignacion(ticket, area); // Estimated cost
	        }
	    }
	    return h;
	}
			
}
