package com.example.validator

class User {

    var id: Int = 0
    var name: String = ""
    var age: Int = 0
    var imageurl: String = ""

    constructor(name: String, age: Int, imageurl: String) {
        this.name = name
        this.age = age
        this.imageurl = imageurl
    }

    constructor() {
    }
}