package com.example.validator

class User {

    var id: Int = 0
    var name: String = ""
    var age: Int = 0

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    constructor() {
    }
}