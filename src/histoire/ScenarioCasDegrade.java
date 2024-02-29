package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Gaulois gaulouis = new Gaulois("Gaulouis", 10);
		Etal etal = new Etal();
		
		//		
		
		etal.libererEtal();
		
		try {
			System.out.println("TEST etal non-occupée");
			etal.acheterProduit(1, gaulouis);
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
		
		etal.occuperEtal(gaulouis, "chaussettes", 1);
		
		System.err.println("".equals(etal.acheterProduit(1, null)));

		try {
			etal.acheterProduit(0, gaulouis);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		//		
		
		System.out.println("Fin du test");
	}

}
