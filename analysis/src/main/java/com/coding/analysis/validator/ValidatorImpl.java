package com.coding.analysis.validator;

import com.coding.common.build.Result;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import strman.Strman;

import java.io.File;

import static com.coding.common.util.FileUtils.legalDirectory;

@Slf4j
public class ValidatorImpl implements Validator {

    @Override
    public ResultInput validate(Result result) {
        Preconditions.checkNotNull(result);
        return createResultInput(result);

    }

    private ResultInput createResultInput(Result result) {
        ResultInput resultInput = new ResultInput().result(result);
        if (checkNull(result.buildTime(), "buildTime", resultInput)) {
            log.error("result illegal, buildTime is null");
            return resultInput;
        }

        if (checkNull(result.specificReason(), "specificReason", resultInput)) {
            log.error("specificReason illegal, specificReason is null");
            return resultInput;
        }
        if (checkNull(result.path(), "path", resultInput)) {
            log.error("result illegal, path is null");
            return resultInput;
        }
        if (checkArgument(!legalDirectory(new File(result.path()))
                , "path", resultInput, " is illegal directory", ResultIllegalReason.ILLEGAL_PATH)) {
            log.error("result illegal, path={}", result.path());
            return resultInput;

        }
        return resultInput
                .legal(true);
    }

    private boolean checkNull(Object reference, String field, ResultInput resultInput) {
        return checkArgument(reference == null
                , field, resultInput, " is null", ResultIllegalReason.PARAMETER_LOSS);
    }


    private boolean checkArgument(boolean reference
            , String field, ResultInput resultInput, String description, ResultIllegalReason illegalReason) {
        if (!reference) {
            resultInput
                    .legal(false)
                    .resultIllegalReason(illegalReason)
                    .msg(Strman.append(field, description));

            return false;
        }
        return true;
    }


}
