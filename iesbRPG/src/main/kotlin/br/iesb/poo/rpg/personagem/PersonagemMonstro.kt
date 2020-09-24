package br.iesb.poo.rpg.personagem

class PersonagemMonstro(raca: Int,
                        nick: String,
                        element: Int): Personagem(nick,element) {

    // Orc = 1; Goblin = 2
    // Orc + Defesa; Goblin + Ataque

    var raca: Int = 0

    init{
        this.raca = raca
    }

    open fun Perder(){
        println("Monstro foi abatido. \n" )
    }

}