package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Get One Transaction"

    request {
        method 'GET'
//        url $(regex('/transaction/'+ uuid()))
        url '/transaction/5EF60C78-2D38-4936-A736-235E0A6B2177'.toLowerCase()
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
                id: "5EF60C78-2D38-4936-A736-235E0A6B2177",
                amount: "CHF 1000",
                iban: "CH93-0000-0000-0000-0000-0",
                date: "01-22-2020",
                description: "Online payment CHF"
        )

    }
}
