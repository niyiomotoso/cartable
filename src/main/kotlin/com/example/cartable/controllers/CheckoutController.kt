import com.example.cartable.dtos.CheckoutDto
import com.example.cartable.dtos.OrderReceipt
import com.example.cartable.services.CheckoutService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController(private val checkoutService: CheckoutService) {
    @PostMapping("/process")
    fun processCheckout(@RequestBody checkoutDto: CheckoutDto): ResponseEntity<OrderReceipt> {
        val orderReceipt = checkoutService.processCheckout(checkoutDto.customerId)

        return ResponseEntity(orderReceipt, HttpStatus.OK)
    }
}