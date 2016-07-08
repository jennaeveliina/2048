/*
 * Lausekielinen ohjelmointi, toinen Harjoitustyˆ, syksy 2014
 *
 * Tekstipohjainen muunnelma 2048-pelist‰.
 *
 * Jenna Halmetoja
 */
public class M2048{
   /*
    * Vakioidut attribuutit
    */
   //Alustetaan vakioituja muuttujia.
   private static final String OHJAUS = "l/left, r/right, u/up, d/down, z/undo, q/quit?";
   private static final char LEFT = 'l';
   private static final char RIGHT = 'r';
   private static final char UP = 'u';
   private static final char DOWN = 'd';
   private static final char UNDO = 'z';
   private static final char QUIT = 'q';
   
   private static final String VIRHE = "Invalid command-line argument!";
   private static final String LOPETUS = "Bye, see you soon.";
   
   //m‰‰ritell‰‰n reuna- ja taustamerkit
   private static final char TAUSTA = Automaatti.TAUSTA;
   private static final char REUNA = '.';
   
   //vakioidaan t‰ss‰ metodissa k‰ytett‰v‰ muuttuja, joka sis‰lt‰‰ aakkoset.
   private static final String MERKIT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   
   //pistelaskuri, ainoana sallittuna attribuuttina
   private static int pisteet = 0;
   
   
   /* 
    * Tulostetaan kaksiulotteisen taulukon t alkiot n‰ytˆlle.
    */
   public static void tulostaTaulukko(char[][] kentta){
      // Tulostetaan vain, jos taulukolle on varattu muistia.
      if (kentta != null) {
         // Tulostetaan rivit.
         for (int rivi = 0; rivi < kentta.length; rivi++) {
            // Tulostetaan rivi.
            for (int sarake = 0; sarake < kentta[rivi].length; sarake++){
               System.out.print(kentta[rivi][sarake]);
            }
            // Rivin lopussa vaihdetaan rivi‰.
            System.out.println();
         }
      }
   }
   /* 
    * Alustetaan kaksiulotteinen kentta, parametreina kentta
    */
   public static void alustaKentta(char[][] kentta){
      // tarkistetaan onko taulukolle varattu muistia
      if (kentta != null) {
         // Asetetaan jompikumpi merkki kuhunkin taulukon alkioon.
         for (int rivi = 0; rivi < kentta.length; rivi++){
            for (int sarake = 0; sarake < kentta[rivi].length; sarake++){
               // jos reunapaikka, asetetaan merkiksi reunamerkki
               if (rivi == 0 || rivi == kentta.length - 1 ||
                  sarake == 0 || sarake == kentta[rivi].length - 1){
                  kentta[rivi][sarake] = REUNA;
               }
               // jos sis‰paikka, niin t‰ytet‰‰n v‰lilyˆnneill‰.
               else{
                  kentta[rivi][sarake] = TAUSTA;
               }
            }
         }
      }
   }
   /* 
    * Luo annetun kokoisen taulukon, 
    * alustaa sen ja palauttaa char-alkioiden taulukon
    * parametreina rivien ja sarakkeiden lukum‰‰r‰.
    * Paluuarvo on null, jos parametreiss‰ havaitaan virhe.
    */
   public static char[][] luoKentta(int rivlkm, int sarlkm){
      // Parametrit kunnossa(napataan pienin ja suurin sivun pituuden
      // m‰‰rittelyt automaattiluokasta)
      if (rivlkm >= Automaatti.MINSIVUNPIT && sarlkm == rivlkm) {
         // Luodaan taulukko-olio ja liitet‰‰n siihen viite.
         char[][] taulu = new char[rivlkm][sarlkm];

         // Alustetaan taulukko pelikent‰ksi.
         alustaKentta(taulu);

         // Palautetaan viite taulukko-olioon.
         return taulu;
      }
      // Parametreissa virhe.
      else{
         return null;
   }
      }
   /*
    * Kirjoittaa uuden, muutetun kentan,
    * parametreinaan taulukko, taulukon indeksi arvo ja 
    * rivin tai sarakkeen valinta, boolean tyyppisell‰ muuttujalla,
    * ja uusi, muutettu merkkijono.
    */
   public static char[][] kirjoitaUusiKentta(char[][] kentta, int indeksi, boolean rivi, String uusiMerkkijono, boolean suoraSuunta){
      //parametrit oikeellisia.
      if(kentta != null && indeksi < kentta.length){
         //indeksi-laskuri uudelle merkkijonolle.
         int k = 0;   
         //jos liike on alas tai oikealle, k‰‰nnet‰‰n merkkijono ymp‰ri
         if (!suoraSuunta){
            //tehd‰‰n uusi nurinkurinen merkkijono
            uusiMerkkijono = new StringBuilder(uusiMerkkijono).reverse().toString();
         }
         //k‰yd‰‰n l‰pi kaikki rivit
         for(int i = 0; i < kentta.length; i++){
            //k‰yd‰‰n l‰pi rivin sarakkeet.
            for(int j = 0; j < kentta[i].length; j++){
               //jos ollaan valitussa indeksiss‰
               //ja valitaan rivi.
               if(i == indeksi && rivi){
                  //asetetaan kentt‰n t‰m‰n hetkiseen paikkaan 
                  //uudenMerkkijonon t‰m‰n hetkinen merkki
                  kentta[i][j] = uusiMerkkijono.charAt(k);
                  //lis‰t‰‰n laskuria
                  k++;
               }
               //jos ollaan valitussa indeksiss‰,
               //ja valitaan sarake
               else if(j == indeksi && !rivi){
                  //asetetaan kentt‰n t‰m‰n hetkiseen paikkaan 
                  //uudenMerkkijonon t‰m‰n hetkinen merkki
                  kentta[i][j] = uusiMerkkijono.charAt(k);
                  //lis‰t‰‰n laskuria
                  k++;
               }
            }
         }
         //palautetaan muokattu kentta
         return kentta;
      }
      //parametreissa virhe.
      else{
         return null;
      }
   }
   /*
    * tekee taulukon rivit ja sarakkeet merkkijonoiksi,
    * parametreinaan kentta, taulukon indeksi arvo ja 
    * rivin tai sarakkeen valinta, ja boolean tyyppisell‰ muuttujalla.
    */
   public static String muutaMerkkijonoksi(char[][] kentta, int indeksi, boolean rivi, boolean suoraSuunta){
      //muuttuja paluuarvolle.
      String merkkijono = "";
      //parametrit oikeellisia.
      if(kentta != null && indeksi >= 0){
         //k‰yd‰‰n l‰pi kaikki rivit
         for(int i = 0; i < kentta.length; i++){
            //k‰yd‰‰n l‰pi rivin sarakkeet.
            for(int j = 0; j < kentta[i].length; j++){
               //jos ollaan valitussa indeksiss‰
               //ja valitaan rivi.
               if(i == indeksi && rivi){
                  //lis‰t‰‰n merkkijonoon kent‰n merkit
                  merkkijono += kentta[i][j];
               }
               //jos ollaan valitussa indeksiss‰,
               //ja valitaan sarake
               else if(j == indeksi && !rivi){
                  //lis‰t‰‰n merkkijonoon kent‰n merkit
                  merkkijono += kentta[i][j];
               }
            }
         }
      }
      //jos liike on alas tai oikealle, k‰‰nnet‰‰n merkkijono ymp‰ri
      if (!suoraSuunta){
         //k‰‰nnet‰‰n merkkijono nurinkurin
         merkkijono = new StringBuilder(merkkijono).reverse().toString();
      }
      //palautetaan uusi merkkijono
      return merkkijono;
   }
   /*
    * Tiivistet‰‰n merkkijono(eli kent‰n rivi tai sarake),
    * parametrina kent‰n rivi tai sarake(riippuen liikkeen suunnasta
    * merkkijonona.
    */
   public static String tiivistaMerkkijono(String merkkijono){
      //muuttuja tiivistetylle merkkijonolle.
      String tiivistetty = "";
      //aputaulukko
      char[] tiivis = new char[merkkijono.length()];
      //indeksi laskuri aputaulukon paikoille
      int j = 0;
      
      //k‰yd‰‰n merkkijonon paikat l‰pi.
      for(int i = 0; i < merkkijono.length(); i++){
         //jos reunamerkki
         if(merkkijono.charAt(i) == REUNA){
            //lis‰t‰‰n reunapalat omalle paikalleen. 
            tiivis[i] = merkkijono.charAt(i);
            j++;
         }
         //jos taustamerkki
         else if(merkkijono.charAt(i) == TAUSTA){
            //lis‰t‰‰n taustamerkit omalle paikalleen.
            //tiivis[i] = merkkijono.charAt(i);
         }
         //muuten napataan merkkijonon t‰m‰nhetkinen merkki
         //aputaulukko[j]:hin
         else{
            tiivis[j] = merkkijono.charAt(i);
            j++;
         }
      }
      //tehd‰‰n taulukosta merkkijono
      String merkkijono2 = new String(tiivis);
      //yhdistet‰‰n kirjaimet
      tiivistetty = yhdistaMerkit(merkkijono2);
      //palautetaan uusi merkkijono.
      return tiivistetty;
   }
   /*
    * liikutetaan pelikent‰n nappuloita,
    * parametreina kentt‰ ja liike
    */
   public static char [][] liiku(char[][] kentta, char liike){
      //apumuuttujia
      String merkkijono = "";
      String tiivistetty ="";
      
      //jos totta, valinta on rivi 
      //jos ep‰totta, valinta on sarake
      boolean rivi = true;
      //jos totta, liike on vasemmalle tai ylˆs.
      //jos ep‰totta, liike on oikealle tai alas.
      boolean suoraSuunta = true;
      
      //muuttuja paluuarvolle
      char [][] muutettuKentta = kentta;
      
      for(int i = 0; i < kentta.length; i++){
         //jos liikutaan vaakasuunnassa
         if(liike ==LEFT || liike == RIGHT){ 
            //jos liikutaan oikealle,k‰‰nnet‰‰n lippumuuttujan arvo
            if (liike == RIGHT){
               suoraSuunta = false;
            }
            //tehd‰‰n merkkijono
            merkkijono = muutaMerkkijonoksi(kentta, i, rivi, suoraSuunta);
            //tiivistet‰‰n merkkijono
            tiivistetty = tiivistaMerkkijono(merkkijono);
            //uudistetaan kentta
            muutettuKentta = kirjoitaUusiKentta(kentta, i, rivi, tiivistetty, suoraSuunta);
         }
         //jos liikutaan pystysuunnassa
         else if(liike == DOWN || liike == UP){
            //jos liikutaan oikealle,k‰‰nnet‰‰n lippumuuttujan arvo
            if (liike == DOWN){
               suoraSuunta = false; 
            }
            //tehd‰‰n merkkijono
            merkkijono = muutaMerkkijonoksi(kentta, i, !rivi, suoraSuunta);
            //tiivistet‰‰n merkkijono
            tiivistetty = tiivistaMerkkijono(merkkijono);
                 
            //uudistetaan kentta
            muutettuKentta = kirjoitaUusiKentta(kentta, i, !rivi, tiivistetty, suoraSuunta);
         }
      }
      //palautetaan muutettu kentt‰.
      return muutettuKentta;
   }
   /*
    * Kopioidaan vanha kentt‰, parametrina kentta
    */
   public static char[][] kopioiVanhaKentta(char[][] kentta){
      char[][] uusiKentta = new char[kentta.length][kentta.length];
      //k‰yd‰‰n l‰pi kaikki rivit
      for(int i = 0; i < kentta.length; i++){
         //k‰yd‰‰n l‰pi rivin sarakkeet.
         for(int j = 0; j < kentta[i].length; j++){
            char uusiMerkki = TAUSTA;
            uusiMerkki = kentta[i][j];
            uusiKentta[i][j] = uusiMerkki;
         }
      }
      return uusiKentta;
   }
   /*
    * tekee uuden pelikent‰n,
    * parametreina kentt‰ ja liike,
    * parametreina kentta ja k‰ytt‰j‰n syˆte.
    */
   public static char[][] muutaKentta(char[][] kentta, char liike){
      char [][] muutettuKentta = kentta;
      //tarkistetaan onko varattu muistia.
      if(kentta != null) {
         muutettuKentta = liiku(kentta, liike);
      }
      return muutettuKentta;
   }
   /*
    * tutkitaan onko merkkijonon vierekk‰iset merkit samoja
    * parametreina merkkijono ja yksiulotteinen aputaulukko
    */
   public static void tutkiOnkoSamat(String merkkijono, char[] muutettu1){
      boolean samat = false;
      //laskuri aputaulukon paikoille.
      int j = 0;
      //tutkitaan onko nykyinen ja seuraava merkki
      for(int i = 0; i < merkkijono.length(); i++){
         //lis‰t‰‰n viimeinen merkki (eli reunapalanen) joka tapauksessa
         if(i+1 == merkkijono.length()){
            muutettu1[i] = merkkijono.charAt(i);
            j++;
         }
         //yhdistet‰‰n merkit, jos samat ja ei olla viimeisess‰ paikassa
         //ja merkki‰ ei ole vaihdettu.
         else if(i < merkkijono.length()-1){
            //asetetaan apumuuttujien arvoiksi
            //nykyinen ja seuravaa merkki
            char merkki1 = merkkijono.charAt(i);
            char merkki2 = merkkijono.charAt(i+1);
            char[] charArray = merkkijono.toCharArray();
            //muutetaan pelikent‰n uudet merkit siirretyiksi merkeiksi
            if(merkki1 == Automaatti.UUSIMINPELIMERKKI){
               charArray[i] = 'A';
               merkkijono = new String(charArray);
            }
            //tehd‰‰n sama myˆs jos merkki2 on aloitusmerkki
            if(merkki2 == Automaatti.UUSIMINPELIMERKKI){
               charArray[i+1] = 'A';
               merkkijono = new String(charArray);
            }
            //napataan reunat sein‰m‰t mukaanlukien viimeinen reunapalikka mukaan.
            if (merkki1 == REUNA && merkki2 == REUNA){
               muutettu1[i] = merkkijono.charAt(i);
               muutettu1[i+1] = merkkijono.charAt(i+1);
               i++;
            }
            //yhdistell‰‰n jos vierekk‰iset merkit ovat samat ja ne ei ole tyhji‰
            else if(merkkijono.charAt(i) == merkkijono.charAt(i+1) &&
               merkkijono.charAt(i) != 0 && merkkijono.charAt(i) != TAUSTA){
               //kutsutaan metodia joka yhdist‰‰ merkit seuraavaksi aakkoseksi. 
               //liike oikealle niin tapahtuu "A A " --> " B"
               muutettu1[j] = yhdistaOikeasti(charArray[i]);
               j++;
               //hyp‰t‰‰n i:n seuraavaan paikkaan, 
               //koska kirjaimet yhdistettiin.
               i = i+1;
            }
            //jos merkit ei ole samoja, lis‰t‰‰n merkkijonoon vanha merkki.
            else{
               //jos "tyhj‰ char" lis‰t‰‰n taustamerkki
               if (merkkijono.charAt(i) == 0){
                  muutettu1[j] = TAUSTA;
               }
               //jos char ei ole tyhj‰, lis‰t‰‰n vanha merkki
               else {
                  muutettu1[j] = merkkijono.charAt(i);
               }
               //menn‰‰n taulukon seuraavaan paikkaan
               j++;
            }
         }
      }  
   }
   /* 
    * Metodi, joka yhdist‰‰ rivin tai sarakkeen samanlaiset 
    * kirjaimet(esim. A ja A) seuraavaksi aakkoseksi.
    * parametreina merkkijono
    */
   public static String yhdistaMerkit(String merkkijono){
      //muuttuja paluuarvolle
      String muutettu = "";
      //aputaulukko
      char[] muutettu1 = new char[merkkijono.length()];
      
      //aputaulukko 
      char[] apuMerkit = new char[merkkijono.length()];
      //t‰ytet‰‰n aputaulukko.
      for (int i = 0; i < merkkijono.length(); i++){
         //jos tyhj‰ merkki, lis‰t‰‰n sen tilalle taustamerkki
         if (merkkijono.charAt(i) == 0){
            //laitetaan taustamerkki aputaulukkoon
            apuMerkit[i] = TAUSTA;
         }
         //muuten lis‰t‰‰n nykyinen merkki
         else{
            apuMerkit[i] = merkkijono.charAt(i);
         }
      }
      //tehd‰‰n taulukosta uusi merkkijono
      merkkijono = new String(apuMerkit);
      
      //tutkitaan onko nykyinen ja seuraava merkki
      tutkiOnkoSamat(merkkijono, muutettu1);
      
      //k‰yd‰‰n muutettu1 taulukon paikat l‰pi
      for (int i = 0; i < muutettu1.length; i++){
         //jos tyhj‰ char, lis‰t‰‰n taustamerkki
         if (muutettu1[i] == 0){
            //laitetaan uusimerkki taulukkoon
            muutettu1[i] = TAUSTA;
         }
      }
      //tehd‰‰n taulukosta merkkijono
      //ja palautetaan se.
      muutettu = new String(muutettu1);
      return muutettu;
   }
   /*
    * Yhdistet‰‰n merkit ja laskeetaan samalla pisteet
    * parametreina merkki1
    */
   public static char yhdistaOikeasti(char merkki1){
      char yhdistetty = ' ';
      //k‰yd‰‰n MERKIT-merkkijonon kaikki paikat l‰pi
      for(int i = 0; i < MERKIT.length(); i++){
         //jos merkki ei ole jonon viimeinen merkki
         if(i+1 <= MERKIT.length()){
            //jos kyseess‰ on kirjain-merkki
            if(merkki1 == MERKIT.charAt(i)){
               //annetaan yhdistetty-muuttujalle arvo, joka on seuraava MERKIT-jonon paikka
               yhdistetty = MERKIT.charAt(i+1);
               //lasketaan pisteet.
               laskePisteet(yhdistetty);
            }
         }
      }
      //palautetaan yhdistetty merkki
      return yhdistetty; 
   }
   /*
    * Tarkistetaan onko vapaita paikkoja j‰ljell‰,
    * parametreina kentt‰.
    */
   public static boolean kentallaOnTilaa(char[][] kentta2, char[][] kentta){
      //paluuarvo
      boolean onVapaitaPaikkoja = false;
      //k‰yd‰‰n l‰pi kaikki rivit
      for(int i = 0; i < kentta.length; i++){
         //k‰yd‰‰n l‰pi rivin sarakkeet.
         for(int j = 0; j < kentta[i].length; j++){
            // katsotaan onko tyhj‰‰ tilaa
            if(kentta[i][j] == 0 || kentta[i][j] == TAUSTA){
               onVapaitaPaikkoja = true;
            }
         }
      }
      //palautetaan lippumuuttuja
      return onVapaitaPaikkoja;
   }
   /*
    * Tarkistetaan onko kentt‰ muuttunut,
    * parametreina kentt‰.
    */
   public static boolean kenttaOnMuuttunut(char[][] kentta2, char[][] kentta){
      //paluuarvo
      boolean kenttaMuuttui = false;
      //k‰yd‰‰n l‰pi kaikki rivit
      for(int i = 0; i < kentta.length; i++){
        //k‰yd‰‰n l‰pi rivin sarakkeet.
         for(int j = 0; j < kentta[i].length; j++){
            // katsotaan onko pelimerkit erilaisia
            if(kentta[i][j] != Automaatti.UUSIMINPELIMERKKI && 
               kentta2[i][j] != Automaatti.UUSIMINPELIMERKKI &&
               kentta[i][j] != kentta2[i][j] ||
               Character.toLowerCase(kentta[i][j]) != Character.toLowerCase(kentta2[i][j])) {
               //k‰‰nnet‰‰n lippu
               kenttaMuuttui = true;
               //palautetaan totuuarvo heti, jos oikein
               return kenttaMuuttui;
            }
         }
      }
      //palautetaan lippumuuttuja
      return kenttaMuuttui;
   }
   /*
    * Lasketaan pisteet, 
    * parametreina pisteet ja i:n arvo, parametreina uusimerkki(merkki1)
    */
   public static void laskePisteet(char yhdistetty){
      //lasketaan pisteet apumuuttujan avulla
      double pisteet2 = Math.pow(2, yhdistetty - 'A' +1);
      
      //lis‰t‰‰n vanhat pisteet "uusiin pisteisiin"
      pisteet += (int)pisteet2;
   }
   /*
    * Tulostetaan pisterivi, parametreina pisteet.
    * Koodin selkeytt‰miseksi tehty apu metodi.
    */
   public static void tulostaPisterivi(int pisteet){
      System.out.println("Points: " +pisteet+ ".");
   }
   /*
    * pelataan peli‰, main-metodista siirretty p‰‰silmukan sis‰ltˆ.
    * parametreina liike, kentt‰
    */
   public static void pelaaPelia(char liike, char[][] kentta){
      //tehd‰‰n kent‰st‰ kopio, jotta peruutus liike onnistuu.
      char[][] kentta2 = kentta;
      //tehd‰‰n backup pisteist‰
      int pisteetBackup = pisteet;
      //lippumuuttuja.
      boolean jatketaan = true;
      //peli‰ pelaava p‰‰silmukka.
      while (jatketaan){
         //Jos lopetetaan peli.
         if (liike == QUIT){
            System.out.println(LOPETUS);
            jatketaan = false;
         }
         else if(liike == UNDO){
            //vanha kentt‰
            kentta = kopioiVanhaKentta(kentta2);
            pisteet = pisteetBackup;
            
            tulostaPisterivi(pisteet);

            tulostaTaulukko(kentta);

            //tulostetaan liikkeiden ohjeet k‰ytt‰j‰lle
            System.out.println(OHJAUS);

            //Luetaan pelaajan komento.
            liike = In.readChar();
         }
         //jos pelataan peli‰
         else if(liike == LEFT || liike == RIGHT || liike == UP || liike == DOWN){
            //tallennetaan vanha kentt‰.
            kentta2 = kopioiVanhaKentta(kentta);
            pisteetBackup = pisteet;
            //pelataan peli‰
            kentta = muutaKentta(kentta, liike);

            //jos kentt‰ on muuttunut
            boolean kenttaOnMuuttunut = kenttaOnMuuttunut(kentta2, kentta);
            //ja jos kent‰ll‰ on tilaa
            boolean kentallaOnTilaa = kentallaOnTilaa(kentta2, kentta);
          
            // kentta on muuttunut ja kentalla on tilaa
            // true true
            if (kenttaOnMuuttunut && kentallaOnTilaa){
               //tulostetaan pisterivi
               tulostaPisterivi(pisteet);
               Automaatti.sijoita(kentta, 1);
               
               tulostaTaulukko(kentta);
               //tulostetaan liikkeiden ohjeet k‰ytt‰j‰lle
               System.out.println(OHJAUS);
               //Luetaan pelaajan komento.
               liike = In.readChar();
            }
            //jos kentt‰ on muuttunut, mutta ei ole tilaa
            // true false, t‰m‰ varmuuden vuoksi
            else if (kenttaOnMuuttunut && !kentallaOnTilaa){
               //game Over tms.
               liike = QUIT;
            }
            //false true
            else if (!kenttaOnMuuttunut && kentallaOnTilaa){
               tulostaPisterivi(pisteet);
               tulostaTaulukko(kentta);
               //tulostetaan liikkeiden ohjeet k‰ytt‰j‰lle
               System.out.println(OHJAUS);
               //Luetaan pelaajan komento.
               liike = In.readChar();
            }
            //false false
            else if (!kenttaOnMuuttunut && !kentallaOnTilaa){
               //game over
               liike = QUIT;
            }
         }
         //jos liike ei sallittu
         else{
            //Tulosta pisterivi ja kentt‰ uudelleen
            tulostaPisterivi(pisteet);
            tulostaTaulukko(kentta);
            
            //tulostetaan liikkeiden ohjeet k‰ytt‰j‰lle
            System.out.println(OHJAUS);

            //Luetaan pelaajan komento.
            liike = In.readChar();
         }
      }
   }
   /*
    * Main-metodi.
    */
   public static void main(String[]args){
      /*
       * luodaan ja alustetaan muuttujia.
       */
      
      //muuttuja, joilla tarkastetaan 
      //komentoriviparametrien oikeellisuus
      int siemen = 0;
      int sivunPituus = 0;
      // muuttuja, jolla tutkitaan onko 
      // komentoriviparametri oikeanlainen.
      boolean argsOK = true;
      
      //tulostetaan tervehdysteksti, kun peli alkaa.
      System.out.println("-----------");
      System.out.println("| 2 0 4 8 |");
      System.out.println("-----------");
      
      //tehd‰‰n backup pisteist‰
      int pisteetBackup = pisteet;
      
      //Testataan komentoparametrien oikeellisuus.
      if ((args.length == 2)){
         //Testataan ovatko kaikki int-tyyppisi‰.
         try {
            siemen = Integer.parseInt(args[0]);
            sivunPituus = Integer.parseInt(args[1]);
            if(sivunPituus < Automaatti.MINSIVUNPIT)
               argsOK = false;
         }
         //napataan virhe.
         catch (NumberFormatException e){
            argsOK = false;
         }
      }
      //Katsotaan ovatko annetut syˆtteet oikeellisia.
      else{
         argsOK = false;
      }
      //jos komentoriviparametrit ei ok,
      //tulostetaan virheilmoitus ja lopetetaan peli.
      if (!argsOK){
         System.out.println(VIRHE);
         System.out.println(LOPETUS);
      }
      else{
         //Luodaan 6x6 merkkitaulukko.
         char[][] kentta = luoKentta(sivunPituus, sivunPituus);
         
         tulostaPisterivi(pisteet);
         //K‰ynnistet‰‰n automaatti.
         Automaatti.kaynnista(siemen);

         //Sijoitetaan merkit kent‰lle.
         Automaatti.sijoita(kentta, 2);

         //Tulostetaan kentt‰
         tulostaTaulukko(kentta);
      
         //Tulostetaan liikkeiden ohjeet k‰ytt‰j‰lle
         System.out.println(OHJAUS);

         //Luetaan pelaajan komento.
         char liike = In.readChar();
         
         //ollaan silmukassa niin kauan kunnes p‰‰tet‰‰n lopettaa
         //tai peli loppuu
         pelaaPelia(liike, kentta);
         
      }
   }
}