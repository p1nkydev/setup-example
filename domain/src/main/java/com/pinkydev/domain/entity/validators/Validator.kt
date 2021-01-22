package com.pinkydev.domain.entity.validators

interface Validator<in T> {
    /**
     * @return true if validation is successful
     */
    fun isValid(value: T): Boolean
}