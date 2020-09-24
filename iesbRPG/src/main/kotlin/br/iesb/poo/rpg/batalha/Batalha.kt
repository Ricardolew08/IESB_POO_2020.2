package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

fun batalha(monstro: PersonagemMonstro, jogador: PersonagemJogador) {


    var ataqueJ: Int = jogador.ataque
    var ataqueM: Int = monstro.ataque

    var defesaJ: Int = jogador.defesa
    var defesaM: Int = monstro.defesa

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
        println("Você pode atacar o monstro primeiro! \n" +
                "Utilize sua vantagem!")

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

        println("EMBOSCADA! Monstro começou a te atacar.")

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
}

fun main() {

   // batalha(monstro = PersonagemMonstro(), jogador = PersonagemJogador())

}