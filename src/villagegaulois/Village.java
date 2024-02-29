package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {

	private static class Marche {

		private final Etal[] etals;

		//

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];

			for (int i = 0; i < this.etals.length; i++) {
				this.etals[i] = new Etal();
			}
		}

		//

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal < 0 || this.etals.length <= indiceEtal) {
				throw new IllegalArgumentException(
						"L'indice de l'étal doit étre compris entre 0 (inclus) et " + this.etals.length + " (exclus).");
			}

			Etal etal = this.etals[indiceEtal];
			if (etal.isEtalOccupe()) {
				etal.libererEtal();
			}

			etal.occuperEtal(vendeur, produit, nbProduit);
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalsContenantProduit = 0;

			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];

				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtalsContenantProduit++;
				}
			}

			int indiceEtalContenantProduit = 0;
			Etal[] etalsContenantProduit = new Etal[nbEtalsContenantProduit];

			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];

				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					etalsContenantProduit[indiceEtalContenantProduit] = etal;
					indiceEtalContenantProduit++;
				}
			}

			return etalsContenantProduit;
		}

		private int trouverEtalLibre() {
			int indiceEtal;
			boolean etalTrouvee = false;

			for (indiceEtal = 0; indiceEtal < this.etals.length && !etalTrouvee; indiceEtal++) {
				etalTrouvee = !this.etals[indiceEtal].isEtalOccupe();
			}

			return etalTrouvee ? indiceEtal - 1 : -1;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etal = null;
			boolean etalTrouvee = false;

			for (int i = 0; i < this.etals.length && !etalTrouvee; i++) {
				etal = this.etals[i];
				etalTrouvee = etal.isEtalOccupe() && etal.getVendeur().getNom().equals(gaulois.getNom());
			}

			return etalTrouvee ? etal : null;
		}

		private String afficherMarche() {
			StringBuilder messageBuilder = new StringBuilder();

			int nbEtalVide = 0;

			for (int i = 0; i < this.etals.length; i++) {
				Etal etal = this.etals[i];

				if (!etal.isEtalOccupe()) {
					nbEtalVide++;
				} else {
					messageBuilder.append(etal.afficherEtal());
				}
			}

			messageBuilder.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");

			return messageBuilder.toString();
		}

	}

	//

	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	private Marche marche;

	//

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];

		this.marche = new Marche(nbEtals);
	}

	//

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	//

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder messageBuilder = new StringBuilder();

		messageBuilder
				.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");

		int indiceEtal = this.marche.trouverEtalLibre();
		this.marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);

		messageBuilder.append("Le vendeur " + vendeur.getNom() + " vend des fleurs é l'étal n°" + (indiceEtal + 1) + ".\n");

		return messageBuilder.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder messageBuilder = new StringBuilder();

		Etal[] etals = this.marche.trouverEtals(produit);

		switch (etals.length) {
		case 0:
			messageBuilder.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
			break;
		case 1:
			messageBuilder.append(
					"Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
			break;
		default:
			messageBuilder.append("Les vendeurs qui proposent des " + produit + " sont :\n");

			for (Etal etal : etals) {
				messageBuilder.append(" - " + etal.getVendeur().getNom() + "\n");
			}

			break;
		}

		return messageBuilder.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = rechercherEtal(vendeur);

		if (etal == null) {
			return null;
		} else {
			return etal.libererEtal();
		}
	}

	public String afficherMarche() {
		return "Le marché du village \"" + this.getNom() + "\" posséde plusieurs étals :\n"
				+ this.marche.afficherMarche();
	}

}