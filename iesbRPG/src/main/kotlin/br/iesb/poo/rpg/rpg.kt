package br.iesb.poo.rpg

import br.iesb.poo.rpg.personagem.Personagem
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

enum class TipoPersonagem {
    PERSONAGEM_MONSTRO,
    PERSONAGEM_JOGADOR
}
class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()



    fun CriarPersonagem(
            nome: String,
            elemento: Int,
            tipo: TipoPersonagem
    ) {
        val personagem = if( tipo == TipoPersonagem.PERSONAGEM_JOGADOR ) {


        }

    }
}