package org.mifos.pheevouchermanagementsystem.api.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mifos.pheevouchermanagementsystem.api.definition.VoucherLifecycleManagementApi;
import org.mifos.pheevouchermanagementsystem.data.RequestDTO;
import org.mifos.pheevouchermanagementsystem.data.ResponseDTO;
import org.mifos.pheevouchermanagementsystem.service.ActivateVoucherService;
import org.mifos.pheevouchermanagementsystem.service.CreateVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import static org.mifos.pheevouchermanagementsystem.util.VoucherManagementEnum.*;

@RestController
public class VoucherLifecycleManagementApiController implements VoucherLifecycleManagementApi {
    @Autowired
    ActivateVoucherService activateVoucherService;
    @Override
    public ResponseDTO voucherStatusChange(String callbackURL, RequestDTO requestBody, String command) throws ExecutionException, InterruptedException, JsonProcessingException {
        try {
            if(command.equals("activate")){
                activateVoucherService.activateVouchers(requestBody, callbackURL);
            }        } catch (Exception e) {
            return new ResponseDTO(FAILED_RESPONSE.getValue(), FAILED_RESPONSE.getMessage(), requestBody.getRequestID());

        }
        return new ResponseDTO(SUCCESS_RESPONSE.getValue(), SUCCESS_RESPONSE.getMessage(), requestBody.getRequestID());

    }
}