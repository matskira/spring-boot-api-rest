package io.github.matskira.validation.constraintsvalidate;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.matskira.validation.NotEmptyList;

public class NotEmptyListConstraints implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		return value != null && !value.isEmpty();
	}


	@Override
	public void initialize(NotEmptyList constraintsAnnotation) {
	}
}
