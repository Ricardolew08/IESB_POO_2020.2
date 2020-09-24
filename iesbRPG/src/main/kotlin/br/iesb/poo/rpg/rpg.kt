package br.iesb.poo.rpg

import br.iesb.poo.rpg.personagem.Personagem
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

enum class TipoPersonagem { //enum basicamente criar classes no sentido de enumeração
    PERSONAGEM_MONSTRO,
    PERSONAGEM_JOGADOR
}
class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()

    val arraynomemonstro = arrayOf("Bolsonaro", "Michele", "Tump", "Valdemiro Putao")

    fun CriarPersonagem(
            classe: Int,
            nome: String,
            elemento: Int,
            tipo: TipoPersonagem
    ):Personagem{
        val personagem = if( tipo == TipoPersonagem.PERSONAGEM_JOGADOR ) {
            PersonagemJogador(classe, nome, elemento)
        }else{
            var novaraca = (1..2).random()
            var tamanhoarray = arraynomemonstro.size-1
            var nomevindoapi = arraynomemonstro[(0..tamanhoarray).random()]
            var elementorandomico = (1..4).random()
            PersonagemMonstro(novaraca, nomevindoapi, elementorandomico)
        }
        if(personagem is PersonagemJogador){
            jogadores.add(personagem)
        }else monstros.add(personagem as PersonagemMonstro)

        return personagem
    }

}
