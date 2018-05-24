package com.copart.framework.fcm.sasl.auth;

/**
 * Implementation of the SASL PLAIN mechanism.
 *
 */
public class SASLPlainMechanism extends SASLJavaXMechanism {

	public static final String NAME = PLAIN;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean authzidSupported() {
		return true;
	}

	@Override
	public int getPriority() {
		return 400;
	}

	@Override
	public SASLPlainMechanism newInstance() {
		return new SASLPlainMechanism();
	}
}
