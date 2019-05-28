package com.learnyeai.resource.core.plan;

import java.util.Iterator;

import com.learnyeai.resource.core.details.TeachableDetails;

public interface TeachingPlan<TD extends TeachableDetails>
		extends Iterator<PlanDirectory<TD>> {

	String getName();

}
