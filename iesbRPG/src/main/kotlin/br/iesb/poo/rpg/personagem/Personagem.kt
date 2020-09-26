package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

open class Personagem(nick: String, element: Int, idNumero: Int) {

    var id: Int = idNumero
    var nome: String = nick

    var nivel: Int = 1
    var dinheiro: Int = 0

    var ataque: Int = 1
    var defesa: Int = 1

    // Água - 1; Fogo - 2; Ar - 3; Terra - 4;
    // Água > Fogo > Ar > Terra > Água [...]
    // Terreno igual ao seu, +1 de defesa; Ganho na hierarquia de elemento em relação ao oponente = +1 ataque

    var elemento: Int = element

    protected open fun genId(Seed: Int): Int {
        return 0
    }

    //INTERFACE
    open fun derrota(rpg: Rpg): String {
        return ""
    }
}