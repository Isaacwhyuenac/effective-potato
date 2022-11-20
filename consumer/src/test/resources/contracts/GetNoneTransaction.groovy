package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Get None Transaction"

    request {
        method 'GET'
        url '/transaction/A8AEC7C1-E05C-43D7-9BEB-5C06ACF8C9EE'.toLowerCase()
        headers {
            contentType applicationJson()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }

        body ([])

    }
}
