package nl.openvalue.joosten.rosanne.kotlininput

import nl.openvalue.joosten.rosanne.kotlininput.controller.KotlinInputRestController
import nl.openvalue.joosten.rosanne.kotlininput.repository.InputRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class KotlinInputApplicationTests {
    @Autowired
    lateinit var kotlinInputRestController: KotlinInputRestController

    @MockBean
    lateinit var inputRepository: InputRepository

    @Test
    fun contextLoads() {
    }

}
