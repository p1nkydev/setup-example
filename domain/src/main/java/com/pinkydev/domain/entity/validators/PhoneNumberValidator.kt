package com.pinkydev.domain.entity.validators

class PhoneNumberValidator : Validator<String> {

    private val pattern = Regex("^\\+994((50)|(51)|(55)|(70)|(77))[\\d]{0,7}\$")

    override fun isValid(value: String): Boolean {
        return pattern.matches(value)
    }
}