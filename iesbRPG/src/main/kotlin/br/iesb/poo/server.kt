package br.iesb.poo
//Import no build.gradle
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro
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

//post("/batalha"){}
val RPG : Rpg = Rpg()



fun main() {

    var retorno = batalha(PersonagemMonstro(1, "noem", 3), PersonagemJogador(1, "sla", 2), RPG)

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
            post("/jogadores/criarjogador"){
                val novojogador = call.receive<PersonagemJogador>()
                RPG.jogadores.add(novojogador)
            }
            get("/batalha"){
                call.respondText(retorno)
            }

        }
    }.start(wait = true)

}