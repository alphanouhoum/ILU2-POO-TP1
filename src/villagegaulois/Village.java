package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
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
				if(etal != null && etal.contientProduit(produit)) {
					nbEtalsProduit++;
				}
			}
			Etal[] etalsProduit = new Etal[nbEtalsProduit];
			
			int index = 0;
			for (int i = 0; i < etals.length; i++) {
				if( etals[i] != null && etals[i].contientProduit(produit)) {
					etalsProduit[index] = etals[i];
					index++;
				}
			}
			
			return etalsProduit;
					
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if(etal != null && etal.getVendeur().equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}
		
		public Boolean partirVendeurEtal(Gaulois vendeur) {
			Etal vendeurEtal = marche.trouverVendeur(vendeur);
			for (int i = 0; i< etals.length; i++){
				if (etals[i] != null && etals[i].getVendeur().getNom() == vendeur.getNom()) {
					etals[i] = null;
					return true;
				}
			}
			return false;
			
		}
		
		public String afficherMarche() {
			StringBuilder string = new StringBuilder();
			int nbEtalVide=0;
			for (Etal etal : etals) {
				if(etal != null) {
					string.append(etal.afficherEtal());
				}else{
					nbEtalVide++;
				}
			}
			if(nbEtalVide > 0) {
				string.append("Il reste "+nbEtalVide+" étals non utilisés dans le marché.");
			}
			
			return string.toString();
		}
				
		
		public String libererEtal(Gaulois vendeur) {
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i] != null && etals[i].getVendeur().equals(vendeur)) {
		            String message = etals[i].libererEtal();
		            etals[i] = null; // L'étal redevient libre
		            return message;
		        }
		    }
		    return vendeur.getNom() + " n'est pas vendeur au marché.\n";
		}
	
	
	}
	
	
	
	//Nouvelle methode classe village tp1
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		StringBuilder string = new StringBuilder();
		
		if (indiceEtal != -1 ) {
			string.append(vendeur.getNom() + " cherche un endroit pour vendre "+ nbProduit + " "+produit+".\n"
					+ "Le vendeur "+vendeur.getNom()+ " vend des "+ produit +" à l'étal n° "+ (indiceEtal+1) + "\n");
		}
		else {
			string.append(vendeur.getNom()+ " n'a pas trouver d'endtoit pour vendre\n");
		}
		
		return string.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal [] produitVendeur = marche.trouverEtals(produit);
		
		
		StringBuilder string =  new StringBuilder();
		
		if (produitVendeur.length == 0) {
			string.append("Il n'y a pas de vendeur qui propose des " + produit +" au marché.\n");
			
		}else if (produitVendeur.length == 1){ 
			string.append("Seul le vendeur "+produitVendeur[0].getVendeur().getNom()+" propose des "+ produit +" au marché.\n");
		}else{
			string.append("Les vendeurs qui proposent des "+ produit + " sont :\n");
			for (Etal etal : produitVendeur) {
				string.append("- "+ etal.getVendeur().getNom()+ ""
						+ "\n");
			}
		}
		
		return string.toString();
	}
	
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		
		return marche.libererEtal(vendeur);
	}
	
	public String afficherMarche() {
		
		StringBuilder string = new StringBuilder();
		string.append("Le marché du village "+ this.nom+ "possède plusieurs étals : \n" );
		string.append(marche.afficherMarche());
		return string.toString();
	}
	
}







