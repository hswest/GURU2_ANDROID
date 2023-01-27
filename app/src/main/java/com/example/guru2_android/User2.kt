package com.example.guru2_android

import java.io.Serializable

class User2(
    var id: String,
    var pw: String

): Serializable {
    constructor(): this("","")

}