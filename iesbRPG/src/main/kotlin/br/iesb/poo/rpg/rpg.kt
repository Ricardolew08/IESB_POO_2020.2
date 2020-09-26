package br.iesb.poo.rpg

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

enum class TipoPersonagem { //enum basicamente criar classes no sentido de enumeração
    PERSONAGEM_MONSTRO,
    PERSONAGEM_BOSS
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()

    private val listaNomes = arrayOf(
        "Valdomiro Putão",
        "Bozonaru",
        "Lula d'Asilva Bushim Shei al Mussei",
        "Donarudu Trumpuru",
        "Montro",
        "Éffiagácê",
        "Kin John 1: Flango",
        "Kin John 2: return('The')",
        "Conde Temer",
        "Abaishar, o Assado"
    )

    var contadorJ = 0
    var contadorM = 0

    fun criarMonstro(
        tipoPersonagem: TipoPersonagem,
        jogadorBaseBatalha: PersonagemJogador
    ): PersonagemMonstro {
        val novoPersonagem = if (tipoPersonagem == TipoPersonagem.PERSONAGEM_BOSS) {
            PersonagemMonstro(
                //TEMP
                novaRaca = (1..2).random(),
                (listaNomes).random(),
                elementoMonstro = (1..4).random(),
                jogadorBase = jogadorBaseBatalha,
                idNumero = contadorM++
            )
        } else {
            PersonagemMonstro(
                novaRaca = (1..2).random(),
                (listaNomes).random(),
                elementoMonstro = (1..4).random(),
                jogadorBase = jogadorBaseBatalha,
                idNumero = contadorM++
            )
        }
        monstros.add(novoPersonagem)
        return novoPersonagem
    }
}
