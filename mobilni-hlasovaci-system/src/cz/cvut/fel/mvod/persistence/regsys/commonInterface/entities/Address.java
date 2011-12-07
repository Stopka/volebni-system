/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities;

/**
 * Entita reprezentující adresu účastníka. Adresa má poviné jen město, číslo
 * popisné a ulice jsou nepoviné údaje.
 * @author Lahvi
 */
public class Address {
    private String city;
    private String street;
    private String number;

    /**
     * Konstruktor nastavující všechny 3 parametry. Povinný je pouze parametr 
     * {@code city}, ostatní mohou být {@code null}.
     * @param city  Město dané adresy.
     * @param street Ulice dané adresy.
     * @param number  Číslo popisně.
     */
    public Address(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }
    /**
     * Konstruktor vytvářející adresu pouze s poviným parametrem {@code city}. Ostatní
     * parametry, ulice a čp, budou nastaveny na {@code null}.
     * @param city Město dané adresy.
     */
    public Address(String city) {
        this(city, null, null);
    }
    /**
     * Vrací město dané adresy.
     * @return Město.
     */
    public String getCity() {
        return city;
    }
    /**
     * Nastavuje adrese nové město.
     * @param city Nové město adresy.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Vrací číslo popisné dané adresy.
     * @return Číslo Popisné.
     */
    public String getNumber() {
        return number;
    }
    /**
     * Nastavuje číslo popisné dané adresy.
     * @param number nové ČP.
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * Vrací ulici dané adresy.
     * @return ulice.
     */
    public String getStreet() {
        return street;
    }
    /**
     * Nastavuje novou ulici dané adresy.
     * @param street 
     */
    public void setStreet(String street) {
        this.street = street;
    }
    
    
    
    
}
