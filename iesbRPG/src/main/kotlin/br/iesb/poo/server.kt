package br.iesb.poo
 //Import no build.gradle
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

//post("/batalha"){}
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
            post("/jogadores/criarjogador"){
                val novojogador = call.receive<PersonagemJogador>()
                RPG.jogadores.add(novojogador)
            }


        }
    }.start(wait = true)

}