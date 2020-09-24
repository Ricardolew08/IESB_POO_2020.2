package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem

fun batalha(jogador: PersonagemJogador, RPG: Rpg):String {


    var monstro = RPG.CriarPersonagem(0, "0", 0, TipoPersonagem.PERSONAGEM_MONSTRO)

    var log: String = "log da batalha\n"

    var ataqueJ: Int = jogador.ataque
    var ataqueM: Int = monstro.ataque

    var defesaJ: Int = jogador.defesa
    var defesaM: Int = monstro.defesa

    ataqueM += (0..3).random()
    defesaM += (0..3).random()

    val chanceterreno: Int = (1..4).random()

    if (chanceterreno == jogador.elemento)
        ataqueJ++
    if (chanceterreno == monstro.elemento)
        ataqueM++

    if (chanceterreno + 1 == jogador.elemento || jogador.elemento == 1 && chanceterreno == 4)
        defesaJ--
    if (chanceterreno + 1 == monstro.elemento || monstro.elemento == 1 && chanceterreno == 4)
        defesaM--

    if (monstro.elemento + 1 == jogador.elemento || jogador.elemento == 1 && monstro.elemento == 4)
        defesaJ--
    if (jogador.elemento + 1 == monstro.elemento || monstro.elemento == 1 && jogador.elemento == 4)
        defesaM--



    val iniciativa: Int = (0..10).random()
    if (5 + jogador.sorte > iniciativa) {
        log += "Você atacou o monstro primeiro!\n"

        while (defesaJ > 0 || defesaM > 0) {
            defesaM -= ataqueJ
            if (defesaM <= 0) {
                jogador.Ganhar()
                break
            }

            defesaJ -= ataqueM
            if (defesaJ <= 0) {
                jogador.Perder()
                break
            }

        }


    } else
        log += "EMBOSCADA! Monstro começou a te atacar.\n"

    while (defesaM > 0 || defesaJ > 0) {
        defesaJ -= ataqueM
        if (defesaJ <= 0) {
            jogador.Perder()
            break
        }

        defesaM -= ataqueJ
        if (defesaM <= 0) {
            jogador.Ganhar()
            break
        }
    }

    return log
}
