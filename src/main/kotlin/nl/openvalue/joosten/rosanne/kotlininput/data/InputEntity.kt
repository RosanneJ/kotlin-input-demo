package nl.openvalue.joosten.rosanne.kotlininput.data

import javax.persistence.*

@Entity
@Table(name = "input")
class InputEntity(
        @Id
        @GeneratedValue
        val id: Long = -1,

        @Column(name = "name")
        val name: String = "",

        @Column(name = "yearsOfExperience")
        val yearsOfExperience: Long = -1,

        @Column(name = "programming1")
        val programming1: String = ""
){}
