package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		etal.occuperEtal(asterix, "fleurs", 20);
		
		
		try {
	        System.out.println(etal.acheterProduit(-5, obelix)); // etal non occuper
	    
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
	    }catch (IllegalStateException e) {
	        e.printStackTrace();
	    }
		
		System.out.println("Fin test");
	
	}
}
