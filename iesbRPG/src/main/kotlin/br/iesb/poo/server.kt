package br.iesb.poo

import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.personagem.PersonagemJogador
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.iesb.poo.rpg.batalha.batalha

val RPG : Rpg = Rpg()

fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }
            get("/") {
                call.respondText("<h1>Hello Kenniston !!!! </h1> </br> <h2>Programação Orientada a Objetos P1</h2> ", ContentType.Text.Html)
            }
            get("/jogadores"){
                call.respond(RPG.jogadores)
            }
            get("/monstros"){
                call.respond(RPG.monstros)
            }
            // check disponibilidade do nome do jogador
            post("/jogadores/criarjogador"){
                val novojogador = call.receive<PersonagemJogador>()
                RPG.jogadores.add(PersonagemJogador(novojogador.classe, nomeJogador = (novojogador.nome as String), novojogador.elemento, RPG.contador++))
                call.respondText("Criado com sucesso ${novojogador.nome} de ID:${RPG.contador-1}", status = HttpStatusCode.Created)
            }
            post("/batalha/{idURL}"){
                val idJogador = call.parameters["idURL"]?.toInt()
                val log : String = batalha(RPG.jogadores.filter{it.id==idJogador}[0], RPG)
                call.respondText(log)
            }
            //DELETE!!!! do monstro que perdeu a vida
        }
    }.start(wait = true)
}