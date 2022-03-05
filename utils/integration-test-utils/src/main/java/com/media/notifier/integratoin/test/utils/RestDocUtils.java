package com.media.notifier.integratoin.test.utils;

import lombok.experimental.UtilityClass;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

@UtilityClass
public class RestDocUtils {
    public RestDocumentationResultHandler createRestDoc(String docName) {
        return MockMvcRestDocumentation.document(docName,
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));
    }
}
