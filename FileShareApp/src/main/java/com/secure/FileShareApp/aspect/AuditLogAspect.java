package com.secure.FileShareApp.aspect;

import com.secure.FileShareApp.annotation.FileIdParam;
import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.annotation.UserIdParam;
import com.secure.FileShareApp.dto.UploadedFileDto;
import com.secure.FileShareApp.service.AuditLogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLogAspect {

    private final AuditLogsService auditService;

    @Around("@annotation(logAction)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint, LogAction logAction) throws Throwable {
        Object result;
        String userId = null;
        String fileId = null;
        String message;

        try {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof String strArg) {
                    if (strArg.equals("userId")) {
                        userId = strArg;
                    } else if (strArg.equals("fileId")) {
                        fileId = strArg;
                    }
                }
//                else if (arg instanceof User user) {
//                    userId = user.getUserId();
//                } else if (arg instanceof UploadedFile file) {
//                    fileId = file.getFileId();
//                }
            }

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (int i = 0; i < args.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof UserIdParam) {
                        userId = (String) args[i];
                    } else if (annotation instanceof FileIdParam) {
                        fileId = (String) args[i];
                    }
                }
            }

            result = joinPoint.proceed(); // Execute the actual method

            // Now, try to extract fileId from the returned object
            if (result instanceof UploadedFileDto fileDto) {
                fileId = fileDto.getFileId();
            } else if (result instanceof Page<?> pageResult && !pageResult.isEmpty()) {
                Object firstItem = pageResult.getContent().getFirst();
                if (firstItem instanceof UploadedFileDto fileDto) {
                    fileId = fileDto.getFileId();
                }
            }

            message = "SUCCESS: Action [" + logAction.action() + "] executed successfully for User: " + userId + " on File: " + fileId;
            auditService.logAction(userId, fileId, logAction.action(), message);
            log.info(message);

            return result;
        } catch (Exception e) {
            message = "ERROR: Action [" + logAction.action() + "] failed for User: " + userId + " on File: " + fileId + ". Reason: " + e.getMessage();
            auditService.logAction(userId, fileId, logAction.action(), message);
            log.error(message);
            throw e;
        }
    }
}

