package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/transactions'
        headers {
            header('Content-Type': 'application/json')
        }
    }

    response {
        status OK()
        headers {
            header('Content-Type': 'application/json')

        }
    }
}
