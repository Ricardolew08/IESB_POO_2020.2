package br.iesb.poo.rpg.personagem

class PersonagemMonstro:Personagem() {

    // Orc = 1; Goblin = 2
    // Orc + Defesa; Goblin + Ataque

    var raca: Int = 0

   open fun Perder(){
        println("Monstro foi abatido. \n" )
    }

}