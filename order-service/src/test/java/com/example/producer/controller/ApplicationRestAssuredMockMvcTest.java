package com.example.producer.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.entity.Transaction;
import com.example.producer.service.TransactionService;
import com.example.producer.utils.PageUtil;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/*
curl --request POST \
  --url 'https://dev-qrj9gibi.us.auth0.com/oauth/token' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data grant_type=client_credentials \
  --data 'client_id=1uHwtjmPwwmjroQqbTia9LNAQmyDGmVh' \
  --data client_secret=xxxx \
  --data audience=https://dev-qrj9gibi.us.auth0.com/api/v2/ | jq -r '.access_token' | pbcopy

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Slf4j
public class ApplicationRestAssuredMockMvcTest {

  private static final String AUTHORIZATION = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImlPN0RHR0RUeUpUM0ZlWkowZ29qNyJ9.eyJpc3MiOiJodHRwczovL2Rldi1xcmo5Z2liaS51cy5hdXRoMC5jb20vIiwic3ViIjoiMXVId3RqbVB3d21qcm9RcWJUaWE5TE5BUW15REdtVmhAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vZGV2LXFyajlnaWJpLnVzLmF1dGgwLmNvbS9hcGkvdjIvIiwiaWF0IjoxNjY5Njk3MTQ3LCJleHAiOjE2Njk3ODM1NDcsImF6cCI6IjF1SHd0am1Qd3dtanJvUXFiVGlhOUxOQVFteURHbVZoIiwic2NvcGUiOiJyZWFkOmNsaWVudF9ncmFudHMgY3JlYXRlOmNsaWVudF9ncmFudHMgZGVsZXRlOmNsaWVudF9ncmFudHMgdXBkYXRlOmNsaWVudF9ncmFudHMgcmVhZDp1c2VycyB1cGRhdGU6dXNlcnMgZGVsZXRlOnVzZXJzIGNyZWF0ZTp1c2VycyByZWFkOnVzZXJzX2FwcF9tZXRhZGF0YSB1cGRhdGU6dXNlcnNfYXBwX21ldGFkYXRhIGRlbGV0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSByZWFkOnVzZXJfY3VzdG9tX2Jsb2NrcyBjcmVhdGU6dXNlcl9jdXN0b21fYmxvY2tzIGRlbGV0ZTp1c2VyX2N1c3RvbV9ibG9ja3MgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmhvb2tzIHVwZGF0ZTpob29rcyBkZWxldGU6aG9va3MgY3JlYXRlOmhvb2tzIHJlYWQ6YWN0aW9ucyB1cGRhdGU6YWN0aW9ucyBkZWxldGU6YWN0aW9ucyBjcmVhdGU6YWN0aW9ucyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOmluc2lnaHRzIHJlYWQ6dGVuYW50X3NldHRpbmdzIHVwZGF0ZTp0ZW5hbnRfc2V0dGluZ3MgcmVhZDpsb2dzIHJlYWQ6bG9nc191c2VycyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgdXBkYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgcmVhZDphbm9tYWx5X2Jsb2NrcyBkZWxldGU6YW5vbWFseV9ibG9ja3MgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgdXBkYXRlOmN1c3RvbV9kb21haW5zIHJlYWQ6ZW1haWxfdGVtcGxhdGVzIGNyZWF0ZTplbWFpbF90ZW1wbGF0ZXMgdXBkYXRlOmVtYWlsX3RlbXBsYXRlcyByZWFkOm1mYV9wb2xpY2llcyB1cGRhdGU6bWZhX3BvbGljaWVzIHJlYWQ6cm9sZXMgY3JlYXRlOnJvbGVzIGRlbGV0ZTpyb2xlcyB1cGRhdGU6cm9sZXMgcmVhZDpwcm9tcHRzIHVwZGF0ZTpwcm9tcHRzIHJlYWQ6YnJhbmRpbmcgdXBkYXRlOmJyYW5kaW5nIGRlbGV0ZTpicmFuZGluZyByZWFkOmxvZ19zdHJlYW1zIGNyZWF0ZTpsb2dfc3RyZWFtcyBkZWxldGU6bG9nX3N0cmVhbXMgdXBkYXRlOmxvZ19zdHJlYW1zIGNyZWF0ZTpzaWduaW5nX2tleXMgcmVhZDpzaWduaW5nX2tleXMgdXBkYXRlOnNpZ25pbmdfa2V5cyByZWFkOmxpbWl0cyB1cGRhdGU6bGltaXRzIGNyZWF0ZTpyb2xlX21lbWJlcnMgcmVhZDpyb2xlX21lbWJlcnMgZGVsZXRlOnJvbGVfbWVtYmVycyByZWFkOmVudGl0bGVtZW50cyByZWFkOmF0dGFja19wcm90ZWN0aW9uIHVwZGF0ZTphdHRhY2tfcHJvdGVjdGlvbiByZWFkOm9yZ2FuaXphdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbnMgZGVsZXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcnMgZGVsZXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIGNyZWF0ZTpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgcmVhZDpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbl9jb25uZWN0aW9ucyBkZWxldGU6b3JnYW5pemF0aW9uX2Nvbm5lY3Rpb25zIGNyZWF0ZTpvcmdhbml6YXRpb25fbWVtYmVyX3JvbGVzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBkZWxldGU6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBjcmVhdGU6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIHJlYWQ6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIGRlbGV0ZTpvcmdhbml6YXRpb25faW52aXRhdGlvbnMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.HtKznmRuRlyk6mv6VX2PZJcWxpxvFn-KnWa5ieygMlkMlegu4z7h4NGtdJHsmlpnIjNEXj-dMcTwgzO6Pm1KVypq59zS49DcGw62TaLu-Zi6pzJdTU7tiJXNO2fY38PkJWWDxSGmpsoS7ybgPU6qc39Lo91_2QoMkKdpIFjWd832KxnZHfa1rhfMwP4ApZV_2ypPKcLQkgs937lBmv7ddZb9xEfED_v3WeDWksrqsfktkM7_OoH_gh3veLTLNh_drDhm55h4yMFsiUWk_F5YLAbdaJM65a_Hg6rhwHjy-pYe1gPUDdoj9tzyOCrss1jODo1nmtOpw3hzca4vHMOQKg";
  @Autowired
  private WebApplicationContext webApplicationContext;

//  @Autowired
//  private TransactionController transactionController;

  // @Autowired
  // private MockMvc mockMvc;

  @MockBean
  private TransactionService transactionService;

  @LocalServerPort
  private int port;

  private static final String apiPath = "/transaction";

  @BeforeEach
  public void setUp() {
    RestAssured.port = this.port;
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    RestAssuredMockMvc.reset();
    RestAssuredMockMvc.config().getMockMvcConfig().automaticallyApplySpringSecurityMockMvcConfigurer();
    // RestAssuredMockMvc.mockMvc(mockMvc);
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
  }

  @Test
  public void testGetAllTransactions() throws Exception {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    List<Transaction> content = Arrays.asList(
      Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build(),
      Transaction.builder().id(UUID.randomUUID()).amount("GBP 1000").iban(iban).date(localDate).description(description).build(),
      Transaction.builder().id(UUID.randomUUID()).amount("EURO 1000").iban(iban).date(localDate).description(description).build()
    );

    PageUtil<Transaction> transactionPageUtil = new PageUtil<>(1, 1, content, content.size());

    BDDMockito.given(this.transactionService
        .getAllTransactions(Mockito.any()))
      .willReturn(
        transactionPageUtil
      );

    log.info(transactionPageUtil.toString());
    // @formatter:off
    Response response = RestAssured
      .given()
        .header(new Header("Authorization", AUTHORIZATION))
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .get(apiPath)
      .then()
        .body("content[0].amount", Matchers.is(amount))
        .body("content[1].amount", Matchers.is("GBP 1000"))
        .body("content[2].amount", Matchers.is("EURO 1000"))
      .statusCode(HttpStatus.OK.value())
      .extract().response();

    System.out.println("===================================");
    System.out.println(response.getBody().asString());
    System.out.println("===================================");

    // @formatter:on

    Mockito.verify(this.transactionService, Mockito.times(1)).getAllTransactions(Mockito.any(Pageable.class));
    Mockito.verifyNoMoreInteractions(this.transactionService);
  }

  @Test
  public void testGetTransaction() throws Exception {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    // @formatter:off
    BDDMockito
      .given(this.transactionService.getTransaction(id))
      .willReturn(
        Optional.ofNullable(
          Transaction.builder().amount(amount).iban(iban).date(localDate).description(description).build()
        )
      );

    // @formatter:on

    // @formatter:off
    RestAssured
      .given()
        .header(new Header("Authorization", AUTHORIZATION))
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
      .when()
        .get(apiPath + "/" + id)
      .then()
        .body("amount", Matchers.is(amount))
        .body("description", Matchers.is(description))
        .body("iban", Matchers.is(iban))

      .statusCode(HttpStatus.OK.value());
    // @formatter:on

    Mockito.verify(this.transactionService, Mockito.times(1)).getTransaction(id);
    Mockito.verifyNoMoreInteractions(this.transactionService);
  }


}
