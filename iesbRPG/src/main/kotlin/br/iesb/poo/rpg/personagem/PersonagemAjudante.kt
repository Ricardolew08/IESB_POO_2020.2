package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemAjudante (classeAjudante: Int,
nomeAjudante: String,
elementoAjudante: Int,
jogadorBase: PersonagemJogador,
rpg: Rpg):Personagem(nomeAjudante, elementoAjudante) {

    var classe: Int = classeAjudante


}