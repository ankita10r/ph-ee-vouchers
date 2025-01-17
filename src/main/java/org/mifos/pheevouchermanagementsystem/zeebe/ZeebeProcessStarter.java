package org.mifos.pheevouchermanagementsystem.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.mifos.connector.common.zeebe.ZeebeVariables;
import org.mifos.pheevouchermanagementsystem.service.RedeemVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ZeebeProcessStarter {
    private static final Logger logger = LoggerFactory.getLogger(ZeebeProcessStarter.class);

    @Autowired
    private ZeebeClient zeebeClient;
    public String startZeebeWorkflow(String workflowId, String request, Map<String, Object> extraVariables) {
        String transactionId = generateTransactionId();

        Map<String, Object> variables = new HashMap<>();
        variables.put(org.mifos.connector.common.zeebe.ZeebeVariables.TRANSACTION_ID, transactionId);
        variables.put(org.mifos.connector.common.zeebe.ZeebeVariables.CHANNEL_REQUEST, request);
        variables.put(ZeebeVariables.ORIGIN_DATE, Instant.now().toEpochMilli());
        if (extraVariables != null) {
            variables.putAll(extraVariables);
        }

        logger.info("starting workflow HERE:");
        // TODO if successful transfer response arrives in X timeout return it otherwise do callback
        ProcessInstanceEvent instance = zeebeClient.newCreateInstanceCommand().bpmnProcessId(workflowId).latestVersion()
                .variables(variables).send().join();

        logger.info("zeebee workflow instance from process {} started with transactionId {}, instance key: {}", workflowId, transactionId,
                instance.getProcessInstanceKey());
        return transactionId;
    }
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
