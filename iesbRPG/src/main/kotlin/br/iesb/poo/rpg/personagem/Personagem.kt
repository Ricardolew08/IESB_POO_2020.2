package br.iesb.poo.rpg.personagem


open class Personagem(nick: String, element: Int) {

    var nome: String? = null

    var nivel: Int = 1
    var dinheiro: Int = 0

    var ataque: Int = 1
    var defesa: Int = 1

    // Água - 1; Fogo - 2; Ar - 3; Terra - 4;
    // Água > Fogo > Ar > Terra > Água [...]
    // Terreno igual ao seu, +1 de defesa; Ganho na hierarquia de elemento em relação ao oponente = +1 ataque

    var elemento: Int = 0

    init {
        nome = nick
        elemento = element
    }

    protected open fun genId(Seed: Int): Int {
        return 0
    }

    protected fun morrer() {

    }

    open fun derrota(): String {
        return ""
    }
}