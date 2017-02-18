package net.aegistudio.uio.wrap;

import java.io.IOException;
import java.util.Arrays;

import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;

public class ZeroPaddingString extends Transform<String, String> {
	protected int length;
	
	public ZeroPaddingString(int paramLength, Wrapper<String> delegated) {
		super(delegated, string -> {
			int top = 0;	byte[] bytes = string.getBytes();
			for(int i = 0; i < paramLength && i < bytes.length; i++) {
				if(bytes[i] == 0) break;
				else top = i + 1;
			}
			byte[] result = new byte[top];
			System.arraycopy(bytes, 0, result, 0, top);
			return new String(result);
		}, string -> {
			byte[] result = new byte[paramLength];
			Arrays.fill(result, (byte)0);
			System.arraycopy(string.getBytes(), 0, result, 0, 
					Math.min(string.getBytes().length, paramLength));
			return new String(result);
		});
		this.length = paramLength;
	}
	
	public void translate(Translator translator) throws IOException {
		translator.string(length, delegate);
	}
}
