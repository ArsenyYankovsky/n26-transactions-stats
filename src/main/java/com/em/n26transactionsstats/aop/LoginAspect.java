package com.em.n26transactionsstats.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A central logging . inspect all the methods in and out and log .
 * 
 * @author nwalisundara
 *
 */
@Aspect
public class LoginAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAspect.class);

	@Around("execution(* com.em.n26transactionsstats..*.*(..))") //
	public Object log(ProceedingJoinPoint call) throws Throwable { //
		StringBuilder builder = new StringBuilder(); // //
		builder.append(call.getSignature());
		Arrays.stream(call.getArgs()).forEach(e -> builder.append(" | ").append(e));
// Long the  the input
		LOGGER.info(builder.toString());
		Object point = call.proceed();
		// Log the out put
		LOGGER.info(point.toString());
		return point;
	}

}
