package com.example.cartable.repositories

import com.example.cartable.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository : JpaRepository<Item, Long?> {
    fun existsByName(name: String): Boolean
            @Query(value = "SELECT p.email as partnerEmail, p.logo as partnerLogo, p.phone as partnerPhone, pw.fiscal_balance as partnerFiscalBalance, p.name as partnerName,  p.display_name as partnerDisplayName, p.uuid as partnerUuid, p.partner_id as partnerId from partner_wallets pw  left join partners p on p.partner_id = pw.partner_id WHERE pw.partner_id=:partnerId", nativeQuery = true)
    fun getFullWalletDetails(@Param("partnerId") partnerId: String?): List<Item?>?
}
