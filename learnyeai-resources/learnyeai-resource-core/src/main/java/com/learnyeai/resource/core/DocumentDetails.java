package com.learnyeai.resource.core;

import com.learnyeai.resource.core.details.ResourceableDetails;

public interface DocumentDetails<T extends DocumentType> extends ResourceableDetails {

	T getDocumentType();

}
