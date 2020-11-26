package com.example.homeoffice

class UserHelperClass {
    var name: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var phone: String? = null

    constructor() {}
    constructor(name: String?, username: String?, phone: String?, email: String?, password: String?) {
        this.name = name
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
    }

}