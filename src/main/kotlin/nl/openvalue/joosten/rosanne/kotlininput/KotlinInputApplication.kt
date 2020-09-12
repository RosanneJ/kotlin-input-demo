package nl.openvalue.joosten.rosanne.kotlininput

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinInputApplication

fun main(args: Array<String>) {
	runApplication<KotlinInputApplication>(*args)
}
