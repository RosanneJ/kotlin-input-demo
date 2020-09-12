package nl.openvalue.joosten.rosanne.kotlininput.controller

import nl.openvalue.joosten.rosanne.kotlininput.data.Greeting
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
@CrossOrigin(origins = ["http://localhost:4200"], allowedHeaders = ["*"])
class KotlinInputRestController {
    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

}
