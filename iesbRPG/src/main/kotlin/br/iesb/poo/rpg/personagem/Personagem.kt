package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

open class Personagem(nick: String, element: Int) {

    var id: Int = -1
    var nome: String = nick

    var sorte: Int = 0
    var nivel: Int = 1
    var dinheiro: Int = 30

    var ataque: Int = 1
    var defesa: Int = 1

    // Água - 1; Fogo - 2; Ar - 3; Terra - 4;
    // Água > Fogo > Ar > Terra > Água [...]

    var elemento: Int = element

    //INTERFACES?
    protected open fun genId(rpgAtual: Rpg): Int {
        return 0
    }

    open fun derrota(rpg: Rpg): String {
        return ""
    }
}