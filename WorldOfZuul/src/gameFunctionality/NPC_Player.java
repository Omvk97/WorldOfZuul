/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameFunctionality;

/**
 *
 * @author steff
 */
public class NPC_Player {

    String NPC_type;
    String NPC_name;
    String NPC_drecription;

    public NPC_Player(String NPC_type, String NPC_name, String NPC_drecription) {
        this.NPC_type = NPC_type;
        this.NPC_name = NPC_name;
        this.NPC_drecription = NPC_drecription;

    }

    public String getNPC_type() {
        return NPC_type;
    }

    public String getNPC_name() {
        return NPC_name;
    }

    public String getNPC_drecription() {
        return NPC_drecription;
    }

    @Override
    public String toString() {
        return NPC_type + NPC_name + NPC_drecription;
    }
}
