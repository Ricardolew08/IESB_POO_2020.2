package br.iesb.poo.rpg.loja

import br.iesb.poo.rpg.personagem.PersonagemJogador
import java.io.File
import java.nio.file.Paths


open class Itens(identrada : String?,
tipoentrada: String,
nomeentrada: String,
efeitoentrada: String,
precoentrada: Int,
jogador: PersonagemJogador) {

    var preco: Int = -1

    var id: String? = ""

    var nome: String = ""

    var efeito: String = ""

    var tipo: String = ""


    init {


        id = identrada

        nome = nomeentrada

        efeito = efeitoentrada


        //1-pocao 2-arma 3-armadura
        tipo = tipoentrada

        preco = precoentrada





    }


    //1-pocao 2-arma 3-armadura


//    var retorno : Boolean = buscar(id)

    open fun buscar(id: String?): ArrayList<String> {
//        var retorno: Boolean = false
        var arr = arrayListOf<String>()
        var teste = arrayListOf<String>()
//        val path = Paths.get("").toAbsolutePath().toString()

        File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/loja/item.txt").forEachLine {//[0] - id | [1] - tipo | [2] -nome | [3] -efeito | [4] - preco
            arr = it.split("|") as ArrayList<String>
//            retorno = true
//            it.any{it == id}
            if (arr[0] == id) {

                teste = arr

            }
        }

        return teste
    }

    open fun check_dinheiro(jogador:PersonagemJogador, precoentrada: Int): Boolean{
        var retorno: Boolean

        if(jogador.dinheiro >= precoentrada) {

            jogador.dinheiro = jogador.dinheiro - precoentrada


            return true

//            log+="${preco} moedas de ouro foram debitadas!"

        }else{
//            log+="Você não tem moedas de ouro suficientes para a transação!"

            return false
        }

    }

//    open fun EFEITO

}



