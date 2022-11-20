//package contracts
//
//import groovy.json.JsonOutput
//import org.springframework.cloud.contract.spec.Contract
//
//def json = JsonOutput.toJson([
//        [
//                id         : "6b8bdc7d-a91a-4e3f-a299-cb621590f337",
//                amount     : "CHF 1000",
//                iban       : "CH93-0000-0000-0000-0000-0",
//                date       : "01-22-2022",
//                description: "Online payment CHF"
//        ]
//])
//
//Contract.make {
//    description "Create Transaction Event"
//    label 'CreateTransactionEvent'
//
//    input {
//        triggeredBy('sendMessage()')
//    }
//
//    outputMessage {
//        sentTo("transaction")
//        body(json)
////        headers {
////            header('kafka_messageKey', 'id-6b8bdc7d-a91a-4e3f-a299-cb621590f337')
////        }
//    }
//
//
//}