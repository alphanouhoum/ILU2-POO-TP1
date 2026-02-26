package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		Marche marche = new Marche(nbEtals);
	}

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
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	//TP1 modification 
	// Class intern 
	private class Marche{
		private Etal[] etals;
		
		public Marche (int nbEtals){
			etals = new Etal[nbEtals];
		}
		
		public void utiliserEtal (int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = new Etal();
			etal.occuperEtal(vendeur, produit, nbProduit);
			etals[indiceEtal] = etal;
		}
		
		public int trouverEtalLibre () {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i] == null) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsProduit = 0;
			for (Etal etal : etals) {
				if(etal.contientProduit(produit)) {
					nbEtalsProduit++;
				}
			}
			
			Etal[] etalsProduit = new Etal[nbEtalsProduit];
			
			int index = 0;
			for (int i = 0; i < etalsProduit.length; i++) {
				if(etals[i].contientProduit(produit)) {
					etalsProduit[index] = etals[i];
					index++;
				}
			}
			
			return etalsProduit;
					
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if(etal.getVendeur().equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}
		
		public void afficherMarche() {
			int nbEtalVide=0;
			for (Etal etal : etals) {
				if(etal.isEtalOccupe()) {
					System.out.println(etal.afficherEtal());
				}
				if(!etal.isEtalOccupe()) {
					nbEtalVide++;
				}
			}
			System.out.println("Il reste "+nbEtalVide+" étals non utilisés dans le marché.");
		}
				
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder string = new StringBuilder(vendeur.getNom() + "cherche un endroit pour vendre "+ nbProduit + produit+".\n"
				+ "Le vendeur "+vendeur.getNom()+ "vend des "+ produit +" à l'étal n° "+marche.trouverVendeur(vendeur));
		
		return string.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal [] produitVendeur = marche.trouverEtals(produit);
		
		StringBuilder string;
		if (produitVendeur.length == 0) {
			string = new StringBuilder("Il n'y a pas de vendeur qui propose des" + produit +" au marché.");
		}else{ //(produitVendeur.length == 1)
			string = new StringBuilder("Seul le vendeur "+produitVendeur[0].getVendeur()+ " propose des "+ produit +" au marché.");
		}
		
		return string.toString();
	}

}