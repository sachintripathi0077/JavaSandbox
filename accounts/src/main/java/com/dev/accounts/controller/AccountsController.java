package com.dev.accounts.controller;

import com.dev.accounts.constants.AccountConstants;
import com.dev.accounts.dto.CustomerAccountDto;
import com.dev.accounts.dto.CustomerDto;
import com.dev.accounts.dto.ErrorResponseDto;
import com.dev.accounts.dto.ResponseDto;
import com.dev.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Validated
public class AccountsController {

//    @Autowired  // using constructor autowiring instead
    private IAccountService accountService;

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
}
