package com.ocesclade.amisdeescalade.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		
		Properties props = new Properties();
		URL resource = this.getClass().getResource("passay_fr.properties");
		
		try {
			props.load(new FileInputStream(resource.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageResolver resolver = new PropertiesMessageResolver(props);
		
		PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
				new LengthRule(8, Integer.MAX_VALUE),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new CharacterRule(EnglishCharacterData.Special, 1),
				new WhitespaceRule()
				));
		RuleResult result = validator.validate(new PasswordData(password));
		if (result.isValid()) {
			return true;
		}
		List<String> messages = validator.getMessages(result);

		String messageTemplate = messages.stream().collect(Collectors.joining(","));
		context
			.buildConstraintViolationWithTemplate(messageTemplate)
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		return false;
	}

}
