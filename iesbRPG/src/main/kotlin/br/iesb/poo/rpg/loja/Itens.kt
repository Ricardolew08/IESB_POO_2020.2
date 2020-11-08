package br.iesb.poo.rpg.loja

import java.io.File



open class Itens(id : String?) {

    val preco: Int = -1

    val id: String? = id

    val nome: String = ""

    val efeito : String = ""


    //1-pocao 2-arma 3-armadura
    val tipo : Int = -1


    open fun buscar(id: String){
        var retorno: Boolean = false
        File("itens.txt").forEachLine { if(it.any(id::contains)) {
            retorno = true
            val arr = arrayOf(it.split("|"))
        } }

    }
}



