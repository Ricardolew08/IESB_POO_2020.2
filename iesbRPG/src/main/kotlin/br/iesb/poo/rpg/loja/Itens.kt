package br.iesb.poo.rpg.loja

import br.iesb.poo.rpg.personagem.PersonagemJogador
import java.io.File
import java.nio.file.Paths


open class Itens(
    identrada: String?,
    tipoentrada: String,
    nomeentrada: String,
    efeitoentrada: String,
    precoentrada: Int,
    jogador: PersonagemJogador
) {

    var preco: Int = -1

    var id: String? = ""

    var nome: String = ""

    var efeito: String = ""

    var tipo: String = ""

    var qtd: Int = 0


    init {


        id = identrada

        nome = nomeentrada

        efeito = efeitoentrada


        //1-pocao 2-arma 3-armadura
        tipo = tipoentrada

        preco = precoentrada


    }

    open fun buscar(id: String?): ArrayList<String> {
        var arrAux: ArrayList<String>
        var retorno = arrayListOf<String>()

        File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/loja/item.txt").forEachLine {//[0] - id | [1] - tipo | [2] -nome | [3] -efeito | [4] - preco
            arrAux = it.split("|") as ArrayList<String>
            if (arrAux[0] == id) {
                retorno = arrAux
            }
        }
        return retorno
    }

//    open fun check_dinheiro(jogador: PersonagemJogador, precoentrada: Int): Boolean {
//        var retorno: Boolean
//
//        if (jogador.dinheiro >= precoentrada) {
//
//            jogador.dinheiro = jogador.dinheiro - precoentrada
//
//
//            return true
//
////            log+="${preco} moedas de ouro foram debitadas!"
//
//        } else {
////            log+="Você não tem moedas de ouro suficientes para a transação!"
//
//            return false
//        }
//
//    }

    open fun efeito(jogador: PersonagemJogador, id: String?) {
        var eff = buscar(id)[3].split(".") as ArrayList<String>
        if (eff[0] == "hp") { //jogador.eff[0]
            jogador.vida = jogador.vida + eff[1].toInt()

        } else if (eff[0] == "atk") {
            jogador.ataque = jogador.ataque + eff[1].toInt()

        } else if (eff[0] == "def") {
            jogador.defesa = jogador.defesa + eff[1].toInt()
        }
        //jogador.removerItem(id)
    }

}



