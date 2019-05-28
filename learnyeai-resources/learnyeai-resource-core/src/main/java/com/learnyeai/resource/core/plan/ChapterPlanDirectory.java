package com.learnyeai.resource.core.plan;

import com.learnyeai.resource.core.course.ChapterDetails;
import com.learnyeai.resource.core.details.TeachableDetails;

public interface ChapterPlanDirectory<TD extends TeachableDetails> extends PlanDirectory<TD> {

	ChapterDetails getChapter();

}
