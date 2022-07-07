package com.blogservice.service;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface ApiDocumentUtils {

    // localhost -> docs.api.com
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
              modifyUris()
                    .scheme("https")
                    .host("docs.api.com")
                    .removePort()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse();
    }
}