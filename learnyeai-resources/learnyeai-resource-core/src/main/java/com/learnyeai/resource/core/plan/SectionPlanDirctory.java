package com.learnyeai.resource.core.plan;

import com.learnyeai.resource.core.course.SectionDetails;
import com.learnyeai.resource.core.details.TeachableDetails;

public interface SectionPlanDirctory<TD extends TeachableDetails> extends PlanDirectory<TD> {

	SectionDetails getSection();

}
