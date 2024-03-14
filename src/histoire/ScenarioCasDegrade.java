package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Gaulois gaulouis = new Gaulois("Gaulouis", 10);
		Etal etal = new Etal();
		
		//		
		
		etal.libererEtal();
		
		try {
			System.err.println("TEST 'etal.acheterProduit' : etal non-occupée");
			etal.acheterProduit(1, gaulouis);
		} catch(IllegalStateException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		
		//		
		
		etal.occuperEtal(gaulouis, "chaussettes", 1);

		System.err.println("TEST 'etal.acheterProduit' : acheteur null");
		System.err.println("".equals(etal.acheterProduit(1, null)));

		try {
			System.err.println("TEST 'etal.acheterProduit' : quantité d'achat insuffisante");
			etal.acheterProduit(0, gaulouis);
		} catch(IllegalArgumentException e) {
			System.err.println(true);
		} catch(Exception e) {
			System.err.println(false);
		}
		
		//
		
		Village village = new Village("village-test", 1, 1);
		
		village.ajouterHabitant(gaulouis);
		
		System.err.println("TEST 'village.afficherVillageois' : village sans chef");
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.err.println(true);
		}
		
		//
		
		System.out.println("Fin du test");
	}

}
