package nl.openvalue.joosten.rosanne.kotlininput.repository

import nl.openvalue.joosten.rosanne.kotlininput.data.InputEntity
import org.springframework.data.repository.CrudRepository

interface InputRepository : CrudRepository<InputEntity, Long> {
    fun findInputEntityByName(name: String): InputEntity?

}
