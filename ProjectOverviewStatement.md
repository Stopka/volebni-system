# Project Overview Statement #

## Problém ##
Na volebních mítincích se často hlasuje tajně i veřejně a často se i volí. Nyní máme k dispozici dva samostatné systémy - jeden pro volby a jeden pro jednoduché hlasování prostřednictvím mobilních zařízení. Do obou systémů se ale voliči musí registrovat samostatně. To znamená ztrátu času a zbytečně komplikovaný proces registrace.

## Cíle projektu ##
Cílem projektu je sloučit dva naprosto samostatné projekty do jednoho. Jedním s těchto autonomních projektů jsou [Elektronické volby](https://dip.felk.cvut.cz/browse/details.php?f=F3&d=K13136&y=2011&a=cerevtom&t=bach) Tomáše Čerevky, které řeší problém tajných voleb pomocí volebních kiosků. Druhým projektem, na který navazujeme je [Androidí přenosný hlasovací systém](https://dip.felk.cvut.cz/browse/details.php?f=F3&d=K13136&y=2011&a=murinrad&t=bach) Radovana Murina. Ten řeší spíše otázku veřejného hlasování či testů. My k těmto aplikacím vytvoříme jednotný systém správy voličů.
Dále upravíme datový model systému Elektronické volby a odpovídající business logiku tak, aby odpovídal naši novým požadavkům, a abychom odstranili závady v návrhu. K datovému modelu bude potřeba přidat možnost přidání více rolí jednotlivým uživatelům a také umožnění například volení volebním komisařů, kteří mají rovněž roli voličů, ale v současném stavu se jim neobjeví možnost hlasovat. U aplikace pro mobilní telefony android zanalyzujeme problémy s připojením aplikace k serveru, pokusíme se navrhnout a implementovat nápravu.

## Kritéria úspěchu ##
  * Výsledkem by mělo být zjednodušení registračního procesu při současném používání obou systémů.
  * Odpadne nutnost registrace do každého systému zvlášť a systém bude správně fungovat.
  * Splnění všech požadavků
  * Opravení chyb předchozích projektů

## Rizika ##
V průběhu vývoje se mohou vyskytnout komplikace.
  * Chyby v analýze nebo v implementaci projektu - tento problém se budeme snažit minimalizovat důkladnou analýzou.
  * Selhání hardwaru - má pouze krátkodobé důsledky
  * Odpadnutí člena týmu


## Funkční požadavky ##
  * Systém bude umožňovat registraci voličů
  * Systém bude umožňovat ověření uživatele pomocí otisku prstu
  * Systém bude umožňovat ověření uživatele pomocí jména a hesla
  * Systém bude umožňovat tajné i veřejné hlasování
  * Systém bude umožňovat evidenci přítomnosti uživatelů
  * Systém bude umožňovat zobrazení výsledků voleb/hlasování
  * Systém bude umožňovat veřejné hlasování pomocí telefonu android
  * Systém bude umožňovat tajné hlasování pomocí volebního kiosku


## Nefunkční požadavky ##
  * Systém bude mít společnou databázi voličů pro oba systémy
  * Systém poběží na platformě Java SE/EE
  * Mobilní klient poběží na platformě Android
  * V nové registrační části bude použita souborová databáze

## FURPS ##

### Funkcionalita: ###

Stejná databáze uživatelů pro oba systémy bude usnadňovat registraci,která bude tímto ulehčená hlavně z pozice voličů, kteří se nebudou muset registrovat několikrát. Dále bude fungovat rozlišování rolí a s ním související práva uživatelů v systému a možnost přiřazení více rolí jednomu uživateli.

### Užitečnost: ###

Spojení systémů usnadní realizace voleb a hlasování a zjednoduší správu a evidenci uživatelů.

### Spolehlivost: ###

  * Při výpadku energie či pádu systému zůstanou nedojde ke ztrátě již přijatých potvrzených dat (myšleno hlasů či registrací, jež systém potvrdil jako přijaté).
  * Pokud se dojde ke ztrátě hlasu během jeho putování systémem, voliči bude umožněno hlasovat znovu.
  * Servery budou mít UPS nebo baterie.

### Výkon: ###
  * Doporučené požadavky na systém:
    * CPU 2Ghz a vyšší
    * 1GB RAM
    * 5GB volného místa na HDD
    * Připojení k síti o rychlost alespoň 10Mbit
  * Odezva systému při doporučené konfiguraci bude plynulá až do 100 současně připojených uživatelů.

### Rozšiřitelnost: ###
  * Možnost vytvořit i jiné druhy voleb či hlasování (např.: test atd.)
  * Jiné způsoby registrace (např. přes SMS)
  * Jiné způsoby ověřování totožnosti (použití dalších biometrických údajů, použití přihlašovacích certifikátů nebo čipových karet)