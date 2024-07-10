package com.example.kotlin.itemReader.cursor

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "PAY")
class Pay(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var amount: Long? = null,
    var txName: String? = null,
    @Column(columnDefinition = "TIMESTAMP")
    var txDateTime: LocalDateTime? = null
) {
    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    constructor(id: Long?, amount: Long?, txDateTime: LocalDateTime?) : this() {
        this.id = id
        this.amount = amount
        this.txDateTime = txDateTime
    }

    override fun toString(): String {
        return "Pay(id=$id, amount=$amount, txName=$txName, txDateTime=$txDateTime)"
    }

}
