package com.example.guru2_android

import java.io.Serializable

class User(
    var id: String,
    var pw: String,
    var email: String,
    var name: String

): Serializable {
    constructor(): this("","", "", "")

}