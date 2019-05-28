package com.learnyeai.resource.core;

import com.learnyeai.resource.core.task.ResourceableTaskDetails;

public interface DocumentTaskDetails<Type extends DocumentType, Details extends DocumentDetails<Type>>
		extends ResourceableTaskDetails<Details> {

}
