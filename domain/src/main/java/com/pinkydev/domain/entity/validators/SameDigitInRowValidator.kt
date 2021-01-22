package com.pinkydev.domain.entity.validators

import com.pinkydev.domain.entity.validators.Validator


class SameDigitInRowValidator : Validator<String> {

    private val pattern = Regex("^\\d*(\\d)\\1{2,}\\d*")

    override fun isValid(value: String): Boolean = pattern.matches(value).not()
}