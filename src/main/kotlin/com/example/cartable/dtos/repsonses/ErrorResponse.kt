package com.example.cartable.dtos.repsonses

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorResponse {
    var status = false
    var message: String? = null
    var data: Any? = null
}
