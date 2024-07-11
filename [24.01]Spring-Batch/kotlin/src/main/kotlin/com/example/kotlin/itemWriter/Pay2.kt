package com.example.kotlin.itemWriter

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "PAY2")
class Pay2(
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

    constructor(amount: Long?, txName: String?, txDateTime: LocalDateTime?) : this() {
        this.amount = amount
        this.txName = txName
        this.txDateTime = txDateTime
    }

    override fun toString(): String {
        return "Pay(id=$id, amount=$amount, txName=$txName, txDateTime=$txDateTime)"
    }

}
