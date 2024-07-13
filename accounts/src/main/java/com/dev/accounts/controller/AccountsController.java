package com.dev.accounts.controller;

import com.dev.accounts.constants.AccountConstants;
import com.dev.accounts.dto.*;
import com.dev.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST API for Accounts in MicroBank",
        description = "CRUD REST APIs in MicroBank for Create, Read, Update and Delete"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor -> removing temporarily to work with buildVersion field, else its bean being not present will mess up
@Validated
@Slf4j
public class AccountsController {

//    @Autowired  // using constructor autowiring instead
    private final IAccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    public AccountsController(IAccountService iAccountService){
        this.accountService = iAccountService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @Operation(
            summary = "Create account REST API",
            description = "Create REST API in MicroBank Accounts to create new accounts"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http.status = CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(
            @Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "REST API to fetch accounts details.",
            description = "REST API to fetch accounts details based on a registered mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http.status = OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerAccountDto> fetchAccountDetails(@RequestParam
                                                                  @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                                  String mobileNumber){
        CustomerAccountDto customerAccountDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @Operation(
            summary = "Update REST API for MicroBank Accounts",
            description = "REST API to update existing user/account details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http.status = OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http.status = INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
        }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerAccountDto customerAccountDto){
        boolean isUpdated = accountService.updateAccount(customerAccountDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Update REST API for MicroBank Accounts",
            description = "REST API to update existing user/account details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http.status = OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http.status = INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "(^|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber){
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500,AccountConstants.MESSAGE_500));
        }
    }

    @Retry(name="getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        logger.debug("getBuildInfo() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
        logger.debug("getBuildInfoFallback() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

    @RateLimiter(name = "getJavaVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java 17");
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
