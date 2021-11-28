package demo

import org.springframework.data.annotation.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
class User (
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue var id: Long? = null
)