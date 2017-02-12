package net.aegistudio.uio.strmdbg;

import java.io.IOException;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;

public class StackTranslator extends DebugTranslator {

	public StackTranslator(Debuggable debug, Translator translator) {
		super(debug, translator);
	}
	
	private String getCaller() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StackTraceElement target = elements[4];
		return "(" + target.getFileName() + ":" + target.getLineNumber() + ") :: " + target.getMethodName();
	}
	
	protected void debug(String type, IOECallable next) throws IOException {
		super.debug(getCaller() + " => " + type, next);
	}
	
	protected void constDebug(String type, IOECCallable next) throws IOException, CorruptException {
		super.constDebug(getCaller() + " => " + type, next);
	}
}