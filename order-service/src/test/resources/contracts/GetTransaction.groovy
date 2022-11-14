package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/transactions'
        headers {
            contentType('application/json')
        }
    }

    response {
        status OK()

        contentType('application/json')
    }
}
