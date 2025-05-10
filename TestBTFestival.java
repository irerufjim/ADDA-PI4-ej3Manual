public class TestBTFestival {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (Integer id_fichero = 1; id_fichero <= 3; id_fichero++) {
		DatosFestival.iniDatos(<Nombre del archivo txt entre comillas>); // .txt
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		// DatosFestival.toConsole();
		FestivalBT.search();
		System.out.println(FestivalBT.getSolucion()+ "\n");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		}

	}

}
