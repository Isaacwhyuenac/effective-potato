//package contracts
//
//
//import org.springframework.cloud.contract.spec.Contract
//
//Contract.make {
//    description "Create Transaction"
//    request {
//        method POST()
//        url '/transaction'
//        headers {
//            contentType(applicationJson())
//        }
//
//        body(
//            amount: "CHF 1000",
//            iban: "CH93-0000-0000-0000-0000-0",
//            date: "01-22-2022",
//            description: "Online payment CHF"
//        )
//    }
//
//    response {
//        status CREATED()
//        headers {
//            contentType(applicationJson())
//            location("http://localhost:8081/5EF60C78-2D38-4936-A736-235E0A6B2177")
//        }
//
////        body(
////                id: anyUuid(),
////                amount: fromRequest().query("amount"),
////                iban: fromRequest().query("iban"),
////                date: fromRequest().query("date"),
////                description: fromRequest().query("description")
////        )
//    }
//}
