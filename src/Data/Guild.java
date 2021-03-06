/*
 * RiftSKS ist ein Programm zum führen eines Raidkaders mit der Absicht
 * das SKS-Verfahren bei der Lootverteilung anzuwenden.
 * Copyright (C) 2013  Dirk Evers
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Data;

/**
 * Objekt welches eine Gilde im Rift Kader repräsentiert.
 * @author Dirk
 */
public class Guild {
    //Variable für den Namen der Gilde.
    String name;
    
    /**
     * Konstruktor für eine Gilde.
     * @param name Name der zu erstellenden Gilde
     */
    public Guild(String name){
        this.name = name;
    }
    
    /**
     * Ändern des Namen der Gilde
     * @param name neuer Gildenname
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Gibt den Namen der Gilde zurück.
     * @return der Name der Gilde
     */
    public String getName(){
        return name;
    }
}
