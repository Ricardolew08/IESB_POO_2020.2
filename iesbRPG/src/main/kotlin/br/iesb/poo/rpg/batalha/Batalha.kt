package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem

fun batalha(jogador: PersonagemJogador, RPG: Rpg): String {

    val monstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO, jogadorBaseBatalha = jogador)

    var log = "--LOG DA BATALHA ENTRE ${jogador.nome} e ${if (monstro.raca == 1) "Orc" else "Goblin"} ${monstro.nome}--\n\n"

    log += "[ ~ ] MONSTRO COM ELEMENTO ${monstro.elemento}\n"
    log += "[ ~ ] JOGADOR COM ELEMENTO ${jogador.elemento}\n"

    var ataqueJ: Int = jogador.ataque
    var ataqueM: Int = monstro.ataque

    var defesaJ: Int = jogador.defesa
    var defesaM: Int = monstro.defesa

    log += "[ i ] MONSTRO INICIAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ i ] JOGADOR INICIAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n"

    val chanceterreno: Int = (1..4).random()

    log += "[ ^ ] BATALHA NO TERRENO ${chanceterreno}\n"

    // TERRENO BUFF ATK
    if (chanceterreno == jogador.elemento)
        ataqueJ++
    if (chanceterreno == monstro.elemento)
        ataqueM++

    // TERRENO DEBUFF DEF
    if ((chanceterreno + 1 == jogador.elemento || jogador.elemento == 1 && chanceterreno == 4) && defesaJ > 1)
        defesaJ--
    if ((chanceterreno + 1 == monstro.elemento || monstro.elemento == 1 && chanceterreno == 4) && defesaM > 1)
        defesaM--

    //DIFERENÇA ENTRE COMBATENTES DEBUFF DEF
    if ((monstro.elemento + 1 == jogador.elemento || jogador.elemento == 1 && monstro.elemento == 4) && defesaJ > 1)
        defesaJ--
    if ((jogador.elemento + 1 == monstro.elemento || monstro.elemento == 1 && jogador.elemento == 4) && defesaM > 1)
        defesaM--

    log += "[ f ] MONSTRO FINAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ f ] JOGADOR FINAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n\n"

    val iniciativa: Int = (0..10).random()
    var turno = 1

    if (5 + jogador.sorte > iniciativa) { // OTIMIZAR IFS COM TERNÁRIO PARA ATQUE/DEFESA DO INICIADOR
        log += "[ * ] JOGADOR INICIOU O COMBATE\n"

        while (defesaJ > 0 || defesaM > 0) {
            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM} DE DEFESA\n"

            if (defesaM <= 0) {
                log += "[ = ] JOGADOR GANHOU\n"
                log += jogador.vitoria()
                break
            }

            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ} DE DEFESA\n"

            turno++

            if (defesaJ <= 0) {
                log += "[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota()
                break
            }
        }

    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n"

        while (defesaM > 0 || defesaJ > 0) {
            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ}\n"

            if (defesaJ <= 0) {
                log += "[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota()
                break
            }

            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (defesaM <= 0) {
                log += "[ = ] JOGADOR GANHOU\n"
                log += jogador.vitoria()
                break
            }
        }
    }
    log += "\n--FIM DO COMBATE--\n"
    return log
}
