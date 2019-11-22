package com.springboot.microservices.authenticationservice.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.CharacterRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordGenerator;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;

@Component
public class AuthenticatorUtils {

	public String randomPasswordGenerator() {

		CharacterCharacteristicsRule charCharRule = new CharacterCharacteristicsRule();
		List<CharacterRule> charRule = new ArrayList<CharacterRule>();

		charRule.add(new DigitCharacterRule(1));
		charRule.add(new NonAlphanumericCharacterRule(1));
		charRule.add(new UppercaseCharacterRule(1));
		charRule.add(new LowercaseCharacterRule(1));

		charCharRule.getRules().addAll(charRule);
		charCharRule.setNumberOfCharacteristics(4);

		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.addAll(charRule);

		String strPwd = new PasswordGenerator().generatePassword(10, charRule);
		return strPwd;
	}

	public long randomNumberGenerator() {

		Random rand = null;
		long loginId = 0l;
		try {
			rand = SecureRandom.getInstance("SHA1PRNG");
			loginId = rand.nextLong();
		} catch (NoSuchAlgorithmException nsae) {
		}
		return Math.abs(loginId);
	}

	public float calculateTime(String systemTime, String userTime) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		Date userDateTime = format.parse(userTime);
		Date currentDateTime = format.parse(systemTime);

		long diff = currentDateTime.getTime() - userDateTime.getTime();
		float diffMinutes = (float) diff / (60 * 1000);

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		float twoDigitsF = Float.valueOf(decimalFormat.format(diffMinutes));

		return twoDigitsF;
	}

	public void passwordValidator(String pwd) {

		LengthRule lengthRule = new LengthRule(8, 16);

		WhitespaceRule whitespaceRule = new WhitespaceRule();

		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();

		charRule.getRules().add(new DigitCharacterRule(1));
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		charRule.getRules().add(new UppercaseCharacterRule(1));
		charRule.getRules().add(new LowercaseCharacterRule(1));

		charRule.setNumberOfCharacteristics(3);

		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();
		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3, true);
		QwertySequenceRule qwertySeqRule = new QwertySequenceRule();
		RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		ruleList.add(charRule);
		ruleList.add(alphaSeqRule);
		ruleList.add(numSeqRule);
		ruleList.add(qwertySeqRule);
		ruleList.add(repeatRule);

		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(pwd));

		RuleResult result = validator.validate(passwordData);

		if (result.isValid()) {
			System.out.println("Valid password");
		} else {
			System.out.println("Invalid password:");
			for (String msg : validator.getMessages(result)) {
				System.out.println(msg);
			}
		}
	}
}
