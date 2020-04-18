package com.sturdy.springbatch.appleciation.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class SkipCheckingListener extends StepExecutionListenerSupport {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exicode = stepExecution.getExitStatus().getExitCode();
        if(!exicode.equals((ExitStatus.FAILED.getExitCode())) && stepExecution.getSkipCount() > 0  ) {
            return new ExitStatus("COMPLETE WITH SKIPS");
        } else {
            return null;
        }
    }
}
