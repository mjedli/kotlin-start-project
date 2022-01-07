package com.example.demo.entity

import org.springframework.data.annotation.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
class User (
    var login: String? = null,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null
)