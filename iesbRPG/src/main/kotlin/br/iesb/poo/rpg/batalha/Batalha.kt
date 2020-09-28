package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem
import br.iesb.poo.rpg.personagem.PersonagemMonstro

fun batalha(jogador: PersonagemJogador, RPG: Rpg): String {

    val monstro: PersonagemMonstro = if ((1..100).random() >= 5) {
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO, jogadorBaseBatalha = jogador)
    } else {
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CORINGA, jogadorBaseBatalha = jogador)
    }

    val racaMonstro = arrayOf("Orc", "Goblin", "Gnomio")

    var log = "--LOG DA BATALHA ENTRE ${jogador.nome} e ${racaMonstro[monstro.raca]} ${monstro.nome} DE NÍVEL ${monstro.nivel}--\n\n"

    // INÍCIO CÁLCULO DOS ATRIBUTOS

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

    // FIM CÁLCULOS DE ATRIBUTOS
    // INÍCIO COMBATE

    val iniciativaM: Int = (0..10).random()
    var turno = 1

    if (7 + jogador.sorte > iniciativaM) { //TODO SIMPLIFICAR IFS COM TERNÁRIO PARA ATQUE/DEFESA DO INICIADOR OU UTILIZAR ATACANTE/DEFENSOR NO INÍCIO DO CÓDIGO
        log += "[ * ] JOGADOR INICIOU O COMBATE\n"

        while (defesaJ > 0 || defesaM > 0) {
            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM $defesaM DE DEFESA\n"

            if (defesaM <= 0) {
                log += "[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                break
            }

            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM $defesaJ DE DEFESA\n"

            turno++

            if (defesaJ <= 0) {
                log += "[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
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
                log += jogador.derrota(RPG)
                break
            }

            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (defesaM <= 0) {
                log += "[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                break
            }
        }
    }
    log += "\n--FIM DO COMBATE--\n"
    return log
}
