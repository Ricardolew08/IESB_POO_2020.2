package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem
import br.iesb.poo.rpg.personagem.PersonagemAjudante
import br.iesb.poo.rpg.personagem.PersonagemMonstro

fun batalha(jogador: PersonagemJogador, RPG: Rpg, ajudante: PersonagemAjudante?): String {

    val monstro: PersonagemMonstro = if ((1..100).random() >= 5) {
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO, jogadorBaseBatalha = jogador)
    } else {
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CORINGA, jogadorBaseBatalha = jogador)
    }

    val racaMonstro = arrayOf("Orc", "Goblin", "Gnomio")
    val afinidade = arrayOf("Água", "Fogo", "Ar", "Terra")

    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
        "--LOG DA BATALHA ENTRE ${jogador.nome} e ${racaMonstro[monstro.raca]} ${monstro.nome} DE NÍVEL ${monstro.nivel}--\n\n"

    // INÍCIO CÁLCULO DOS ATRIBUTOS

    log += "[ ~ ] MONSTRO COM ELEMENTO ${afinidade[monstro.elemento-1]}\n"
    log += "[ ~ ] JOGADOR COM ELEMENTO ${afinidade[jogador.elemento-1]}\n"

    if (jogador.durabilidadeataque == 0) {
        jogador.removerItem(jogador)
    } else if (jogador.durabilidadeataque != 0) {
        jogador.durabilidadeataque--
    }

    if (jogador.durabilidadedefesa == 0) {
        jogador.removerItem(jogador)
    } else if (jogador.durabilidadedefesa != 0) {
        jogador.durabilidadedefesa--
    }

    var ataqueJ: Int = jogador.ataque + jogador.ataqueitem
    var ataqueM: Int = monstro.ataque

    var defesaJ: Int = jogador.defesa + jogador.defesaitem
    var defesaM: Int = monstro.defesa

    log += "[ i ] MONSTRO INICIAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ i ] JOGADOR INICIAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n"

    val chanceterreno: Int = (1..4).random()
    val tipoTerreno = arrayOf("AQUÁTICO", "VULCÂNICO", "AÉREO", "MONTANHOSO")

    log += "[ ^ ] BATALHA NO TERRENO ${tipoTerreno[chanceterreno-1]}\n"

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

    if (ajudante != null) {
        ajudante.batalhas++
        if (ajudante.batalhas < 3) {
            ataqueJ = ataqueJ + ajudante.ataque
            defesaJ = defesaJ + ajudante.defesa
            jogador.sorte = jogador.sorte + ajudante.sorte
        } else {
            ajudante.encerrarcontrato(RPG, jogador)
        }
    }

    log += "[ f ] MONSTRO FINAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ f ] JOGADOR FINAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n\n"

    // FIM CÁLCULOS DE ATRIBUTOS
    // INÍCIO COMBATE

    val iniciativaM: Int = (0..10).random()
    var turno = 1

    val INICIOTURNO = 7

    if (INICIOTURNO + jogador.sorte > iniciativaM) {
        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        while (defesaJ > 0 || defesaM > 0) {
            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM $defesaM DE DEFESA\n"

            if (defesaM <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                break
            }

            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM $defesaJ DE DEFESA\n"

            turno++

            if (defesaJ <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                break
            }
        }

        log += "\n--FIM DO COMBATE--\n"
    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"

        while (defesaM > 0 || defesaJ > 0) {
            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ}\n"

            if (defesaJ <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                break
            }

            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (defesaM <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)
                break
            }
        }

        log += "\n--FIM DO COMBATE--\n"
    }

    return log
}

fun batalhaChefe(jogador: PersonagemJogador, RPG: Rpg, ajudante: PersonagemAjudante?): String {

    val chefe: PersonagemMonstro =
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CHEFE, jogadorBaseBatalha = jogador)


    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
        "--LOG DA BATALHA ENTRE ${jogador.nome} E CHEFE ${chefe.nome}--\n\n"

    if (jogador.durabilidadeataque == 0) {
        jogador.removerItem(jogador)
    } else if (jogador.durabilidadeataque != 0) {
        jogador.durabilidadeataque--
    }

    if (jogador.durabilidadedefesa == 0) {
        jogador.removerItem(jogador)
    } else if (jogador.durabilidadedefesa != 0) {
        jogador.durabilidadedefesa--
    }

    var ataqueJ: Int = jogador.ataque + jogador.ataqueitem
    var ataqueM: Int = chefe.ataque

    var defesaJ: Int = jogador.defesa + jogador.defesaitem
    var defesaM: Int = chefe.defesa

    if (ajudante != null) {
        ajudante.batalhas++
        if (ajudante.batalhas < 3) {
            ataqueJ = ataqueJ + ajudante.ataque
            defesaJ = defesaJ + ajudante.defesa
            jogador.sorte = jogador.sorte + ajudante.sorte
        } else {
            ajudante.encerrarcontrato(RPG, jogador)
        }
    }

    log += "[ f ] CHEFE FINAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ f ] JOGADOR FINAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n\n"

    val iniciativaM: Int = (0..10).random()
    var turno = 1

    val INICIOTURNO = 7

    if (INICIOTURNO + jogador.sorte > iniciativaM) {

        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        while (defesaJ > 0 || defesaM > 0) {
            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM $defesaM DE DEFESA\n"

            if (defesaM <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                break
            }

            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM $defesaJ DE DEFESA\n"

            turno++

            if (defesaJ <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                break
            }
        }
    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"

        while (defesaM > 0 || defesaJ > 0) {
            defesaJ -= ataqueM
            log += "TURNO ${turno}: MONSTRO ATACOU COM $ataqueM JOGADOR FICOU COM ${defesaJ}\n"

            if (defesaJ <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrotaChefe(RPG)
                break
            }

            defesaM -= ataqueJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $ataqueJ MONSTRO FICOU COM ${defesaM}\n"

            turno++

            if (defesaM <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                break
            }
        }

        log += "\n--FIM DO COMBATE--\n"
    }

    return log
}
