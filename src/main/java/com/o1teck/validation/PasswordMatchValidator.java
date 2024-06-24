package com.o1teck.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.o1teck.model.entity.SiteUser;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SiteUser> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(SiteUser user, ConstraintValidatorContext c) {
		
		String plainPassword = user.getPlainPassword();
		String repeatPassword = user.getRepeatPassword();
		if(plainPassword==null || plainPassword.length() == 0){
			return true;
		}
		
		//To my worry here about the overdetermined null condition... there is already validation on the register
		//module that would prevent plainPassword from being Null 
		if(plainPassword==null || !plainPassword.equals(repeatPassword)){
			return false;
		}
		return true;
	}
}