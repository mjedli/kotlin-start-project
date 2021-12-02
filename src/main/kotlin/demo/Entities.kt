package demo

import org.springframework.data.annotation.Id
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
class User (
    var login: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var description: String? = null,
    @Id @GeneratedValue(strategy= GenerationType.AUTO) var id: Integer? = null
)