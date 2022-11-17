package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Get One Transaction"

    request {
        method 'GET'
//        url "/transactions/$transactionId"
        url $(regex('/api/orders/'+ uuid()))
        headers {
            contentType applicationJson()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }

        body (
                "transactionId": anyUuid(),
                amount: "CHF 1000",
                iban: "CH93-0000-0000-0000-0000-0",
                date: "01-22-2022",
                description: "Online payment CHF"
        )


    }
}
